package com.comdosoft.financial.timing.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.joint.JointException;
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
	
	public void replaceDevice(Integer terminalId,String newSerialNum) throws ServiceException,JointException{
		Terminal terminal = checkTerminal(terminalId);
		JointManager manager = switchManager(terminal.getPayChannelId());
		manager.replaceDevice(terminal, newSerialNum, terminalService);
	}
	
	public void resetDevice(Integer payChannelId,Terminal terminal) throws JointException{
		JointManager manager = switchManager(payChannelId);
		manager.resetDevice(terminal, terminalService);
	}
	
	public void resetPwd(Integer payChannelId,Terminal terminal) throws JointException{
		JointManager manager = switchManager(payChannelId);
		manager.resetPwd(terminal, terminalService);
	}
	
	public void modifyPwd(Integer payChannelId,String newPwd,
			Terminal terminal) throws JointException{
		JointManager manager = switchManager(payChannelId);
		manager.modifyPwd(terminal, terminalService, newPwd);
	}

	public String syncStatus(Integer terminalId) throws ServiceException{
		Terminal terminal = checkTerminal(terminalId);
		JointManager manager = switchManager(terminal.getPayChannelId());
		String status = manager.syncStatus(terminal, terminalService);
		return status;
	}
	
	public List<OpeningApplie> openingAppliesPage(byte status){
		PageRequest request = new PageRequest(1, 10);
		return openingApplieMapper.selectWithStatus(status, request);
	}
	
	public Page<JointManager.Bank> bankList(String keyword,Integer pageSize,Integer page,
			Integer payChannelId, String serialNum){
		JointManager manager = switchManager(payChannelId);
		PageRequest r = new PageRequest(page, pageSize);
		return manager.bankList(keyword, r, serialNum);
	}
	
	public Page<TradeRecord> pullTrades(Integer terminalId,Integer payChannelId,
			Integer tradeTypeId,Integer page,Integer pageSize){
		if(page == null) {
			page = 1;
		}
		if(pageSize == null) {
			pageSize = 10;
		}
		Terminal terminal = terminalService.findById(terminalId);
		JointManager manager = switchManager(payChannelId);
		PageRequest request = new PageRequest(page, pageSize);
		return manager.pullTrades(terminal, tradeTypeId,request);
	}
	
	@Async
	public void submitOpeningApply(Integer terminalId,Integer payChannelId){
		JointManager manager = switchManager(payChannelId);
		Terminal terminal = terminalService.findById(terminalId);
		manager.submitOpeningApply(terminal, terminalService);
	}
	
	private JointManager switchManager(Integer payChannelId){
		return managers.get(payChannelId.toString());
	}
	
	private Terminal checkTerminal(Integer terminalId) throws ServiceException {
		Terminal terminal = terminalService.findById(terminalId);
		if(terminal == null) {
			throw new ServiceException("未查询到终端["+terminalId+"].");
		}
		if(terminal.getPayChannelId() == null) {
			throw new ServiceException("终端["+terminalId+"]不存在payChannelId");
		}
		return terminal;
	}
}
