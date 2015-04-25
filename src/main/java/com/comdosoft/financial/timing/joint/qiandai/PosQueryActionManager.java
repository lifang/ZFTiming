package com.comdosoft.financial.timing.joint.qiandai;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.comdosoft.financial.timing.controller.api.QiandaibaoController;
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

public class PosQueryActionManager implements JointManager{
	
	@Value("${MD5key}")
	private String MD5key;

	@Override
	public JointResponse acts(JointRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String syncStatus(Terminal terminal, TerminalService terminalService) {
		QiandaibaoController qiandaoHandler = new QiandaibaoController();
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

	@Override
	public Page<TradeRecord> pullTrades(Terminal terminal, Integer tradeTypeId,
			PageRequest request) {
		// TODO Auto-generated method stub
		return null;
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
