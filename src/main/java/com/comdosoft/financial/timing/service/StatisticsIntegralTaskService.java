package com.comdosoft.financial.timing.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.CustomerIntegralRecord;
import com.comdosoft.financial.timing.mapper.trades.TradeRecordStatisticsMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.StatisticsIntegralTaskMapper;
import com.comdosoft.financial.timing.utils.Constants;

/**
 * 购买Pos机统计，业务层
 * 
 * @author DELL
 *
 */
@Service
public class StatisticsIntegralTaskService {
	private static final Logger logger = LoggerFactory
			.getLogger(StatisticsIntegralTaskService.class);

	@Autowired
	private StatisticsIntegralTaskMapper statisticsIntegralTaskMapper;

	@Autowired
	private TradeRecordStatisticsMapper tradeRecordStatisticsMapper;

	/**
	 * 
	 * @throws Exception
	 */
	public synchronized void statisticsIntegral() throws Exception {
		// 未统计积分的订单。
		List<Map<String, Object>> orders = statisticsIntegralTaskMapper
				.findOrderInfo();
		if (orders.size() > 0) {
			logger.info("需要统计积分的订单总条数>>>>>>> " + orders.size());
			CustomerIntegralRecord cir = null;
			// 积分计算规则
			int posValue = statisticsIntegralTaskMapper
					.findPosValue(Constants.INTEGRAL_BUY_POS);
			for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
				try {
					Map<String, Object> map = (Map<String, Object>) iterator
							.next();
					cir = new CustomerIntegralRecord();
					// 计算这条订单所获取的积分。
					int sumIntegral = ((int) map.get("actual_price") / 100)
							/ (posValue / 100);
					// 新增record
					cir.setCustomerId((int) map.get("customer_id"));
					cir.setCreatedAt(new Date());
					cir.setQuantity(sumIntegral);
					cir.setTargetId((int) map.get("id"));
					cir.setTypes((byte) 1);
					cir.setTargetType((byte) 2);
					cir.setDescription(map.get("order_number").toString());
					statisticsIntegralTaskMapper
							.insertCustomerIntegralRecords(cir);
					logger.info("新增用户积分记录, 完成!");
					// 更新customer表的总积分(integral +)
					Integer integral = statisticsIntegralTaskMapper
							.findCustomerIntegral((int) map.get("customer_id"));
					integral = integral == null ? 0 : integral;
					integral += sumIntegral;
					statisticsIntegralTaskMapper.updateCustomerIntegral(
							(int) map.get("customer_id"), integral);
					logger.info("更新用户的总积分：" + integral + " \t完成!");
					// 更新当前id的order积分统计状态为 已统计
					statisticsIntegralTaskMapper
							.updateOrdersIntegralStatus((int) map.get("id"));
					logger.info("更新orders  订单号："
							+ map.get("order_number").toString()
							+ " ,  的状态为已统计。");
					logger.info("订单id：" + map.get("customer_id") + ",订单价格："
							+ map.get("actual_price") + ",订单获取积分："
							+ sumIntegral);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info("全部订单统计完成!");
		} else {
			logger.info("没有找到需要统计积分的订单");
		}
	}

	/**
	 * 交易流水 积分统计
	 */
	public synchronized void transactionFlowingService() throws Exception {
		List<Map<String, Object>> records = tradeRecordStatisticsMapper
				.findTradeRecords();
		if (records.size() > 0) {
			logger.info("需要统计积分的交易流水总条数>>>>>>>>>" + records.size());
			CustomerIntegralRecord cir = null;
			// 积分计算规则
			int posValue = statisticsIntegralTaskMapper
					.findPosValue(Constants.INTEGRAL_TRADE);
			for (Iterator iterator = records.iterator(); iterator.hasNext();) {
				try {
					Map<String, Object> map = (Map<String, Object>) iterator
							.next();
					cir = new CustomerIntegralRecord();
					// 计算这条订单所获取的积分。
					int sumIntegral = ((int) map.get("amount") / 100)
							/ (posValue / 100);
					// 新增record
					cir.setCustomerId((int) map.get("customer_id"));
					cir.setCreatedAt(new Date());
					cir.setQuantity(sumIntegral);
					cir.setTargetId((int) map.get("id"));
					cir.setTypes((byte) 1);
					cir.setTargetType((byte) 1);
					cir.setDescription(map.get("trade_number").toString());
					statisticsIntegralTaskMapper
							.insertCustomerIntegralRecords(cir);
					logger.info("新增用户获取积分记录, 完成!");
					// 更新customer表的总积分(integral +)
					Integer integral = statisticsIntegralTaskMapper
							.findCustomerIntegral((int) map.get("customer_id"));
					logger.info("查找用户原始积分：" + integral );
					integral = integral == null ? 0 : integral;
					integral += sumIntegral;
					statisticsIntegralTaskMapper.updateCustomerIntegral(
							(int) map.get("customer_id"), integral);
					logger.info("更新用户的总积分：" + integral + " \t结束!");
					// 更新当前id的tradeRecords积分统计状态为 已统计
					tradeRecordStatisticsMapper.updateTradeRecords((int) map
							.get("id"));
					logger.info("更新流水号为：" + map.get("trade_number")
							+ " \t的积分统计状态为，已统计!");
					logger.info("订单id：" + map.get("id") + "流水号："
							+ map.get("trade_number") + ",订单价格："
							+ map.get("amount") + ",订单获取积分：" + sumIntegral);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info("全部交易流水记录的积分统计已完成!");
		}else{
			logger.info("没有找到需要统计积分的交易流水记录");
		}
	}
}
