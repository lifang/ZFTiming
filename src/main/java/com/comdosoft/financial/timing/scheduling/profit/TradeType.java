package com.comdosoft.financial.timing.scheduling.profit;

import java.util.Map;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.SupportTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.scheduling.CalculateProfit;
import com.comdosoft.financial.timing.service.TradeService;

public class TradeType implements CalculateType {
	
	@Override
	public boolean support(Byte type) {
		return type==DictionaryTradeType.TYPE_TRADE;
	}

	@Override
	public void calculate(CalculateProfit cp,TradeRecord record) {
		
	}
	
	public void calculate(CalculateProfit cp,TradeRecord record,TradeService tradeService) {
		if(record.getProfitPrice()!=null){
			cp.setTopAgentProfit(record, record.getProfitPrice());
			return;
		}
		Terminal terminal = cp.selectRecordTerminal(record);
		if(terminal == null) {
			cp.setCalculateFail(record);
			return;
		}

		Map<String,Integer> map = tradeService.getRateAndProfit(terminal.getSerialNum());
		Long baseRate = new Long(terminal.getBaseRate());
		Long res = baseRate*record.getAmount()/10000;
		Integer basePoundage = res.intValue();
		Long serviceRate = new Long(map.get("rate"));
		Long res2 = serviceRate*record.getAmount()/10000;
		Integer servicePoundage = res2.intValue();
		Integer poundage = basePoundage+servicePoundage;
		if(terminal.getTopCharge()!=null){
			poundage = poundage<terminal.getTopCharge()?poundage:terminal.getTopCharge();
		}
		Integer dbPoundage = record.getPoundage();
		if(dbPoundage!=null){
			if(poundage!=dbPoundage){
				cp.setRecordTerminalProfitFail(record);
				cp.setCalculateFail(record);
				return;
			}
		}else{
			record.setPoundage(poundage);
		}
		SupportTradeType supportTradeType = cp.supportTradeType(
				record.getPayChannelId(), record.getTradeTypeId());
			//将profitPrice设置为 基础分润+浮动分润
		Integer serviceProfit = Integer.valueOf(map.get("profit"));
		Integer profitPrice = basePoundage*2*supportTradeType.getBaseProfit()/100000
				+servicePoundage*serviceProfit/10000;
		cp.setCalculateSuccess(record, profitPrice);
		cp.setTopAgentProfit(record, profitPrice);
		
	}


}
