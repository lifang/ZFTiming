package com.comdosoft.financial.timing.joint.qiandai;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.timing.controller.api.QiandaibaoController;
import com.comdosoft.financial.timing.domain.trades.ResultInfo;
import com.comdosoft.financial.timing.domain.trades.ResultList;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.joint.JointException;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.utils.StringUtils;
import com.comdosoft.financial.timing.utils.page.Page;
import com.comdosoft.financial.timing.utils.page.PageRequest;

public class TransRecordQueryActionManager  implements JointManager{

	private static final String MD5key = "AB14EF83C9204C268CA764AAF49D4D787C025837%$#@$&^%$@5610216-428D8A82-090E25849C03";
	
	private static final Logger LOG = LoggerFactory.getLogger(TransRecordQueryActionManager.class);
	
	@Override
	public JointResponse acts(JointRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String syncStatus(Terminal terminal, TerminalService terminalService) {
		return null;
	}

	@Override
	public Page<Bank> bankList(String keyword, PageRequest request,
			String serialNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<TradeRecord> pullTrades(Terminal terminal, Integer tradeTypeId,
			PageRequest request){
		QiandaibaoController qiandaoHandler = new QiandaibaoController();
		List<TradeRecord> records = new ArrayList<TradeRecord>();
		String eqno = terminal.getSerialNum();
		String querytype = "2";
		String begintime = "";
		String endtime = "";
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
		ResultInfo resultInfo = (ResultInfo) StringUtils.parseJSONStringToObject(re, ResultInfo.class);				
		ResultList[] result = resultInfo.getResult().getList();
		
		for(ResultList res : result){
			TradeRecord tradeRecord = new TradeRecord();
			tradeRecord.setAmount(res.getAmount());
			tradeRecord.setId(res.getId());
			tradeRecord.setPoundage(res.getPoundage());
			tradeRecord.setTradedStatus(Integer.valueOf(res.getTradedStatus()));
			tradeRecord.setTradeNumber(res.getTrade_number());
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
