package com.comdosoft.financial.timing.domain.trades;

public class ResultList {
	private Float amount;
	private Integer id;
	private Float poundage;
	private String payIntoAccount;
	private String tradedStatus;
	private String tradeNumber;
	private String tradedTimeStr;
	private String payFromAccount;
	private String terminalNumber;

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getPoundage() {
		return poundage;
	}

	public void setPoundage(Float poundage) {
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

	public String getTradeNumber() {
		return tradeNumber;
	}

	public void setTradeNumber(String tradeNumber) {
		this.tradeNumber = tradeNumber;
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
