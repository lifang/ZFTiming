package com.comdosoft.financial.manage.joint.zhonghui.transactions;

public class LastTenTrans extends TransactionAction {

	public LastTenTrans(String phoneNum, String password, String position) {
		super(phoneNum, password, position);
	}

	@Override
	protected String url() {
		return "/transactions";
	}

}
