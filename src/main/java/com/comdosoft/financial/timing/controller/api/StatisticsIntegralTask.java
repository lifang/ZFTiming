package com.comdosoft.financial.timing.controller.api;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.timing.service.StatisticsIntegralTaskService;
 

public class StatisticsIntegralTask {

	private static final Logger logger = LoggerFactory
			.getLogger(StatisticsIntegralTask.class);

	@Resource
	private StatisticsIntegralTaskService statisticsIntegralTaskService;

	/**
	 * 统计购买的pos机 所获得的积分。
	 */
	public void statisticsIntegral() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 购买pos机的统计
					logger.info("准备统计订单积分");
					statisticsIntegralTaskService.statisticsIntegral();

					logger.info("订单积分统计结束");
				} catch (Exception e) {
					logger.error("统计订单积分异常!" + e.getMessage());
				}

			}
		}).start();
	}

	/**
	 * 统计交易流水
	 */
	public void transactionFlowing() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("准备统计交易流水");
					statisticsIntegralTaskService.transactionFlowingService();
					logger.info("统计交易流水结束");
				} catch (Exception e) {
					logger.error("统计交易流水异常!" + e.getMessage());
				}
			}
		}).start();
	}

}
