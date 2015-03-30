package com.comdosoft.financial.timing.joint.zhonghui.transactions;

import java.text.MessageFormat;

public class NoTrans extends TransactionAction {
	
	private int respNo;

	public NoTrans(String phoneNum, String password, String position, String appVersion,int respNo) {
		super(phoneNum, password, position, appVersion);
		this.respNo = respNo;
	}

	@Override
	protected String url() {
		return MessageFormat.format("/transactions/R{0}", respNo);
	}

}
