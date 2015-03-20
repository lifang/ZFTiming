package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.Map;

public class DeviceResetAction extends RequireLoginAction {
	
	private String idNumber;
	private String ksnNo;

	public DeviceResetAction(String phoneNum, String password, String position,
			String idNumber, String ksnNo) {
		super(phoneNum, password, position);
		this.idNumber = idNumber;
		this.ksnNo = ksnNo;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("idNumber", idNumber);
		params.put("ksnNo", ksnNo);
		return params;
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return DeviceResetResult.class;
	}

	@Override
	protected String url() {
		return "/swiper/reset";
	}
	
	public static class DeviceResetResult extends Result{
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
