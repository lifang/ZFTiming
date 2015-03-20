package com.comdosoft.financial.manage.joint.zhonghui;

import java.util.Map;

public class PwdChangeAction extends RequireLoginAction {
	
	private String newPassword;// 登录名或者手机号码
	private String password;

	public PwdChangeAction(String phoneNum, String password, String position,
			String newPassword) {
		super(phoneNum, password, position);
		this.newPassword = newPassword;
		this.password = password;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("password", password);
		params.put("newPassword", newPassword);
		return params;
	}

	@Override
	public String url() {
		return "/user/changePassword";
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return Result.class;
	}

}
