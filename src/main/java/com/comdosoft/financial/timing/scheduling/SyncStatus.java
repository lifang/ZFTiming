/**
 * 
 */
package com.comdosoft.financial.timing.scheduling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
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
	
	@Scheduled(fixedDelay=8*60*60*1000)
	public void syncOpeningApplyStatus(){
		LOG.debug("start sync.");
		List<OpeningApplie> openApplies = null;
		int row = 0;
		do{
			openApplies = thirdPartyService.openingAppliesPage(OpeningApplie.STATUS_CHECKING,row);
			for(OpeningApplie apply : openApplies) {
				try {
					thirdPartyService.syncStatus(apply.getTerminalId());
				} catch (Exception e) {
					LOG.error("",e);
					thirdPartyService.syncFail(apply);
				}
			}
			row++;
		}while(!openApplies.isEmpty());
	}
}
