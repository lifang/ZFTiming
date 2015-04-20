package com.comdosoft.financial.timing.domain.trades;

import java.util.Date;

import com.comdosoft.financial.timing.domain.zhangfu.Agent;
import com.comdosoft.financial.timing.domain.zhangfu.PayChannel;

public class TradeRecord {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.trade_number
	 * @mbggenerated
	 */
	private String tradeNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.batch_number
	 * @mbggenerated
	 */
	private String batchNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.terminal_number
	 * @mbggenerated
	 */
	private String terminalNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.order_number
	 * @mbggenerated
	 */
	private String orderNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.agent_id
	 * @mbggenerated
	 */
	private Integer agentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.merchant_number
	 * @mbggenerated
	 */
	private String merchantNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.merchant_name
	 * @mbggenerated
	 */
	private String merchantName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.pay_channel_id
	 * @mbggenerated
	 */
	private Integer payChannelId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.profit_price
	 * @mbggenerated
	 */
	private Integer profitPrice;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.amount
	 * @mbggenerated
	 */
	private Integer amount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.poundage
	 * @mbggenerated
	 */
	private Integer poundage;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.traded_at
	 * @mbggenerated
	 */
	private Date tradedAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.sys_order_id
	 * @mbggenerated
	 */
	private String sysOrderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.account_name
	 * @mbggenerated
	 */
	private String accountName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.account_number
	 * @mbggenerated
	 */
	private String accountNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.actual_payment_price
	 * @mbggenerated
	 */
	private Integer actualPaymentPrice;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.paid_result
	 * @mbggenerated
	 */
	private Byte paidResult;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.paid_code
	 * @mbggenerated
	 */
	private Integer paidCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.paid_error_description
	 * @mbggenerated
	 */
	private String paidErrorDescription;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.created_at
	 * @mbggenerated
	 */
	private Date createdAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.updated_at
	 * @mbggenerated
	 */
	private Date updatedAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.trade_type_id
	 * @mbggenerated
	 */
	private Integer tradeTypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.traded_status
	 * @mbggenerated
	 */
	private Integer tradedStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.attach_status
	 * @mbggenerated
	 */
	private Integer attachStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.customer_id
	 * @mbggenerated
	 */
	private Integer customerId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.types
	 * @mbggenerated
	 */
	private Byte types;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.city_id
	 * @mbggenerated
	 */
	private Integer cityId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.integral_status
	 * @mbggenerated
	 */
	private Boolean integralStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column trade_records.pay_from_account
	 * @mbggenerated
	 */
	private String payFromAccount;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.id
	 * @return  the value of trade_records.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.id
	 * @param id  the value for trade_records.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.trade_number
	 * @return  the value of trade_records.trade_number
	 * @mbggenerated
	 */
	public String getTradeNumber() {
		return tradeNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.trade_number
	 * @param tradeNumber  the value for trade_records.trade_number
	 * @mbggenerated
	 */
	public void setTradeNumber(String tradeNumber) {
		this.tradeNumber = tradeNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.batch_number
	 * @return  the value of trade_records.batch_number
	 * @mbggenerated
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.batch_number
	 * @param batchNumber  the value for trade_records.batch_number
	 * @mbggenerated
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.terminal_number
	 * @return  the value of trade_records.terminal_number
	 * @mbggenerated
	 */
	public String getTerminalNumber() {
		return terminalNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.terminal_number
	 * @param terminalNumber  the value for trade_records.terminal_number
	 * @mbggenerated
	 */
	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.order_number
	 * @return  the value of trade_records.order_number
	 * @mbggenerated
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.order_number
	 * @param orderNumber  the value for trade_records.order_number
	 * @mbggenerated
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.agent_id
	 * @return  the value of trade_records.agent_id
	 * @mbggenerated
	 */
	public Integer getAgentId() {
		return agentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.agent_id
	 * @param agentId  the value for trade_records.agent_id
	 * @mbggenerated
	 */
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.merchant_number
	 * @return  the value of trade_records.merchant_number
	 * @mbggenerated
	 */
	public String getMerchantNumber() {
		return merchantNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.merchant_number
	 * @param merchantNumber  the value for trade_records.merchant_number
	 * @mbggenerated
	 */
	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.merchant_name
	 * @return  the value of trade_records.merchant_name
	 * @mbggenerated
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.merchant_name
	 * @param merchantName  the value for trade_records.merchant_name
	 * @mbggenerated
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.pay_channel_id
	 * @return  the value of trade_records.pay_channel_id
	 * @mbggenerated
	 */
	public Integer getPayChannelId() {
		return payChannelId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.pay_channel_id
	 * @param payChannelId  the value for trade_records.pay_channel_id
	 * @mbggenerated
	 */
	public void setPayChannelId(Integer payChannelId) {
		this.payChannelId = payChannelId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.profit_price
	 * @return  the value of trade_records.profit_price
	 * @mbggenerated
	 */
	public Integer getProfitPrice() {
		return profitPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.profit_price
	 * @param profitPrice  the value for trade_records.profit_price
	 * @mbggenerated
	 */
	public void setProfitPrice(Integer profitPrice) {
		this.profitPrice = profitPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.amount
	 * @return  the value of trade_records.amount
	 * @mbggenerated
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.amount
	 * @param amount  the value for trade_records.amount
	 * @mbggenerated
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.poundage
	 * @return  the value of trade_records.poundage
	 * @mbggenerated
	 */
	public Integer getPoundage() {
		return poundage;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.poundage
	 * @param poundage  the value for trade_records.poundage
	 * @mbggenerated
	 */
	public void setPoundage(Integer poundage) {
		this.poundage = poundage;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.traded_at
	 * @return  the value of trade_records.traded_at
	 * @mbggenerated
	 */
	public Date getTradedAt() {
		return tradedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.traded_at
	 * @param tradedAt  the value for trade_records.traded_at
	 * @mbggenerated
	 */
	public void setTradedAt(Date tradedAt) {
		this.tradedAt = tradedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.sys_order_id
	 * @return  the value of trade_records.sys_order_id
	 * @mbggenerated
	 */
	public String getSysOrderId() {
		return sysOrderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.sys_order_id
	 * @param sysOrderId  the value for trade_records.sys_order_id
	 * @mbggenerated
	 */
	public void setSysOrderId(String sysOrderId) {
		this.sysOrderId = sysOrderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.account_name
	 * @return  the value of trade_records.account_name
	 * @mbggenerated
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.account_name
	 * @param accountName  the value for trade_records.account_name
	 * @mbggenerated
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.account_number
	 * @return  the value of trade_records.account_number
	 * @mbggenerated
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.account_number
	 * @param accountNumber  the value for trade_records.account_number
	 * @mbggenerated
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.actual_payment_price
	 * @return  the value of trade_records.actual_payment_price
	 * @mbggenerated
	 */
	public Integer getActualPaymentPrice() {
		return actualPaymentPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.actual_payment_price
	 * @param actualPaymentPrice  the value for trade_records.actual_payment_price
	 * @mbggenerated
	 */
	public void setActualPaymentPrice(Integer actualPaymentPrice) {
		this.actualPaymentPrice = actualPaymentPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.paid_result
	 * @return  the value of trade_records.paid_result
	 * @mbggenerated
	 */
	public Byte getPaidResult() {
		return paidResult;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.paid_result
	 * @param paidResult  the value for trade_records.paid_result
	 * @mbggenerated
	 */
	public void setPaidResult(Byte paidResult) {
		this.paidResult = paidResult;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.paid_code
	 * @return  the value of trade_records.paid_code
	 * @mbggenerated
	 */
	public Integer getPaidCode() {
		return paidCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.paid_code
	 * @param paidCode  the value for trade_records.paid_code
	 * @mbggenerated
	 */
	public void setPaidCode(Integer paidCode) {
		this.paidCode = paidCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.paid_error_description
	 * @return  the value of trade_records.paid_error_description
	 * @mbggenerated
	 */
	public String getPaidErrorDescription() {
		return paidErrorDescription;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.paid_error_description
	 * @param paidErrorDescription  the value for trade_records.paid_error_description
	 * @mbggenerated
	 */
	public void setPaidErrorDescription(String paidErrorDescription) {
		this.paidErrorDescription = paidErrorDescription;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.created_at
	 * @return  the value of trade_records.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.created_at
	 * @param createdAt  the value for trade_records.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.updated_at
	 * @return  the value of trade_records.updated_at
	 * @mbggenerated
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.updated_at
	 * @param updatedAt  the value for trade_records.updated_at
	 * @mbggenerated
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.trade_type_id
	 * @return  the value of trade_records.trade_type_id
	 * @mbggenerated
	 */
	public Integer getTradeTypeId() {
		return tradeTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.trade_type_id
	 * @param tradeTypeId  the value for trade_records.trade_type_id
	 * @mbggenerated
	 */
	public void setTradeTypeId(Integer tradeTypeId) {
		this.tradeTypeId = tradeTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.traded_status
	 * @return  the value of trade_records.traded_status
	 * @mbggenerated
	 */
	public Integer getTradedStatus() {
		return tradedStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.traded_status
	 * @param tradedStatus  the value for trade_records.traded_status
	 * @mbggenerated
	 */
	public void setTradedStatus(Integer tradedStatus) {
		this.tradedStatus = tradedStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.attach_status
	 * @return  the value of trade_records.attach_status
	 * @mbggenerated
	 */
	public Integer getAttachStatus() {
		return attachStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.attach_status
	 * @param attachStatus  the value for trade_records.attach_status
	 * @mbggenerated
	 */
	public void setAttachStatus(Integer attachStatus) {
		this.attachStatus = attachStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.customer_id
	 * @return  the value of trade_records.customer_id
	 * @mbggenerated
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.customer_id
	 * @param customerId  the value for trade_records.customer_id
	 * @mbggenerated
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.types
	 * @return  the value of trade_records.types
	 * @mbggenerated
	 */
	public Byte getTypes() {
		return types;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.types
	 * @param types  the value for trade_records.types
	 * @mbggenerated
	 */
	public void setTypes(Byte types) {
		this.types = types;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.city_id
	 * @return  the value of trade_records.city_id
	 * @mbggenerated
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.city_id
	 * @param cityId  the value for trade_records.city_id
	 * @mbggenerated
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.integral_status
	 * @return  the value of trade_records.integral_status
	 * @mbggenerated
	 */
	public Boolean getIntegralStatus() {
		return integralStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.integral_status
	 * @param integralStatus  the value for trade_records.integral_status
	 * @mbggenerated
	 */
	public void setIntegralStatus(Boolean integralStatus) {
		this.integralStatus = integralStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column trade_records.pay_from_account
	 * @return  the value of trade_records.pay_from_account
	 * @mbggenerated
	 */
	public String getPayFromAccount() {
		return payFromAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column trade_records.pay_from_account
	 * @param payFromAccount  the value for trade_records.pay_from_account
	 * @mbggenerated
	 */
	public void setPayFromAccount(String payFromAccount) {
		this.payFromAccount = payFromAccount;
	}

	public static final int TRADE_STATUS_SUCCESS = 1;//交易成功
    public static final int TRADE_STATUS_FAIL = 2;//交易失败
    public static final int TRADE_STATUS_WAIT_CONFIRM = 3;//交易结果待确认

    public static final int ATTACH_STATUS_NO_CALCULATED = 1;//未计算
    public static final int ATTACH_STATUS_CALCULATED = 2;//已计算
    public static final int ATTACH_STATUS_FAIL = 3;//分润失败

    private Agent agent;
    private PayChannel payChannel;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public PayChannel getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(PayChannel payChannel) {
		this.payChannel = payChannel;
	}

	public String getTradeStatusName(){
		if(tradedStatus == null) {
			return null;
		}
        if(tradedStatus==TradeRecord.TRADE_STATUS_SUCCESS){
            return "交易成功";
        }
        if(tradedStatus==TradeRecord.TRADE_STATUS_FAIL){
            return "交易失败";
        }
        if(tradedStatus==TradeRecord.TRADE_STATUS_WAIT_CONFIRM){
            return "交易结果待确认";
        }
        return "";
    }
}
