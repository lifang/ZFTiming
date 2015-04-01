package com.comdosoft.financial.timing.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.joint.JointManager;

/**
 * 
 * @author GK.W
 *
 */
@Service
public class ThirdPartyService {
	
	@Resource
	private Map<String,JointManager> managers;

	public String syncStatus(Integer payChannelId,String account,String passwd,String serialNum){
		JointManager manager = managers.get(payChannelId);
		String status = manager.syncStatus(account, passwd, serialNum);
		return status;
	}
}
