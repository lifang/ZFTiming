package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
public class EnquiryRequest extends RequestBean {

	public EnquiryRequest() {
		super("Enquiry");
	}
	
	private String merchantId;
	private String platformId;
	private String transType;
	private String merchantOrderId;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	@Override
	public Class<? extends JointResponse> getResponseType() {
		return EnquiryResponse.class;
	}

	@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
	public static class EnquiryResponse extends ResponseBean {
		
		private String merchantName;
		private String transId;
		private String transAmt;
		private String transTime;
		private String currency;//交易币种
		private String issueBank;//发卡行名称
		private String orderStatus;
		
		public String getMerchantName() {
			return merchantName;
		}
		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}
		public String getTransId() {
			return transId;
		}
		public void setTransId(String transId) {
			this.transId = transId;
		}
		public String getTransAmt() {
			return transAmt;
		}
		public void setTransAmt(String transAmt) {
			this.transAmt = transAmt;
		}
		public String getTransTime() {
			return transTime;
		}
		public void setTransTime(String transTime) {
			this.transTime = transTime;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getIssueBank() {
			return issueBank;
		}
		public void setIssueBank(String issueBank) {
			this.issueBank = issueBank;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
	}
}
