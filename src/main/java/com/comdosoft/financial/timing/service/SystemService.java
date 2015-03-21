package com.comdosoft.financial.timing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.SysConfig;
import com.comdosoft.financial.timing.mapper.zhangfu.SysConfigMapper;

@Service
public class SystemService {

	@Autowired
	private SysConfigMapper sysConfigMapper;
	
	public SysConfig findTradeRecordDefaultProfit() {
		return sysConfigMapper.findByKey(SysConfig.TRADE_RECORD_DEFAULT_PROFIT);
	}
}
