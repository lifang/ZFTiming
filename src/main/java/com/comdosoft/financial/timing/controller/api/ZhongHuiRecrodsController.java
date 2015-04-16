package com.comdosoft.financial.timing.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.timing.domain.Response;
import com.comdosoft.financial.timing.domain.ZhangFuRecord;
import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.service.OperateRecordService;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.service.TradeService;

@RestController
@RequestMapping("/api/zhonghui")
public class ZhongHuiRecrodsController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ZhongHuiRecrodsController.class);
	
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private OperateRecordService operateRecordService;
	
	//导入交易类型流水
	@RequestMapping(value="/orders/import",method=RequestMethod.POST)
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
				operateRecordService.saveOperateRecord(OperateRecord.TYPE_OTHER, "导入交易类型流水错误，"+e.getMessage());
				LOG.error("import trade record error...",e);
			}
		}
		return "-1";
	}

	//记录非交易类型流水
	@RequestMapping(value="/orders/submit",method=RequestMethod.POST)
	public void submitOrder(@RequestBody ZhangFuRecord record){
		tradeService.receiveRecord(record);
	}
	
	//更新终端其他交易类型接口
	@RequestMapping(value="/trade/type",method=RequestMethod.POST)
	public Response openTradeType(String serialNum,String idCard,String name){
		Terminal terminal = terminalService.findBySerial(serialNum);
		if(terminal == null){
			return Response.getError("未查询到相应的终端");
		}
		terminalService.updateNotTradeTypeStatus(terminal,idCard,name);
		return Response.getSuccess(null);
	}
	
	@RequestMapping(value="/terminal/status",method=RequestMethod.POST)
	public Response terminalStatus(String serialNum){
		Terminal terminal = terminalService.findBySerial(serialNum);
		if(terminal==null){
			Response.getError("未查询到相应的终端");
		}
		OpeningApplie oa = terminalService.findOpeningAppylByTerminalId(terminal.getId());
		Map<String,Object> openingApply = new HashMap<>();
		openingApply.put("status", oa.getStatus());
		openingApply.put("phone", oa.getPhone());
		Map<String,Object> result = new HashMap<>();
		result.put("terminalStatus", terminal.getStatus());
		result.put("openingApply", openingApply);
		return Response.getSuccess(result);
	}
	

	@RequestMapping(value="/status/not/trade",method=RequestMethod.POST)
	public Response notTradeTypeStatus(String serialNum){
		Terminal terminal = terminalService.findBySerial(serialNum);
		if(terminal==null){
			return Response.getError("未查询到相应的终端");
		}
		Integer status = terminalService.findNotTradeTypeStatus(terminal);
		if(status == null) {
			return Response.getError("未查询到非交易类型");
		}
		return Response.getSuccess(status);
	}
}
