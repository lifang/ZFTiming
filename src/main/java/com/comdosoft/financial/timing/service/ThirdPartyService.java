package com.comdosoft.financial.timing.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.joint.JointManager;

/**
 * 
 * @author GK.W
 *
 */
@Service
public class ThirdPartyService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ThirdPartyService.class);

	@Autowired
	private Map<Integer,JointManager> managers;

	public String syncStatus(Integer payChannelId,String account,String passwd,String serialNum){
		JointManager manager = managers.get(payChannelId);
		String status = manager.syncStatus(account, passwd, serialNum);
		return status;
	}
	
	@Scheduled(fixedDelay=60000)
	public void syncOpeningApplyStatus(){
		
	}
}
