package com.comdosoft.financial.timing.scheduling.profit;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.SupportTradeType;
import com.comdosoft.financial.timing.scheduling.CalculateProfit;

public class OtherType implements CalculateType {

	@Override
	public boolean support(Byte type) {
		return type==DictionaryTradeType.TYPE_OTHER;
	}

	@Override
	public void calculate(CalculateProfit cp,TradeRecord record) {
		if(record.getProfitPrice()!=null){
			cp.setTopAgentProfit(record, record.getProfitPrice());
			return;
		}
		SupportTradeType supportTradeType = cp.supportTradeType(
				record.getPayChannelId(), record.getTradeTypeId());
		if(supportTradeType.getTerminalRate()==null || supportTradeType.getBaseProfit()==null){
			cp.setCalculateFail(record);
			return;
		}
		Integer poundage = record.getAmount()*supportTradeType.getTerminalRate()/1000;
		if(poundage<supportTradeType.getFloorCharge()){
			poundage = supportTradeType.getFloorCharge();
		}else if(poundage>supportTradeType.getTopCharge()){
			poundage = supportTradeType.getTopCharge();
		}
		if(poundage != record.getPoundage()) {
			cp.setRecordTerminalProfitFail(record);
			cp.setCalculateFail(record);
		}
		Integer profitPrice = poundage - supportTradeType.getBaseProfit()*record.getAmount()/1000;
		if(profitPrice<supportTradeType.getFloorProfit()){
			profitPrice = supportTradeType.getFloorProfit();
		}
		if(profitPrice>supportTradeType.getTopProfit()){
			profitPrice = supportTradeType.getTopProfit();
		}
		cp.setCalculateSuccess(record, profitPrice);
		cp.setTopAgentProfit(record, profitPrice);
	}

}
