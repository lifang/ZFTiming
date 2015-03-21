package com.comdosoft.financial.timing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.mapper.zhangfu.TerminalMapper;

@Service
public class TerminalService {

	@Autowired
	private TerminalMapper terminalMapper;
	
	public Terminal findBySerial(String serial) {
		return terminalMapper.selectBySerial(serial);
	}
	
	public void saveTerminal(Terminal termainl) {
		terminalMapper.updateByPrimaryKey(termainl);
	}
}
