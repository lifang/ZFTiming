package com.comdosoft.financial.timing.scheduling.profit;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.scheduling.CalculateProfit;

/**
 * 分润计算类型
 * 对应DictionaryTradeType里的tradeType类型
 * @author wu
 *
 */
public interface CalculateType {
	
	boolean support(Byte type);
	
	void calculate(CalculateProfit cp,TradeRecord record);

}
