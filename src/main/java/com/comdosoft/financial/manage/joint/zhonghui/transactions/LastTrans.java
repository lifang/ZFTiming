package com.comdosoft.financial.manage.joint.zhonghui.transactions;

public class LastTrans extends TransactionAction {

	public LastTrans(String phoneNum, String password, String position) {
		super(phoneNum, password, position);
	}

	@Override
	protected String url() {
		return "/transactions/last";
	}

}
