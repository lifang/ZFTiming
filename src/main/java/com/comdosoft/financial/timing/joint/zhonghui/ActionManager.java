package com.comdosoft.financial.timing.joint.zhonghui;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;

public class ActionManager implements JointManager{
	
	private static final Logger LOG = LoggerFactory.getLogger(ActionManager.class);
	
	private String baseUrl;
	private String product;
	private Map<String,String> zhProductType;

	public void setZhProductType(Map<String, String> zhProductType) {
		this.zhProductType = zhProductType;
	}

	@Override
	public JointResponse acts(JointRequest request) {
		if(!(request instanceof Action)){
			throw new IllegalArgumentException();
		}
		Action action = (Action)request;
		try {
			return action.process(this);
		} catch (IOException e) {
			LOG.error("",e);
		}
		return null;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProduct() {
		return product;
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#syncStatus(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String syncStatus(String account, String passwd, String serialNum) {
		String type = null;
		LoginAction login = new LoginAction(account, passwd, null, zhProductType.get(type), serialNum);
		LoginAction.LoginResult result = (LoginAction.LoginResult)acts(login);
		if(result == null) {
			return null;
		}
		return result.getStatus();
	}

}
