package com.comdosoft.financial.timing.joint.zhonghui;

import java.io.InputStream;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 实名认证
 * @author wu
 *
 */
public class RealnameAuthAction extends RequireLoginAction {
	
	private String name;
	private String idNumber;
	private InputStream personal;
	private InputStream personalBack;

	public RealnameAuthAction(String phoneNum, String password, String position, String appVersion,
			String name, String idNumber, InputStream personal, InputStream personalBack) {
		super(phoneNum, password, position, appVersion);
		this.name = name;
		this.idNumber = idNumber;
		this.personal = personal;
		this.personalBack = personalBack;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("name",name);
		params.put("idNumber", idNumber);
		return params;
	}

	@Override
	protected Map<String, InputStream> fileParams() {
		Map<String, InputStream> fileParams = Maps.newHashMap();
		fileParams.put("personal", personal);
		fileParams.put("personalBack", personalBack);
		return fileParams;
	}

	@Override
	public String url() {
		return "/user/realnameAuth";
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return Result.class;
	}

	@Override
	protected int checkIndex() {
		return 0;
	}

}
