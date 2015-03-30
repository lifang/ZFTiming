package com.comdosoft.financial.timing.joint.zhonghui.transactions;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTrans extends TransactionAction {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	private Date date;
	
	public DateTrans(String phoneNum, String password, String position, String appVersion,
			Date date) {
		super(phoneNum, password, position, appVersion);
		this.date = date;
	}

	@Override
	protected String url() {
		return MessageFormat.format("/transactions/{0}", formatter.format(date.toInstant()));
	}

}
