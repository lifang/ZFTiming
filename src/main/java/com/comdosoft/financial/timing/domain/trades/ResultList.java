package com.comdosoft.financial.timing.domain.trades;

public class ResultList {
	private Integer amount;
	private Integer id;
	private Integer poundage;
	private String payIntoAccount;
	private String tradedStatus;
	private String trade_number;
	private String tradedTimeStr;
	private String payFromAccount;
	private String terminalNumber;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPoundage() {
		return poundage;
	}

	public void setPoundage(Integer poundage) {
		this.poundage = poundage;
	}

	public String getPayIntoAccount() {
		return payIntoAccount;
	}

	public void setPayIntoAccount(String payIntoAccount) {
		this.payIntoAccount = payIntoAccount;
	}

	public String getTradedStatus() {
		return tradedStatus;
	}

	public void setTradedStatus(String tradedStatus) {
		this.tradedStatus = tradedStatus;
	}

	public String getTrade_number() {
		return trade_number;
	}

	public void setTrade_number(String trade_number) {
		this.trade_number = trade_number;
	}

	public String getTradedTimeStr() {
		return tradedTimeStr;
	}

	public void setTradedTimeStr(String tradedTimeStr) {
		this.tradedTimeStr = tradedTimeStr;
	}

	public String getPayFromAccount() {
		return payFromAccount;
	}

	public void setPayFromAccount(String payFromAccount) {
		this.payFromAccount = payFromAccount;
	}

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

}
