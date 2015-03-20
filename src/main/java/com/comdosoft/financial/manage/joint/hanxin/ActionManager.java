package com.comdosoft.financial.manage.joint.hanxin;

import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.JointHandler;
import com.comdosoft.financial.manage.joint.JointManager;
import com.comdosoft.financial.manage.joint.JointRequest;
import com.comdosoft.financial.manage.joint.JointResponse;
import com.comdosoft.financial.manage.utils.HttpUtils;

public class ActionManager implements JointManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ActionManager.class);
	
	private HttpClientContext context = HttpClientContext.create();
	private String url;
	private String rsaKey;
	
	@Override
	public void acts(JointRequest request, JointHandler handler) {
		if(!(request instanceof RequestBean)){
			throw new IllegalArgumentException();
		}
		RequestBean bean = (RequestBean)request;
		LOG.debug("request bean:...{}",bean.toString());
		try {
			String sendData = bean.generateBody(this);
			JointResponse response = HttpUtils.post(url, sendData, context, bean);
			handler.handle(response);
		} catch (Exception e) {
			LOG.error("",e);
		}
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

}
