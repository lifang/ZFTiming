/**
 * 
 */
package com.comdosoft.financial.timing.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.timing.domain.Response;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.service.ThirdPartyService;
import com.comdosoft.financial.timing.utils.page.Page;

/**
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年3月27日 下午4:42:52
 */
@RestController
@RequestMapping("/api/service")
public class RestServiceController {
	
	@Autowired
	private ThirdPartyService thirdPartyService;
	@Autowired
	private TerminalService terminalService;
	
	/**
	 * 提交申请资料
	 * @param terminalId
	 * @param payChannelId
	 * @return
	 */
	@RequestMapping(value="/apply/open",method=RequestMethod.POST)
	public Response applyOpen(Integer terminalId,Integer payChannelId){
		if(terminalId==null||payChannelId==null){
			return Response.getError("参数[terminalId,payChannelId]都不可为空！");
		}
		thirdPartyService.submitOpeningApply(terminalId, payChannelId);
		return Response.getSuccess("请求处理中...");
	}

	/**
	 * 结算银行列表
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @param payChannelId
	 * @return
	 */
	@RequestMapping(value="/banks/query",method=RequestMethod.POST)
	public Response queryBanks(String keyword,Integer page,
			Integer pageSize,Integer payChannelId,String serialNum){
		if(payChannelId==null){
			return Response.getError("参数[payChannelId]不可为空！");
		}
		if(page==null){
			page = 1;
		}
		if(pageSize==null){
			pageSize = 10;
		}
		Page<JointManager.Bank> banks = thirdPartyService.bankList(keyword,
				pageSize, pageSize, payChannelId, serialNum);
		return Response.getSuccess(banks);
	}

	/**
	 * 查询交易流水
	 * @param terminalId
	 * @param payChannelId
	 * @param tradeTypeId
	 * @return
	 */
	@RequestMapping(value="/orders/query",method=RequestMethod.POST)
	public Response queryOrders(Integer terminalId,Integer payChannelId,
			Integer tradeTypeId,Integer page,Integer pageSize){
		if(payChannelId==null||terminalId==null||tradeTypeId==null){
			return Response.getError("参数[tradeTypeId,terminalId,payChannelId]都不可为空！");
		}
		Page<TradeRecord> records = thirdPartyService.pullTrades(
				terminalId, payChannelId, tradeTypeId,page, pageSize);
		if(records == null) {
			return Response.getError("第三方交易流水查询失败");
		}
		return Response.getSuccess(records);
	}

	/**
	 * 同步状态
	 * @param account
	 * @param password
	 * @param serialNum
	 * @param payChannelId
	 * @return
	 */
	@RequestMapping(value="/status/sync",method=RequestMethod.POST)
	public Response syncStatus(String account,String password,String serialNum,Integer payChannelId){
		if(account==null||password==null||serialNum==null||payChannelId==null){
			return Response.getError("参数[account,password,serialNum,payChannelId]都不可为空！");
		}
		Terminal terminal = terminalService.findBySerial(serialNum);
		if(terminal == null) {
			return Response.getError("未查询到终端.");
		}
		String result = thirdPartyService.syncStatus(payChannelId,account,password,terminal);
		if(StringUtils.hasLength(result)){
			return Response.getSuccess(result);
		}
		return Response.getError("同步失败");
	}
}
