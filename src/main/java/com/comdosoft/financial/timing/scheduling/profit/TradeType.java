package com.comdosoft.financial.timing.scheduling.profit;

import java.math.BigInteger;
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
		if(terminal.getBaseRate()==null){
			return;
		}
		Integer basePoundage = (new BigInteger(String.valueOf(terminal.getBaseRate()))
				.multiply(new BigInteger(String.valueOf(record.getAmount())))
				.divide(new BigInteger("10000"))).intValue();
		Integer servicePoundage = (new BigInteger(String.valueOf(map.get("rate")))
				.multiply(new BigInteger(String.valueOf(record.getAmount()))
				.divide(new BigInteger("10000")))).intValue();
		Integer poundage = basePoundage+servicePoundage;
		if(terminal.getTopCharge()!=null){
			poundage = poundage.compareTo(terminal.getTopCharge())<0?poundage:terminal.getTopCharge();
		}
		Integer dbPoundage = record.getPoundage();
		if(dbPoundage!=null){
			if(!poundage.equals(dbPoundage)){
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
		Integer profitPrice = (new BigInteger(String.valueOf(basePoundage)).multiply(new BigInteger("2"))
				.multiply(new BigInteger(String.valueOf(supportTradeType.getBaseProfit())))
				.divide(new BigInteger("100000"))).intValue() 
				+ (new BigInteger(String.valueOf(servicePoundage))
				.multiply(new BigInteger(String.valueOf(serviceProfit)))
				.divide(new BigInteger("10000"))).intValue();
		cp.setCalculateSuccess(record, profitPrice);
		cp.setTopAgentProfit(record, profitPrice);
		
	}


}
