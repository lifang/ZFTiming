package com.comdosoft.financial.timing.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.timing.domain.zhangfu.CustomerIntegralRecord;
 

public interface StatisticsIntegralTaskMapper {
	/**
	 * 查询订单表状态为已发货或已评价。并且未获得积分的用户id
	 * 
	 * @return
	 */
	List<Map<String, Object>> findOrderInfo();

	/**
	 * 查询常量表，sys_config 对应integralBuyPos 的计分规则。
	 * 
	 * @param paramName
	 * @return
	 */
	int findPosValue(String paramName);

	/**
	 * 计算customer的积分。SUM(o.actual_price)/100/posVal
	 * 
	 * @param customerid
	 * @param posVal
	 * @return
	 */
	int calcIntegral(int id, int posVal);

	/**
	 * 更新order表中 用户的积分统计状态为已统计积分 状态：integral_status=1
	 * 
	 * @param customerId
	 */
	void updateOrdersIntegralStatus(int id);

	/**
	 * 新增用户的积分记录。
	 * 
	 * @param record
	 */
	void insertCustomerIntegralRecords(CustomerIntegralRecord record);

	/**
	 * 更新customer的总积分。 Integral=Integral+xx;
	 * 
	 * @param customerId
	 */
	void updateCustomerIntegral(int customerId, int integral);

	
	Integer findCustomerIntegral(int customerId);
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
