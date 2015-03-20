package com.comdosoft.financial.manage.joint;

public interface JointResponse {
	
	static final int RESULT_SUCCESS = 1;
	static final int RESULT_FAIL = 9;
	
	int getResult();
	
	void setResult(int result);
	
	boolean isSuccess();
	
	String getMsg();

}
