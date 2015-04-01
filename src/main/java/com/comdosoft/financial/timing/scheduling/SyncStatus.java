/**
 * 
 */
package com.comdosoft.financial.timing.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comdosoft.financial.timing.service.ThirdPartyService;

/**
 * 同步状态
 * 
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年3月31日 下午4:43:58
 */
@Component
public class SyncStatus {
	private static final Logger LOG = LoggerFactory.getLogger(SyncStatus.class);
	
	@Autowired
	private ThirdPartyService thirdPartyService;
	
	@Scheduled(fixedDelay=6000)
	public void syncOpeningApplyStatus(){
		LOG.debug("start sync.");
	}
}
