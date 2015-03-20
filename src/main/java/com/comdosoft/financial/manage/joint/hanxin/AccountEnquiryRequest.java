package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
public class AccountEnquiryRequest extends RequestBean {

	public AccountEnquiryRequest() {
		super("AccountEnquiry");
	}
	
	private String merchantId;
	private String personalMerRegNo;
	private String taxNo;
	private String occNo;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	@Override
	public Class<? extends JointResponse> getResponseType() {
		return AccountEnquiryResponse.class;
	}

	@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
	public static class AccountEnquiryResponse extends ResponseBean{
		private String merchantId;
		private String merchantName;
		private String legalManName;
		private String legalManIdcard;
		private String mobileNum;
		private String personalMerRegNo;
		private String taxNo;
		private String occNo;
		private String settleAccount;
		private String settleAccountNo;
		private String settleAgency;
		private String merStatus;
		private String authStatus;
		private String settleAccountType;
		
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
		public String getMerStatus() {
			return merStatus;
		}
		public void setMerStatus(String merStatus) {
			this.merStatus = merStatus;
		}
		public String getAuthStatus() {
			return authStatus;
		}
		public void setAuthStatus(String authStatus) {
			this.authStatus = authStatus;
		}
		public String getSettleAccountType() {
			return settleAccountType;
		}
		public void setSettleAccountType(String settleAccountType) {
			this.settleAccountType = settleAccountType;
		}
		
	}
}
