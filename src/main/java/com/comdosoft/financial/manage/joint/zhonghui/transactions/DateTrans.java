package com.comdosoft.financial.manage.joint.zhonghui.transactions;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTrans extends TransactionAction {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	private Date date;
	
	public DateTrans(String phoneNum, String password, String position,
			Date date) {
		super(phoneNum, password, position);
		this.date = date;
	}

	@Override
	protected String url() {
		return MessageFormat.format("/transactions/{0}", formatter.format(date.toInstant()));
	}

}
