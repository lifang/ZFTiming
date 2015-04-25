package com.comdosoft.financial.timing.domain.trades;

public class TransactionRecordQuery {

	private String code;
	
	private String msg;
	
	private String eqno;
	
	//private String agentno;
	
	private String querytype;
	
	private String begintime;
	
	private String endtime;
	
	private String remark;
	
	private OrderList[] orderlist;
	
	private String sign;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEqno() {
		return eqno;
	}

	public void setEqno(String eqno) {
		this.eqno = eqno;
	}

//	public String getAgentno() {
//		return agentno;
//	}
//
//	public void setAgentno(String agentno) {
//		this.agentno = agentno;
//	}

	public String getQuerytype() {
		return querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public OrderList[] getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(OrderList[] orderlist) {
		this.orderlist = orderlist;
	}

}
