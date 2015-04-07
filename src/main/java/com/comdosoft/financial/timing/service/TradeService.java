package com.comdosoft.financial.timing.service;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.timing.domain.ZhangFuRecord;
import com.comdosoft.financial.timing.domain.trades.Profit;
import com.comdosoft.financial.timing.domain.trades.TradeConsumeRecord;
import com.comdosoft.financial.timing.domain.trades.TradeRechargeRecord;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.trades.TradeTransferRepaymentRecord;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.SupportTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.mapper.trades.ProfitMapper;
import com.comdosoft.financial.timing.mapper.trades.TradeConsumeRecordMapper;
import com.comdosoft.financial.timing.mapper.trades.TradeRechargeRecordMapper;
import com.comdosoft.financial.timing.mapper.trades.TradeRecordMapper;
import com.comdosoft.financial.timing.mapper.trades.TradeTransferRepaymentRecordMapper;
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
	private TradeRechargeRecordMapper tradeRechargeRecordMapper;
	@Autowired
	private TradeTransferRepaymentRecordMapper tradeTransferRepaymentRecordMapper;
	@Autowired
	private ProfitMapper profitMapper;
	@Value("${file.trade.record.path}")
	private String tradeRecordFilePath;
	
	@Cacheable("allTradeTypeMap")
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
		tradeRecord.setTerminalNumber(record.getKeyDeviceSerialNo());
		tradeRecord.setTradeNumber(record.getOrderId());
		tradeRecord.setBatchNumber(record.getPartnerNo());
		
		DateTime cdt = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(record.getCreateTime());
		tradeRecord.setTradedAt(cdt.toDate());
		DateTime fdt = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(record.getFinalTime());
		tradeRecord.setTradedAt(fdt.toDate());
		
		tradeRecord.setTradedStatus(Integer.parseInt(record.getResult().getPayStatus()));
		
		switch(record.getTransaction().getType()){
		case "feePhone":
			tradeRecord.setTradeTypeId(DictionaryTradeType.ID_PHONE_RECHARGE);
			Float f = Float.parseFloat(record.getTransaction().getFaceValue())*100;
			tradeRecord.setAmount(f.intValue());
			break;
		case "payForCreditCard":
			tradeRecord.setTradeTypeId(DictionaryTradeType.ID_REPAY);
			Float p = Float.parseFloat(record.getTransaction().getTransferAmount())*100;
			tradeRecord.setAmount(p.intValue());
			break;
		case "transfer":
			tradeRecord.setTradeTypeId(DictionaryTradeType.ID_TRANSFER);
			Float t = Float.parseFloat(record.getTransaction().getTransferAmount())*100;
			tradeRecord.setAmount(t.intValue());
			break;
		}
		String rebateMoney = record.getTransaction().getRebateMoney();
		Float rebateMoneyFloat = Float.parseFloat(rebateMoney)*100;
		tradeRecord.setProfitPrice(rebateMoneyFloat.intValue());
		//终端
		Terminal terminal = terminalService.findBySerial(tradeRecord.getTerminalNumber());
		tradeRecord.setAgentId(terminal.getAgentId());
		tradeRecord.setPayChannelId(terminal.getPayChannelId());
		tradeRecord.setCustomerId(terminal.getCustomerId());
		tradeRecord.setCreatedAt(new Date());
		tradeRecordMapper.insert(tradeRecord);
		
		switch(record.getTransaction().getType()){
		case "feePhone":
			TradeRechargeRecord trr = new TradeRechargeRecord();
			trr.setId(tradeRecord.getId());
			trr.setPayFromAccount(record.getPayCardNo());
			trr.setPhone(record.getTransaction().getFeePhone());
			trr.setArrivedResult(Integer.parseInt(record.getResult().getDeliveryStatus()));
			trr.setArrivedCode(Integer.parseInt(record.getResult().getDeliveryResultCode()));
			trr.setArrivedErrorDescription(record.getResult().getDeliveryResultDes());
			trr.setReturnedCode(Integer.parseInt(record.getResult().getRefundResultCode()));
			trr.setReturnedErrorDescription(record.getResult().getRefundResultDes());
			trr.setReturnedResult(Integer.parseInt(record.getResult().getRefundStatus()));
			tradeRechargeRecordMapper.insert(trr);
			break;
		case "payForCreditCard":
		case "transfer":
			TradeTransferRepaymentRecord ttrr = new TradeTransferRepaymentRecord();
			ttrr.setId(tradeRecord.getId());
			ttrr.setPayFromAccount(record.getPayCardNo());
			ttrr.setPayIntoAccount(record.getTransaction().getToAccount());
			ttrr.setArrivedResult(Integer.parseInt(record.getResult().getDeliveryStatus()));
			ttrr.setArrivedCode(Integer.parseInt(record.getResult().getDeliveryResultCode()));
			ttrr.setArrivedErrorDescription(record.getResult().getDeliveryResultDes());
			ttrr.setReturnedCode(Integer.parseInt(record.getResult().getRefundResultCode()));
			ttrr.setReturnedErrorDescription(record.getResult().getRefundResultDes());
			ttrr.setReturnedResult(Integer.parseInt(record.getResult().getRefundStatus()));
			tradeTransferRepaymentRecordMapper.insert(ttrr);
			break;
		}
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
		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setTerminalNumber(ksn);
		tradeRecord.setTradeNumber(flowNo);
		DateTime date = DateTimeFormat.forPattern("yyyy-M-d H.m.s.S").parseDateTime(time);
		tradeRecord.setTradedAt(date.toDate());
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
		//终端
		Terminal terminal = terminalService.findBySerial(tradeRecord.getTerminalNumber());
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
