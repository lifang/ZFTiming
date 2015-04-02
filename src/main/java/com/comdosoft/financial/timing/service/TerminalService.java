package com.comdosoft.financial.timing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalTradeTypeInfo;
import com.comdosoft.financial.timing.mapper.zhangfu.OpeningApplieMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.TerminalTradeTypeInfoMapper;

@Service
public class TerminalService {

	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private OpeningApplieMapper openingApplieMapper;
	@Autowired
	private TerminalTradeTypeInfoMapper terminalTradeTypeInfoMapper;
	
	public OpeningApplie findOpeningAppylByTerminalId(Integer terminalId){
		return openingApplieMapper.selectOpeningApplie(terminalId);
	}
	
	public Terminal findById(Integer id) {
		return terminalMapper.selectByPrimaryKey(id);
	}
	
	public void updateOpeningApply(OpeningApplie oa) {
		openingApplieMapper.updateByPrimaryKey(oa);
	}
	
	public Terminal findBySerial(String serial) {
		return terminalMapper.selectBySerial(serial);
	}
	
	public void updateTerminal(Terminal terminal) {
		terminalMapper.updateByPrimaryKey(terminal);
	}
	
	//更新TerminalTradeTypeInfo状态
	public void updateTerminalTradeTypeStatus(Integer status, Integer terminalId, Integer tradeTypeId){
		terminalTradeTypeInfoMapper.updateStatus(status, terminalId, tradeTypeId);
	}
	
	public void updateTerminalTradeTypeStatus(Integer status, Integer terminalId){
		terminalTradeTypeInfoMapper.updateStatus(status, terminalId, null);
	}
	
	public List<TerminalTradeTypeInfo> findTerminalTradeTypeInfos(Integer terminalId){
		return terminalTradeTypeInfoMapper.selectTerminalTradeTypeInfos(terminalId);
	}
}
