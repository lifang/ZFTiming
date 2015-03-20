package com.comdosoft.financial.manage.joint.zhonghui;

import com.comdosoft.financial.manage.joint.JointResponse;

public class Result implements JointResponse {
	
	private String respTime;
	private String respCode;
	private String respMsg;
	private int respNo;
	private int reqNo;
	private boolean isSuccess;
	
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public int getRespNo() {
		return respNo;
	}
	public void setRespNo(int respNo) {
		this.respNo = respNo;
	}
	public int getReqNo() {
		return reqNo;
	}
	public void setReqNo(int reqNo) {
		this.reqNo = reqNo;
	}
	@Override
	public int getResult() {
		return isSuccess?RESULT_SUCCESS:RESULT_FAIL;
	}
	@Override
	public boolean isSuccess() {
		return isSuccess;
	}
	@Override
	public String getMsg() {
		return respMsg;
	}
	@Override
	public void setResult(int result) {
		isSuccess = result==RESULT_SUCCESS;
	}

}
