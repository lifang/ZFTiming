package com.comdosoft.financial.timing.joint;

import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.service.TerminalService;


public interface JointManager {
	
	JointResponse acts(JointRequest request);
	
	/**
	 * 同步状态
	 * @return
	 */
	String syncStatus(String account,String passwd,Terminal terminal,TerminalService terminalService);
	
}
