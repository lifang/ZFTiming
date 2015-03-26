package com.comdosoft.financial.timing.service;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.timing.controller.api.ZhangFuRecord;
import com.comdosoft.financial.timing.domain.trades.Profit;
import com.comdosoft.financial.timing.domain.trades.TradeConsumeRecord;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.SupportTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.mapper.trades.ProfitMapper;
import com.comdosoft.financial.timing.mapper.trades.TradeConsumeRecordMapper;
import com.comdosoft.financial.timing.mapper.trades.TradeRecordMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.DictionaryTradeTypeMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.SupportTradeTypeMapper;
import com.comdosoft.financial.timing.utils.page.PageRequest;

@Service
public class TradeService {
	
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private DictionaryTradeTypeMapper dictionaryTradeTypeMapper;
	@Autowired
	private TradeRecordMapper tradeRecordMapper;
	@Autowired
	private SupportTradeTypeMapper supportTradeTypeMapper;
	@Autowired
	private TradeConsumeRecordMapper tradeConsumeRecordMapper;
	@Autowired
	private ProfitMapper profitMapper;
	@Value("${file.trade.record.path}")
	private String tradeRecordFilePath;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d H.m.s.S");
	
	@Cacheable("tradeTypes")
	public Map<Integer, DictionaryTradeType> allTradeTypes(){
		List<DictionaryTradeType> typeList = dictionaryTradeTypeMapper.selectAll();
		return typeList.stream().collect(
				Collectors.toMap(DictionaryTradeType::getId, Function.identity()));
	}

	public List<TradeRecord> searchNonCalculatedRecord(){
		PageRequest request = new PageRequest(1, 10);
		return tradeRecordMapper.selectPage(request,
				TradeRecord.ATTACH_STATUS_NO_CALCULATED,
				TradeRecord.TRADE_STATUS_SUCCESS);
	}
	
	public void updateRecord(TradeRecord tradeRecord){
		tradeRecordMapper.updateByPrimaryKey(tradeRecord);
	}
	
	/**
	 * 查询通道的交易费率
	 * @param channelId
	 * @param tradeTypeId
	 * @return
	 */
	public SupportTradeType selectSupportTradeType(Integer channelId,Integer tradeTypeId){
		return supportTradeTypeMapper.selectBaseSupportTradeType(channelId, tradeTypeId);
	}
	
	public void saveProfit(Profit profit) {
		profitMapper.insert(profit);
	}
	
	@Transactional("transactionManager-trades")
	public void receiveRecord(ZhangFuRecord record) {
		TradeRecord tradeRecord = new TradeRecord();
		tradeRecordMapper.insert(tradeRecord);
	}
	
	@Transactional("transactionManager-trades")
	public void importTradeRecords(MultipartFile file) throws Exception{
		String filePath = MessageFormat.format(tradeRecordFilePath, LocalDate.now().getYear(), file.getOriginalFilename());
		File f = new File(filePath);
		File parent = f.getParentFile();
		if(!parent.exists()){
			parent.mkdirs();
		}
		file.transferTo(f);//保存请求文件
		parseImportFile(f);//解析并处理文件
	}
	
	private void parseImportFile(File file) throws Exception{
		try(RandomAccessFile raf = new RandomAccessFile(file, "r")){
			String line = raf.readLine();
			while(line != null){
				parseLine(line);
				line = raf.readLine();
			}
		}
	}
	
	//设备KSN号||交易流水号||交易时间||交易金额||消费卡号||交易类型||交易状态||商户号||商户名称||参考号||交易号
	//501010020690||000026||2013-9-2 17.20.48.0||11.11||6210982900009137383||sale||1||500000000009585||上海掌富||100002622625	||2205408
	private void parseLine(String line) throws ParseException {
		String[] strs = line.split("\\|\\|");
		if(strs.length!=11){
			throw new IllegalArgumentException("解析长度不正确。");
		}
		String ksn = strs[0];
		String flowNo = strs[1];
		String time = strs[2];
		String amount = strs[3];
		String cardNo = strs[4];
//		String tradeType = strs[5];
		String tradeStatus = strs[6];
		String merchantNo = strs[7];
		String merchantName = strs[8];
		String referNo = strs[9];
		String tradeNo = strs[10];
		Terminal terminal = terminalService.findBySerial(ksn);
		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setTerminalNumber(ksn);
		tradeRecord.setTradeNumber(flowNo);
		Date date = formatter.parse(time);
		tradeRecord.setTradedAt(date);
		Float f = Float.parseFloat(amount)*100;
		tradeRecord.setAmount(f.intValue());
		tradeRecord.setTradeTypeId(DictionaryTradeType.ID_TRADE);//导入的都是交易类型，不需要判断，所以strs[5]都等于sale
		tradeRecord.setTradedStatus(Integer.parseInt(tradeStatus));
		tradeRecord.setMerchantNumber(merchantNo);
		tradeRecord.setMerchantName(merchantName);
		tradeRecord.setOrderNumber(tradeNo);
		tradeRecord.setActualPaymentPrice(tradeRecord.getAmount());
		tradeRecord.setCreatedAt(new Date());
		tradeRecord.setSysOrderId(flowNo+referNo);
		tradeRecord.setAgentId(terminal.getAgentId());
		tradeRecord.setPayChannelId(terminal.getPayChannelId());
		tradeRecord.setCustomerId(terminal.getCustomerId());
		tradeRecordMapper.insert(tradeRecord);
		TradeConsumeRecord tcr = new TradeConsumeRecord();
		tcr.setPayFromAccount(cardNo);
		tcr.setId(tradeRecord.getId());
		tcr.setCreatedAt(new Date());
		tcr.setUpdatedAt(new Date());
		tradeConsumeRecordMapper.insert(tcr);
	}
}
