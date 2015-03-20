package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登陆
 * @author wu
 *
 */
public class LoginAction extends Action {
	
	private String phoneNum;
	private String password;
	private String position;
	private String appVersion;
	private String product;
	
	public LoginAction(String phoneNum, String password, String position,
			String appVersion, String product) {
		this.phoneNum = phoneNum;
		this.password = password;
		this.position = position;
		this.appVersion = appVersion;
		this.product = product;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("loginName", phoneNum);
		params.put("password", password);
		params.put("position", position);
		params.put("appVersion", appVersion);
		params.put("product", product);
		return params;
	}

	@Override
	public String url() {
		return "/user/login";
	}

	@Override
	protected Result parseResult(Map<String, String> headers,
			InputStream stream) {
		LoginResult result = (LoginResult)super.parseResult(headers, stream);
		if(result != null) {
			String session = headers.get("WSHSNO");
			result.session = session;
		}
		return result;
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return LoginResult.class;
	}
	
	public static class LoginResult extends Result {
		private String session;
		private String status;
		private String name;
		private String cardTail;
		private String ksnNo;
		private String sessionKey;
		private String keyCheck;
		private String model;
		private String serialType;
		private int nextReqNo;
		private String bluetoothName;
		private boolean isBluetooth;
		private Map<String,Object> argument;
		private AtomicInteger atomicReqNo;

		public String getSession() {
			return session;
		}

		public String getStatus() {
			return status;
		}

		public String getName() {
			return name;
		}

		public String getCardTail() {
			return cardTail;
		}

		public String getKsnNo() {
			return ksnNo;
		}

		public String getSessionKey() {
			return sessionKey;
		}

		public String getKeyCheck() {
			return keyCheck;
		}

		public String getModel() {
			return model;
		}

		public String getSerialType() {
			return serialType;
		}

		public int getNextReqNo() {
			if(atomicReqNo == null) {
				atomicReqNo = new AtomicInteger(nextReqNo);
			}
			return atomicReqNo.getAndIncrement();
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setCardTail(String cardTail) {
			this.cardTail = cardTail;
		}

		public void setKsnNo(String ksnNo) {
			this.ksnNo = ksnNo;
		}

		public void setSessionKey(String sessionKey) {
			this.sessionKey = sessionKey;
		}

		public void setKeyCheck(String keyCheck) {
			this.keyCheck = keyCheck;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public void setSerialType(String serialType) {
			this.serialType = serialType;
		}

		public void setNextReqNo(int nextReqNo) {
			this.nextReqNo = nextReqNo;
		}

		public String getBluetoothName() {
			return bluetoothName;
		}

		public void setBluetoothName(String bluetoothName) {
			this.bluetoothName = bluetoothName;
		}

		public boolean getIsBluetooth() {
			return isBluetooth;
		}

		public void setIsBluetooth(boolean isBluetooth) {
			this.isBluetooth = isBluetooth;
		}

		public Map<String, Object> getArgument() {
			return argument;
		}

		public void setArgument(Map<String, Object> argument) {
			this.argument = argument;
		}
	}
}
