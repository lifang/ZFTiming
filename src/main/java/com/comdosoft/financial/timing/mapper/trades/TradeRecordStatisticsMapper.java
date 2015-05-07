package com.comdosoft.financial.timing.mapper.trades;

import java.util.List;
import java.util.Map;
 

public interface TradeRecordStatisticsMapper {
	 
 
	/**
	 * 统计交易流水的统计
	 */

	/**
	 * 查询trade_records表，traded_status交易状态为
	 * 交易成功的的，且integral_status（交易流水统计状态）为0（即未统计过积分）的
	 * 
	 * @return
	 */
	List<Map<String, Object>> findTradeRecords();

	/**
	 * 更新对应的trade_records表，将 integral_status 置为 1 （已统计） 
	 * where trade_records.id = id;
	 * @param id
	 */
	void updateTradeRecords(int id);
	
}
