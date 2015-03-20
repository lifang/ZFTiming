package com.comdosoft.financial.manage.joint.hanxin;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

/**
 * 交易列表
 * @author wu
 *
 */
@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
public class EnquiryListRequest extends RequestBean {

	public EnquiryListRequest() {
		super("EnquiryList");
	}
	
	private String platformId;//平台代码
	private String merchantId;//商户代码
	private String transType;//交易类型码
	private String transId;//易流水号
	private String beginTime;//交易开始日期
	private String endTime;//交易结束日期
	private int pageCount = 10;//每页显示的数量
	private int curPage = 1;//当前页数

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	@Override
	public Class<? extends JointResponse> getResponseType() {
		return EnquiryListResponse.class;
	}

	@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
	public static class EnquiryListResponse extends ResponseBean {
		
		private String merchantId;
		private String merchantName;
		private String transType;//交易类型码
		private String transId;//易流水号
		private String beginTime;//交易开始日期
		private String endTime;//交易结束日期
		private List<OrderInfo> orderInfos;
		private int pageCount;
		private int curPage;
		private int totalCount;
		
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
		public String getTransId() {
			return transId;
		}
		public void setTransId(String transId) {
			this.transId = transId;
		}
		public int getPageCount() {
			return pageCount;
		}
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}
		public int getCurPage() {
			return curPage;
		}
		public void setCurPage(int curPage) {
			this.curPage = curPage;
		}
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		@XmlElementWrapper(name="List")
		@XmlElement(name="orderInfo")
		public List<OrderInfo> getOrderInfos() {
			return orderInfos;
		}
		public void setOrderInfos(List<OrderInfo> orderInfos) {
			this.orderInfos = orderInfos;
		}
		public String getTransType() {
			return transType;
		}
		public void setTransType(String transType) {
			this.transType = transType;
		}
		public String getBeginTime() {
			return beginTime;
		}
		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
	}
	
	public static class OrderInfo{
		private String transType;
		private String transId;
		private String merchantOrderId;
		private String transAmt;
		private String transTime;
		private String currency;
		
		public String getTransType() {
			return transType;
		}
		public void setTransType(String transType) {
			this.transType = transType;
		}
		public String getTransId() {
			return transId;
		}
		public void setTransId(String transId) {
			this.transId = transId;
		}
		public String getMerchantOrderId() {
			return merchantOrderId;
		}
		public void setMerchantOrderId(String merchantOrderId) {
			this.merchantOrderId = merchantOrderId;
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
	}
}
