package com.comdosoft.financial.timing.scheduling.profit;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryBillingCycle;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.SupportTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.scheduling.CalculateProfit;

public class TradeType implements CalculateType {

	@Override
	public boolean support(Byte type) {
		return type==DictionaryTradeType.TYPE_TRADE;
	}

	@Override
	public void calculate(CalculateProfit cp,TradeRecord record) {
		if(record.getProfitPrice()!=null){
			cp.setTopAgentProfit(record, record.getProfitPrice());
			return;
		}
		Terminal terminal = cp.selectRecordTerminal(record);
		if(terminal == null) {
			cp.setCalculateFail(record);
			return;
		}
		DictionaryBillingCycle billingCycle = terminal.getBillingCycle();
		Integer basePoundage = terminal.getBaseRate()*record.getAmount()/10000;
		Integer servicePoundage = billingCycle.getServiceRate()*record.getAmount()/10000;
		Integer poundage = basePoundage+servicePoundage;
		if(terminal.getTopCharge()!=null){
			poundage = poundage<terminal.getTopCharge()?poundage:terminal.getTopCharge();
		}
		if(poundage!=record.getPoundage()){
			cp.setRecordTerminalProfitFail(record);
			cp.setCalculateFail(record);
		}
		SupportTradeType supportTradeType = cp.supportTradeType(
				record.getPayChannelId(), record.getTradeTypeId());
			//将profitPrice设置为 基础分润+浮动分润
		Integer profitPrice = basePoundage*2*supportTradeType.getBaseProfit()/10000
				+servicePoundage*supportTradeType.getServiceProfit()/10000;
		cp.setCalculateSuccess(record, profitPrice);
		cp.setTopAgentProfit(record, profitPrice);
	}

}
