package com.comdosoft.financial.manage.joint.zhonghui.transactions;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.manage.joint.zhonghui.RequireLoginAction;
import com.comdosoft.financial.manage.joint.zhonghui.Result;

public abstract class TransactionAction extends RequireLoginAction {

	public TransactionAction(String phoneNum, String password, String position) {
		super(phoneNum, password, position);
	}

	@Override
	protected Map<String, String> params() {
		return null;
	}

	@Override
	protected Method getMethod() {
		return Method.GET;
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return TransResult.class;
	}

	public static class TransResult extends Result{
		private String merchantName;
		private String merchantNo;
		private String terminalNo;
		private List<Transaction> transactions;
		
		public String getMerchantName() {
			return merchantName;
		}
		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}
		public String getMerchantNo() {
			return merchantNo;
		}
		public void setMerchantNo(String merchantNo) {
			this.merchantNo = merchantNo;
		}
		public String getTerminalNo() {
			return terminalNo;
		}
		public void setTerminalNo(String terminalNo) {
			this.terminalNo = terminalNo;
		}
		public List<Transaction> getTransactions() {
			return transactions;
		}
		public void setTransactions(List<Transaction> transactions) {
			this.transactions = transactions;
		}
	}
}
