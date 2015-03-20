package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.Map;

public class PwdResetAction extends Action {
	
	private String accountNo;
	private String ksnNo;
	private String mobile;
	private String product;
	private String appVersion;
	private boolean requireBasekey;

	public PwdResetAction(String accountNo, String ksnNo, String mobile,
			String product, String appVersion, boolean requireBasekey) {
		this.accountNo = accountNo;
		this.ksnNo = ksnNo;
		this.mobile = mobile;
		this.product = product;
		this.appVersion = appVersion;
		this.requireBasekey = requireBasekey;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("accountNo",accountNo);
		params.put("ksnNo",ksnNo);
		params.put("mobile",mobile);
		params.put("product",product);
		params.put("appVersion",appVersion);
		params.put("requireBasekey",Boolean.toString(requireBasekey));
		return params;
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return ResetPwdResult.class;
	}

	@Override
	protected String url() {
		return "/user/resetPassword";
	}

	public static class ResetPwdResult extends Result{
		private String baseKey;
		private String keyCheck;
		private String model;
		
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
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
	}
}
