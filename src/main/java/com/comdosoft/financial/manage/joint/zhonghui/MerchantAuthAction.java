package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
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
	private File business;
	private File businessPlace;
	private File cashierDesk;

	public MerchantAuthAction(String phoneNum, String password, String position,
			String companyName, String regPlace,
			String businessLicense,File business, File businessPlace, File cashierDesk) {
		super(phoneNum, password, position);
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
	protected Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
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
}
