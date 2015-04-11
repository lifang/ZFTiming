package com.comdosoft.financial.timing.joint.zhonghui;

import java.io.InputStream;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 商户认证
 * @author wu
 *
 */
public class MerchantAuthAction extends RequireLoginAction{
	
	private String companyName;
	private String regPlace;
	private String businessLicense;
	private InputStream business;
	private InputStream businessPlace;
	private InputStream cashierDesk;

	public MerchantAuthAction(String phoneNum, String password, String position, String appVersion,
			String companyName, String regPlace,
			String businessLicense,InputStream business, InputStream businessPlace, InputStream cashierDesk) {
		super(phoneNum, password, position, appVersion);
		this.companyName = companyName;
		this.regPlace = regPlace;
		this.businessLicense = businessLicense;
		this.business = business;
		this.businessPlace = businessPlace;
		this.cashierDesk = cashierDesk;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("companyName", companyName);
		params.put("regPlace", regPlace);
		params.put("businessLicense", businessLicense);
		return params;
	}

	@Override
	protected Map<String, InputStream> fileParams() {
		Map<String, InputStream> fileParams = Maps.newHashMap();
		fileParams.put("business", business);
		fileParams.put("businessPlace", businessPlace);
		fileParams.put("cashierDesk", cashierDesk);
		return fileParams;
	}

	@Override
	public String url() {
		return "/user/merchantAuth";
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return Result.class;
	}

	@Override
	protected int checkIndex() {
		return 1;
	}
}
