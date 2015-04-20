package com.comdosoft.financial.timing.mapper.trades;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;

public interface TransactionStatusMapper {
	
	public void insert(TradeRecord tradeRecord);
}
