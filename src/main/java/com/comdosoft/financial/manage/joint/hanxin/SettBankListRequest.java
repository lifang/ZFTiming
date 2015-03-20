package com.comdosoft.financial.manage.joint.hanxin;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.comdosoft.financial.manage.joint.JointResponse;

@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
public class SettBankListRequest extends RequestBean {

	public SettBankListRequest() {
		super("SettBankList");
	}

	@Override
	public Class<? extends JointResponse> getResponseType() {
		return SettBankListResponse.class;
	}

	@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
	public static class SettBankListResponse extends ResponseBean{
		private List<Bank> bankList;

		public List<Bank> getBankList() {
			return bankList;
		}

		public void setBankList(List<Bank> bankList) {
			this.bankList = bankList;
		}
	}
	
	public static class Bank{
		private String bankNo;
		private String bankName;
		
		public String getBankNo() {
			return bankNo;
		}
		public void setBankNo(String bankNo) {
			this.bankNo = bankNo;
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
	}
}
