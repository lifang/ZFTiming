package com.comdosoft.financial.timing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.Agent;
import com.comdosoft.financial.timing.domain.zhangfu.AgentProfitSetting;
import com.comdosoft.financial.timing.mapper.zhangfu.AgentMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.AgentProfitSettingMapper;

@Service
public class AgentService {
	
	@Autowired
	private AgentMapper agentMapper;
	@Autowired
	private AgentProfitSettingMapper agentProfitSettingMapper;
	
	/**
	 * 查询顶级代理商
	 * @param agentId
	 * @return
	 */
	public Agent selectTopLevelAgent(Integer agentId){
		Agent agent = agentMapper.selectByPrimaryKey(agentId);
		if(agent.getCode().length()==3){
			return agent;
		}
		String code = agent.getCode().substring(0, 3);
		return agentMapper.selectByCode(code);
	}

	/**
	 * 根据交易金额，查询最适合的交易费率
	 * @param agentId
	 * @param payChannelId
	 * @param tradeTypeId
	 * @param amount
	 * @return
	 */
	public AgentProfitSetting selectBestProfitSet(Integer agentId,
			Integer payChannelId,Integer tradeTypeId,Integer amount){
		return agentProfitSettingMapper.selectBestProfitSetting(
				agentId, payChannelId, tradeTypeId, amount);
	}
}
