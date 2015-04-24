package com.comdosoft.financial.timing.scheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comdosoft.financial.timing.domain.trades.Profit;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Agent;
import com.comdosoft.financial.timing.domain.zhangfu.AgentProfitSetting;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.SupportTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.SysConfig;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.scheduling.profit.CalculateType;
import com.comdosoft.financial.timing.scheduling.profit.OtherType;
import com.comdosoft.financial.timing.scheduling.profit.TradeType;
import com.comdosoft.financial.timing.service.AgentService;
import com.comdosoft.financial.timing.service.SystemService;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.service.TradeService;

/**
 * 计算分润
 * @author wu
 *
 */
@Component
public class CalculateProfit {
	
	private static final Logger LOG = LoggerFactory.getLogger(CalculateProfit.class);
	
	private static final List<CalculateType> calculateTypes = new ArrayList<>();
	@Autowired
	private TradeService tradeService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TerminalService terminalService;
	
	static {
		calculateTypes.add(new OtherType());
		calculateTypes.add(new TradeType());
	}
	
	@Scheduled(cron="0 0 0 * * ?")
	public void job(){
		List<TradeRecord> records = tradeService.searchNonCalculatedRecord();
		LOG.info("select {} records.",records.size());
		records.forEach((record)->{
			try {
				calculate(record);
			} catch (Exception e) {
				LOG.error("id:"+record.getId()+" calculate exception.",e);
				record.setAttachStatus(TradeRecord.ATTACH_STATUS_FAIL);
				tradeService.updateRecord(record);
			}
		});
	}
	
	public void calculate(TradeRecord record){
		DictionaryTradeType tradeType = tradeService.allTradeTypes().get(record.getTradeTypeId());
		for(CalculateType type : calculateTypes){
			if(type.support(tradeType.getTradeType())){
				if(tradeType.getTradeType()==DictionaryTradeType.TYPE_TRADE){
					TradeType obj = (TradeType)type;
					obj.calculate(this,record,tradeService);
				}
				type.calculate(this,record);
				break;
			}
		}
	}
	
	/**
	 * 设置计算分润失败
	 * @param record
	 */
	public void setCalculateFail(TradeRecord record){
		record.setAttachStatus(TradeRecord.ATTACH_STATUS_FAIL);
		tradeService.updateRecord(record);
	}
	
	/**
	 * 设置计算分润成功
	 * @param record
	 * @param profitPrice
	 */
	public void setCalculateSuccess(TradeRecord record,Integer profitPrice) {
		record.setAttachStatus(TradeRecord.ATTACH_STATUS_CALCULATED);
		record.setProfitPrice(profitPrice);
		tradeService.updateRecord(record);;
	}
	
	public SupportTradeType supportTradeType(Integer channelId,Integer tradeTypeId){
		return tradeService.selectSupportTradeType(channelId, tradeTypeId);
	}
	
	public Terminal selectRecordTerminal(TradeRecord record) {
		return terminalService.findBySerial(record.getTerminalNumber());
	}
	
	/**
	 * 设置终端分润费率出错
	 * @param record
	 */
	public void setRecordTerminalProfitFail(TradeRecord record) {
		Terminal t = selectRecordTerminal(record);
		t.setIsRateWrong(false);
		terminalService.updateTerminal(t);
	}

	/**
	 * 设置顶级代理商的分润记录profit
	 * @param record
	 * @param profitGet
	 */
	public void setTopAgentProfit(TradeRecord record,Integer profitGet){
		Agent topAgent = agentService.selectTopLevelAgent(record.getAgentId());
		List<Integer> agentList = agentService.getAllAgentsByTopAgent(topAgent.getCode());
		Integer amounts = tradeService.getAmounts(agentList);
		if(amounts == null){
			amounts = 0;
		}
		AgentProfitSetting agentProfitSetting = agentService.selectBestProfitSet(topAgent.getId(),
				record.getPayChannelId(), record.getTradeTypeId(), amounts);
		Integer percent = null;
		if(agentProfitSetting != null){
			percent = agentProfitSetting.getPercent();
		}
		if(percent == null) {
			percent = topAgent.getDefaultProfit();
		}
		if(percent == null) {
			SysConfig sysConfig = systemService.findTradeRecordDefaultProfit();
			if(sysConfig!=null) {
				percent = Integer.parseInt(sysConfig.getParamValue());
			}
		}
		if(percent == null) {
			percent = 0;
		}
		Integer profitPay = profitGet*percent/1000;
		Terminal terminal = selectRecordTerminal(record);
		Profit profit = new Profit();
		profit.setMerchantId(terminal.getMerchantId());
		profit.setAccountNumber(record.getAccountNumber());
		profit.setAmount(record.getAmount());
		profit.setCreatedAt(new Date());
		profit.setCurrentAgentId(record.getAgentId());
		profit.setPayChannelId(record.getPayChannelId());
		profit.setProfitsGet(profitGet);
		profit.setProfitsPay(profitPay);
		profit.setStatus(Profit.STATUS_SUCCESS);
		profit.setTerminalNumber(record.getTerminalNumber());
		profit.setTopAgentId(topAgent.getId());
		profit.setTradedAt(record.getTradedAt());
		profit.setTradeRecordId(record.getId());
		profit.setTypes(record.getTypes());
		profit.setUpdatedAt(new Date());
		tradeService.saveProfit(profit);//插入profit
	}
}
