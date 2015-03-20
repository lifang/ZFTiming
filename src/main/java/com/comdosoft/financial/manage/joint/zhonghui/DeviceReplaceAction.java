package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.Map;

public class DeviceReplaceAction extends RequireLoginAction {
	
	private String ksnNo;
	private String model;
	
	public DeviceReplaceAction(String phoneNum, String password, String position,
			String ksnNo, String model) {
		super(phoneNum, password, position);
		this.ksnNo = ksnNo;
		this.model = model;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("ksnNo", ksnNo);
		params.put("model", model);
		return params;
	}

	@Override
	public String url() {
		return "/swiper/change";
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return ReplaceDeviceResult.class;
	}
	
	public static class ReplaceDeviceResult extends Result {
		private String baseKey;
		private String keyCheck;
		
		public String getBaseKey() {
			return baseKey;
		}
		public void setBaseKey(String baseKey) {
			this.baseKey = baseKey;
		}
		public String getKeyCheck() {
			return keyCheck;
		}
		public void setKeyCheck(String keyCheck) {
			this.keyCheck = keyCheck;
		}
	}

}
