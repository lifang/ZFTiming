package com.comdosoft.financial.timing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.joint.JointManager;

/**
 * 
 * @author GK.W
 *
 */
@Service
public class ThirdPartyService {

	@Autowired
	@Qualifier("hanxin")
	private JointManager hxManager;
	@Autowired
	@Qualifier("zhonghui")
	private JointManager zhManager;

	
}
