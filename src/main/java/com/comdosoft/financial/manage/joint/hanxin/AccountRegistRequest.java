package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
public class AccountRegistRequest extends RequestBean {

	public AccountRegistRequest() {
		super("AccountRegist");
	}
	
	private String merchantName;//商户名
	private String legalManName;//法人姓名
	private String legalManIdcard;//法人身份证号
	private String mobileNum;//手机号
	private String personalMerRegNo;//营业执照
	private String taxNo;//税务登记号
	private String occNo;//组织代码号
	private String settleAccountType;//结算账户类型
	private String settleAccount;//结算账户名
	private String settleAccountNo;//结算账户号
	private String settleAgency;//开户行
	private String accountPwd;//密码
	private String terminalInFo;//终端信息

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getLegalManName() {
		return legalManName;
	}

	public void setLegalManName(String legalManName) {
		this.legalManName = legalManName;
	}

	public String getLegalManIdcard() {
		return legalManIdcard;
	}

	public void setLegalManIdcard(String legalManIdcard) {
		this.legalManIdcard = legalManIdcard;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getPersonalMerRegNo() {
		return personalMerRegNo;
	}

	public void setPersonalMerRegNo(String personalMerRegNo) {
		this.personalMerRegNo = personalMerRegNo;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getOccNo() {
		return occNo;
	}

	public void setOccNo(String occNo) {
		this.occNo = occNo;
	}

	public String getSettleAccountType() {
		return settleAccountType;
	}

	public void setSettleAccountType(String settleAccountType) {
		this.settleAccountType = settleAccountType;
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

	public String getSettleAgency() {
		return settleAgency;
	}

	public void setSettleAgency(String settleAgency) {
		this.settleAgency = settleAgency;
	}

	public String getAccountPwd() {
		return accountPwd;
	}

	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}

	public String getTerminalInFo() {
		return terminalInFo;
	}

	public void setTerminalInFo(String terminalInFo) {
		this.terminalInFo = terminalInFo;
	}

	@Override
	public Class<? extends JointResponse> getResponseType() {
		return AccountRegistResponse.class;
	}

	@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
	public static class AccountRegistResponse extends ResponseBean{
		private String merchantId;//商户代码
		private String accountName;
		private String terminalInFo;
		private String occNo;

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

		public String getTerminalInFo() {
			return terminalInFo;
		}

		public void setTerminalInFo(String terminalInFo) {
			this.terminalInFo = terminalInFo;
		}

		public String getOccNo() {
			return occNo;
		}

		public void setOccNo(String occNo) {
			this.occNo = occNo;
		}
	}
}
