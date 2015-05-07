package com.comdosoft.financial.timing.scheduling.profit;

import java.math.BigInteger;

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
		if(supportTradeType.getTerminalRate()==null || supportTradeType.getBaseRate()==null){
			cp.setCalculateFail(record);
			return;
		}
		BigInteger b1 = new BigInteger(String.valueOf(record.getAmount()))
			.multiply(new BigInteger(String.valueOf(supportTradeType.getTerminalRate())));
		Integer poundage = (b1.divide(new BigInteger("10000"))).intValue();
		if(poundage<supportTradeType.getFloorCharge()){
			poundage = supportTradeType.getFloorCharge();
		}else if(poundage>supportTradeType.getTopCharge()){
			poundage = supportTradeType.getTopCharge();
		}
		if(!poundage.equals(record.getPoundage())) {
			cp.setRecordTerminalProfitFail(record);
			cp.setCalculateFail(record);
		}

		BigInteger b2 = new BigInteger(String.valueOf(supportTradeType.getBaseRate()));
		Integer res = (b2.multiply(new BigInteger(String.valueOf(record.getAmount()))).divide(new BigInteger("10000"))).intValue();
		Integer profitPrice = poundage - res.intValue();
		if(profitPrice.compareTo(supportTradeType.getFloorProfit())<0){
			profitPrice = supportTradeType.getFloorProfit();
		}
		if(profitPrice.compareTo(supportTradeType.getTopProfit())>0){
			profitPrice = supportTradeType.getTopProfit();
		}
		cp.setCalculateSuccess(record, profitPrice);
		cp.setTopAgentProfit(record, profitPrice);
	}

}
