package com.comdosoft.financial.timing.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.timing.service.TradeService;

@RestController
@RequestMapping("/api/import/orders")
public class TradeRecrodsImportController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TradeRecrodsImportController.class);
	
	@Autowired
	private TradeService tradeService;
	
	public String importOrders(MultipartFile file,Integer flag){
		if(flag==0){
			return "缺少重要参数";
		}
		if(flag==1){
			//导入交易记录
			try {
				tradeService.importTradeRecords(file);
				return "-1";
			} catch (Exception e) {
				LOG.error("import trade record error...",e);
			}
		}
		return "-1";
	}

}
