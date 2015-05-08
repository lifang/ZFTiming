package com.comdosoft.financial.timing.scheduling;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comdosoft.financial.timing.service.StatisticsIntegralTaskService;
 
@Component
public class StatisticsIntegralTask {

	private static final Logger logger = LoggerFactory
			.getLogger(StatisticsIntegralTask.class);

	@Resource
	private StatisticsIntegralTaskService statisticsIntegralTaskService;

	/**
	 * 统计购买的pos机 所获得的积分。
	 */
	@Scheduled(cron="0 0 0/2 * * ?")
	public void statisticsIntegral() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 购买pos机的统计
					logger.info("准备统计订单积分");
					statisticsIntegralTaskService.statisticsIntegral();
				} catch (Exception e) {
					logger.error("统计订单积分异常!" + e.getMessage());
				}

			}
		}).start();
	}

	/**
	 * 统计交易流水
	 */
	@Scheduled(cron="0 0 0/2 * * ?")
	public void transactionFlowing() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("准备统计交易流水");
					statisticsIntegralTaskService.transactionFlowingService();
				} catch (Exception e) {
					logger.error("统计交易流水异常!" + e.getMessage());
				}
			}
		}).start();
	}

}
