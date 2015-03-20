package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.Map;

/**
 * 刷卡器激活
 */
public class ActivateAction extends Action{
	
	private String licenseCode;
	private String ksnNo;
	private String appVersion;
	private String product;

	public ActivateAction(String licenseCode, String ksnNo, String appVersion,
			String product) {
		this.licenseCode = licenseCode;
		this.ksnNo = ksnNo;
		this.appVersion = appVersion;
		this.product = product;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("licenseCode",licenseCode);
		params.put("ksnNo",ksnNo);
		params.put("appVersion",appVersion);
		params.put("product",product);
		return params;
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return ActivateResult.class;
	}

	@Override
	protected String url() {
		return "/swiper/register";
	}

	public static class ActivateResult extends Result {
		
		private String baseKey;
		private String keyCheck;
		private String serialType;
		private boolean signatureFlag;

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

		public String getSerialType() {
			return serialType;
		}

		public void setSerialType(String serialType) {
			this.serialType = serialType;
		}

		public boolean isSignatureFlag() {
			return signatureFlag;
		}

		public void setSignatureFlag(boolean signatureFlag) {
			this.signatureFlag = signatureFlag;
		}
	}

}
