package com.comdosoft.financial.timing.joint;

public interface JointRequest {
	
	Class<? extends JointResponse> getResponseType();

}
