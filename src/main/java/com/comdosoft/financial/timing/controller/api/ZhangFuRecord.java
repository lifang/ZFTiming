package com.comdosoft.financial.timing.controller.api;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="request")
public class ZhangFuRecord {
	
	private String version;
	private String keyDeviceSerialNo;//终端编号
	private String payCardNo;//付款卡号
	private String userPhone;//预留手机号码
	private String partnerNo;//交易批次号
	private String orderId;//交易流水号
	private String createTime;//交易时间
	private String finalTime;//最后修改时间，更新时间
	private Transaction transaction;
	private Result result;
	private String remark;
	private String signature;
	
	@XmlAttribute
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getKeyDeviceSerialNo() {
		return keyDeviceSerialNo;
	}
	public void setKeyDeviceSerialNo(String keyDeviceSerialNo) {
		this.keyDeviceSerialNo = keyDeviceSerialNo;
	}
	public String getPayCardNo() {
		return payCardNo;
	}
	public void setPayCardNo(String payCardNo) {
		this.payCardNo = payCardNo;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getPartnerNo() {
		return partnerNo;
	}
	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFinalTime() {
		return finalTime;
	}
	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public static class Transaction{
		private String type;
		private String qdOrderId;//系统流水号
		private String feePhone;//到账通知短信
		private String faceValue;//面值，充值金额
		private String price;//支付金额
		private String rebateMoney;//分润返回金额
		private String toAccount;
		private String transferAmount;
		
		@XmlAttribute
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getQdOrderId() {
			return qdOrderId;
		}
		public void setQdOrderId(String qdOrderId) {
			this.qdOrderId = qdOrderId;
		}
		public String getFeePhone() {
			return feePhone;
		}
		public void setFeePhone(String feePhone) {
			this.feePhone = feePhone;
		}
		public String getFaceValue() {
			return faceValue;
		}
		public void setFaceValue(String faceValue) {
			this.faceValue = faceValue;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getRebateMoney() {
			return rebateMoney;
		}
		public void setRebateMoney(String rebateMoney) {
			this.rebateMoney = rebateMoney;
		}
		public String getToAccount() {
			return toAccount;
		}
		public void setToAccount(String toAccount) {
			this.toAccount = toAccount;
		}
		public String getTransferAmount() {
			return transferAmount;
		}
		public void setTransferAmount(String transferAmount) {
			this.transferAmount = transferAmount;
		}
	}
	
	public static class Result{
		private String payStatus;//支付状态 1表示成功 其他表示失败
		private String payResultCode;//支付返回码
		private String payResultDes;//支付时返回结果
		private String deliveryStatus;//到账（结算）状态
		private String deliveryResultCode;//到账（结算）返回码
		private String deliveryResultDes;//到账（结算）描述信息		
		private String refundStatus;//退款状态 1表示成功 0 表示失败或者 未发生退款
		private String refundResultCode;//退款结果 返回码
		private String refundResultDes;//退款结果 信息描述
		
		public String getPayStatus() {
			return payStatus;
		}
		public void setPayStatus(String payStatus) {
			this.payStatus = payStatus;
		}
		public String getPayResultCode() {
			return payResultCode;
		}
		public void setPayResultCode(String payResultCode) {
			this.payResultCode = payResultCode;
		}
		public String getPayResultDes() {
			return payResultDes;
		}
		public void setPayResultDes(String payResultDes) {
			this.payResultDes = payResultDes;
		}
		public String getDeliveryStatus() {
			return deliveryStatus;
		}
		public void setDeliveryStatus(String deliveryStatus) {
			this.deliveryStatus = deliveryStatus;
		}
		public String getDeliveryResultCode() {
			return deliveryResultCode;
		}
		public void setDeliveryResultCode(String deliveryResultCode) {
			this.deliveryResultCode = deliveryResultCode;
		}
		public String getDeliveryResultDes() {
			return deliveryResultDes;
		}
		public void setDeliveryResultDes(String deliveryResultDes) {
			this.deliveryResultDes = deliveryResultDes;
		}
		public String getRefundStatus() {
			return refundStatus;
		}
		public void setRefundStatus(String refundStatus) {
			this.refundStatus = refundStatus;
		}
		public String getRefundResultCode() {
			return refundResultCode;
		}
		public void setRefundResultCode(String refundResultCode) {
			this.refundResultCode = refundResultCode;
		}
		public String getRefundResultDes() {
			return refundResultDes;
		}
		public void setRefundResultDes(String refundResultDes) {
			this.refundResultDes = refundResultDes;
		}
	}
}
