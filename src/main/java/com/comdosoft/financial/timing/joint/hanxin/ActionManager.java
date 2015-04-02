package com.comdosoft.financial.timing.joint.hanxin;

import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalTradeTypeInfo;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.utils.HttpUtils;

public class ActionManager implements JointManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ActionManager.class);
	
	private HttpClientContext context = HttpClientContext.create();
	private String url;
	private String rsaKey;
	
	@Override
	public JointResponse acts(JointRequest request) {
		if(!(request instanceof RequestBean)){
			throw new IllegalArgumentException();
		}
		RequestBean bean = (RequestBean)request;
		LOG.debug("request bean:...{}",bean.toString());
		try {
			String sendData = bean.generateBody(this);
			return HttpUtils.post(url, sendData, context, bean);
		} catch (Exception e) {
			LOG.error("",e);
		}
		return null;
	}
	
	public JointRequest createLogin(String phoneNum, String password, String terminalId) {
		LoginRequest request = new LoginRequest();
		request.setAccountName(phoneNum);
		request.setAccountPwd(password);
		request.setTerminalId(terminalId);
		return request;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setRsaKey(String rsaKey) {
		this.rsaKey = rsaKey;
	}

	public String getRsaKey() {
		return rsaKey;
	}

	@Override
	public String syncStatus(String account, String passwd,
			Terminal terminal, TerminalService terminalService) {
		LoginRequest request = new LoginRequest();
		request.setAccountName(account);
		request.setAccountPwd(passwd);
		request.setTerminalId(terminal.getSerialNum());
		LoginRequest.LoginResponse response = (LoginRequest.LoginResponse)acts(request);
		if(response==null) {
			return null;
		}
		String accountStatus = response.getAccountStatus();
		Integer intStatus = Integer.valueOf(accountStatus);
		if(intStatus == 0){//审核中
			//不做处理
		}
		
		OpeningApplie oa = terminalService.findOpeningAppylByTerminalId(terminal.getId());
		if(intStatus == 1) {//审核成功
			oa.setStatus(OpeningApplie.STATUS_CHECK_SUCCESS);
			terminalService.updateOpeningApply(oa);
			terminal.setStatus(Terminal.STATUS_OPENED);
			terminalService.updateTerminal(terminal);
			terminalService.updateTerminalTradeTypeStatus(
					TerminalTradeTypeInfo.STATUS_OPENED, terminal.getId());
		}
		
		if(intStatus == 2) {//审核失败
			oa.setStatus(OpeningApplie.STATUS_CHECK_FAIL);
			terminalService.updateOpeningApply(oa);
			terminal.setStatus(Terminal.STATUS_NO_OPEN);
			terminalService.updateTerminal(terminal);
		}
		
		return accountStatus;
	}

}
