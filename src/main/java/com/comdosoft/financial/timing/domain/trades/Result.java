package com.comdosoft.financial.timing.domain.trades;

public class Result {
	private Integer total;
	private ResultList[] list;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public ResultList[] getList() {
		return list;
	}

	public void setList(ResultList[] list) {
		this.list = list;
	}

}
