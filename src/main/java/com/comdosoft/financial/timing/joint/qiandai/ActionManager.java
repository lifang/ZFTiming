package com.comdosoft.financial.timing.joint.qiandai;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.comdosoft.financial.timing.controller.api.QiandaibaoController;
import com.comdosoft.financial.timing.domain.trades.ResultInfo;
import com.comdosoft.financial.timing.domain.trades.ResultList;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.joint.JointException;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;
import com.comdosoft.financial.timing.mapper.zhangfu.CommonItemsMapper;
import com.comdosoft.financial.timing.service.QiandaiService;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.utils.StringUtils;
import com.comdosoft.financial.timing.utils.page.Page;
import com.comdosoft.financial.timing.utils.page.PageRequest;
@Component
public class ActionManager implements JointManager{
	
	@Value("${MD5key}")
	private String MD5key;
	@Value("${transaction.query.url}")
	private String transactionQueryUrl;
	@Value("${pos.query.url}")
	private String posQueryUrl;
	
	private static final Logger LOG = LoggerFactory.getLogger(ActionManager.class);
	@Autowired
	private CommonItemsMapper commonItemsMapper;
	@Autowired
	private QiandaiService qiandaiService;
	@Override
	public JointResponse acts(JointRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String syncStatus(Terminal terminal, TerminalService terminalService) {
		return syncStatus(terminal,terminalService,qiandaiService,commonItemsMapper);
	}
	public String syncStatus(Terminal terminal, TerminalService terminalService,
			QiandaiService qiandaiService,CommonItemsMapper commonItemsMapper) {
		QiandaibaoController qiandaoHandler = new QiandaibaoController();
		qiandaoHandler.setMD5key(MD5key);
		qiandaoHandler.setPosQueryUrl(posQueryUrl);
		qiandaoHandler.setQiandaiService(qiandaiService);
		qiandaiService.setCommonItemsMapper(commonItemsMapper);
		String eqno = terminal.getSerialNum();
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String remark = terminal.getReserver1();
		StringBuilder sb = new StringBuilder();
		sb.append("eqno=" + eqno);
		sb.append("now=" + now);
		sb.append(MD5key);
		String sign = StringUtils.encryption(sb.toString(), "MD5");
		String result = qiandaoHandler.posQuery(eqno, now, remark, sign);
		if(!"ok".equals(result)){
			return null;	
		}
		return result;
	}

	@Override
	public Page<Bank> bankList(String keyword, PageRequest request,
			String serialNum) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Page<TradeRecord> pullTrades(Terminal terminal, Integer tradeTypeId,
			PageRequest request){
		QiandaibaoController qiandaoHandler = new QiandaibaoController();
		qiandaoHandler.setMD5key(MD5key);
		qiandaoHandler.setTransactionQueryUrl(transactionQueryUrl);
		List<TradeRecord> records = new ArrayList<TradeRecord>();
		String eqno = terminal.getSerialNum();
		String querytype = "2";
		String begintime = "2015-04-15 01:01:01";
		String endtime = "2015-04-27 01:01:01";
		StringBuilder sb = new StringBuilder();
		sb.append("eqno=" + eqno);
		sb.append("querytype=" + querytype);
		sb.append("begintime=" + begintime);
		sb.append("endtime=" + endtime);
		sb.append(MD5key);
		String sign = StringUtils.encryption(sb.toString(), "MD5");
		
		String re = qiandaoHandler.transactionRecordQuery(eqno, querytype, begintime, endtime, sign);
		if(re.indexOf("\"code\":-1,")>0){		
			return null;		
		}

		ResultInfo resultInfo = new ResultInfo();
		resultInfo = (ResultInfo) StringUtils.parseJSONStringToObject(re, resultInfo);				
		ResultList[] result = resultInfo.getResult().getList();
		
		for(ResultList res : result){
			TradeRecord tradeRecord = new TradeRecord();
			tradeRecord.setAmount((int)(res.getAmount()*100));
			tradeRecord.setId(res.getId());
			tradeRecord.setPoundage((int)(res.getPoundage()*100));
			tradeRecord.setTradedStatus(Integer.valueOf(res.getTradedStatus()));
			tradeRecord.setTradeNumber(res.getTradeNumber());
			try {
				tradeRecord.setTradedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(res.getTradedTimeStr()));
			} catch (ParseException e) {
				LOG.info("交易流水查询失败,日期不正确");
				return null;
			}
			tradeRecord.setPayFromAccount(res.getPayFromAccount());
			tradeRecord.setTerminalNumber(res.getTerminalNumber());
			records.add(tradeRecord);
		}
		return new Page<TradeRecord>(request, records, records.size());
	}

	@Override
	public void submitOpeningApply(Terminal terminal,
			TerminalService terminalService) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPwd(Terminal terminal, TerminalService terminalService)
			throws JointException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyPwd(Terminal terminal, TerminalService terminalService,
			String newPwd) throws JointException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceDevice(Terminal terminal, String newSerialNum,
			TerminalService terminalService) throws JointException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetDevice(Terminal terminal, TerminalService terminalService)
			throws JointException {
		// TODO Auto-generated method stub
		
	}

}
