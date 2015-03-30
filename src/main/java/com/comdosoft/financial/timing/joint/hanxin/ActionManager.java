package com.comdosoft.financial.timing.joint.hanxin;

import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;
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
	public String syncStatus(String account, String passwd, String serialNum) {
		LoginRequest request = new LoginRequest();
		request.setAccountName(account);
		request.setAccountPwd(passwd);
		request.setTerminalId(serialNum);
		LoginRequest.LoginResponse response = (LoginRequest.LoginResponse)acts(request);
		if(response==null) {
			return null;
		}
		return response.getAccountStatus();
	}

}
