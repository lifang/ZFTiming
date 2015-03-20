package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
public class LoginRequest extends RequestBean {
	
	private String merchantId;
	private String accountName;
	private String accountPwd;
	
	public LoginRequest() {
		super("AccountLogIn");
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountPwd() {
		return accountPwd;
	}
	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}
	
	@Override
	public Class<? extends JointResponse> getResponseType() {
		return LoginResponse.class;
	}
	
	@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
	public static class LoginResponse extends ResponseBean {
		
		private String merchantId;
		private String merchantName;//商户名
		private String accountName;//账户名
		private String accountPwd;
		private String personalMerRegNo;//个体工商信息
		private String taxNo;//税务登记号
		private String legalManIdcard;//法人身份证号
		private int settleAccountType;//结算账户类型
		private String settleAccount;//结算账户名
		private String settleAccountNo;//结算账号
		private String settleAgency;//开户行
		private int accountStatus;//开户状态
		private int authStatus;//认证状态
		private String factoryId;//厂商号
		
		public String getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
		
		public String getMerchantName() {
			return merchantName;
		}
		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}
		
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		
		public String getPersonalMerRegNo() {
			return personalMerRegNo;
		}
		public void setPersonalMerRegNo(String personalMerRegNo) {
			this.personalMerRegNo = personalMerRegNo;
		}
		
		public String getLegalManIdcard() {
			return legalManIdcard;
		}
		public void setLegalManIdcard(String legalManIdcard) {
			this.legalManIdcard = legalManIdcard;
		}
		
		public String getSettleAccount() {
			return settleAccount;
		}
		public void setSettleAccount(String settleAccount) {
			this.settleAccount = settleAccount;
		}
		
		public String getSettleAccountNo() {
			return settleAccountNo;
		}
		public void setSettleAccountNo(String settleAccountNo) {
			this.settleAccountNo = settleAccountNo;
		}
		
		public String getAccountPwd() {
			return accountPwd;
		}
		public void setAccountPwd(String accountPwd) {
			this.accountPwd = accountPwd;
		}
		public String getTaxNo() {
			return taxNo;
		}
		public void setTaxNo(String taxNo) {
			this.taxNo = taxNo;
		}
		public String getSettleAgency() {
			return settleAgency;
		}
		public void setSettleAgency(String settleAgency) {
			this.settleAgency = settleAgency;
		}
		public String getFactoryId() {
			return factoryId;
		}
		public void setFactoryId(String factoryId) {
			this.factoryId = factoryId;
		}
		public int getSettleAccountType() {
			return settleAccountType;
		}
		public void setSettleAccountType(int settleAccountType) {
			this.settleAccountType = settleAccountType;
		}
		public int getAccountStatus() {
			return accountStatus;
		}
		public void setAccountStatus(int accountStatus) {
			this.accountStatus = accountStatus;
		}
		public int getAuthStatus() {
			return authStatus;
		}
		public void setAuthStatus(int authStatus) {
			this.authStatus = authStatus;
		}
		
	}
}
