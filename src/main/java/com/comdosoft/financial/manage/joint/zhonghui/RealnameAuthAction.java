package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
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
	private File personal;
	private File personalBack;

	public RealnameAuthAction(String phoneNum, String password, String position,
			String name, String idNumber, File personal, File personalBack) {
		super(phoneNum, password, position);
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
	protected Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
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

}
