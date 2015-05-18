package com.comdosoft.financial.timing.scheduling;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comdosoft.financial.timing.service.OrderService;

@Component
public class OrderCleanJob {
	private static final Logger logger  = LoggerFactory.getLogger(OrderCleanJob.class);
    @Resource
    private OrderService orderService;
    
    @SuppressWarnings("deprecation")
	@Scheduled(cron="0 0 0/2 * * ?")
	public void doClean() {
    	logger.error("开始运行订单清理..."+new Date().toLocaleString());
        orderService.cleanOrder();
    }
}