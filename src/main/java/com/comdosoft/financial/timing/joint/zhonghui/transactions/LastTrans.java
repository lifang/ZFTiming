package com.comdosoft.financial.timing.joint.zhonghui.transactions;

public class LastTrans extends TransactionAction {

	public LastTrans(String phoneNum, String password, String position, String appVersion) {
		super(phoneNum, password, position, appVersion);
	}

	@Override
	protected String url() {
		return "/transactions/last";
	}

}
