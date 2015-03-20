package com.comdosoft.financial.manage.joint.zhonghui.transactions;

import java.text.MessageFormat;

public class NoTrans extends TransactionAction {
	
	private int respNo;

	public NoTrans(String phoneNum, String password, String position,int respNo) {
		super(phoneNum, password, position);
		this.respNo = respNo;
	}

	@Override
	protected String url() {
		return MessageFormat.format("/transactions/R{0}", respNo);
	}

}
