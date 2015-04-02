package com.comdosoft.financial.timing.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.mapper.zhangfu.OpeningApplieMapper;
import com.comdosoft.financial.timing.utils.page.Page;
import com.comdosoft.financial.timing.utils.page.PageRequest;

/**
 * 
 * @author GK.W
 *
 */
@Service
public class ThirdPartyService {
	
	@Resource
	private Map<String,JointManager> managers;
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private OpeningApplieMapper openingApplieMapper;

	public String syncStatus(Integer payChannelId,String account,String passwd,Terminal terminal){
		JointManager manager = managers.get(payChannelId);
		String status = manager.syncStatus(account, passwd, terminal, terminalService);
		return status;
	}
	
	public List<OpeningApplie> openingAppliesPage(byte status){
		PageRequest request = new PageRequest(1, 10);
		return openingApplieMapper.selectWithStatus(status, request);
	}
	
	public Page<JointManager.Bank> bankList(String keyword,Integer pageSize,Integer page,
			Integer payChannelId, String serialNum){
		JointManager manager = managers.get(payChannelId);
		PageRequest r = new PageRequest(page, pageSize);
		return manager.bankList(keyword, r, serialNum);
	}
}
