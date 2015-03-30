package com.comdosoft.financial.timing.joint.zhonghui.transactions;

public class LastTenTrans extends TransactionAction {

	public LastTenTrans(String phoneNum, String password, String position, String appVersion) {
		super(phoneNum, password, position, appVersion);
	}

	@Override
	protected String url() {
		return "/transactions";
	}

}
