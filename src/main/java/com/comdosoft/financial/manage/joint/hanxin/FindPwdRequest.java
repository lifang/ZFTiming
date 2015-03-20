package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
public class FindPwdRequest extends RequestBean {

	public FindPwdRequest() {
		super("FindPwd");
	}
	
	private String merchantId;
	private String phoneNum;
	private String settleAccountName;
	private String setttleAccountNo;
	private String pwd;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSettleAccountName() {
		return settleAccountName;
	}

	public void setSettleAccountName(String settleAccountName) {
		this.settleAccountName = settleAccountName;
	}

	public String getSetttleAccountNo() {
		return setttleAccountNo;
	}

	public void setSetttleAccountNo(String setttleAccountNo) {
		this.setttleAccountNo = setttleAccountNo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public Class<? extends JointResponse> getResponseType() {
		return FindPwdResponse.class;
	}

	@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
	public static class FindPwdResponse extends ResponseBean{
		
	}
}
