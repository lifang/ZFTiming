package com.comdosoft.financial.timing.joint;


public interface JointManager {
	
	JointResponse acts(JointRequest request);
	
	/**
	 * 同步状态
	 * @return
	 */
	String syncStatus(String account,String passwd,String serialNum);
	
}
