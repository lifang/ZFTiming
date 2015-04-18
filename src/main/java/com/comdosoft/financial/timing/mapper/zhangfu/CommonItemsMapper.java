package com.comdosoft.financial.timing.mapper.zhangfu;

import com.comdosoft.financial.timing.domain.trades.TransactionStatusRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;

public interface CommonItemsMapper {

	public TransactionStatusRecord getCommonItems(String eqno);
	
	public void update(Terminal terminal);
	
	public void updateStatus1(int status,String eqno);
	
	public void updateStatus2(int status,String eqno);

	public void updateTerminal(int status,String eqno);

	public void updateTerminalTradeTypeInfo(int status,String eqno);

	public Terminal findBySerial(String serial);
}