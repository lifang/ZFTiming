/**
 * 
 */
package com.comdosoft.financial.timing.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.timing.domain.Response;
import com.comdosoft.financial.timing.service.ThirdPartyService;

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

		return Response.getSuccess(null);
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
	public Response queryBanks(String keyword,Integer page,Integer pageSize,Integer payChannelId){
		if(payChannelId==null){
			return Response.getError("参数[payChannelId]不可为空！");
		}
		if(page==null){
			page = 1;
		}
		if(pageSize==null){
			pageSize = 10;
		}

		return Response.getSuccess(null);
	}

	/**
	 * 查询交易流水
	 * @param terminalId
	 * @param payChannelId
	 * @param tradeTypeId
	 * @return
	 */
	@RequestMapping(value="/orders/query",method=RequestMethod.POST)
	public Response queryOrders(Integer terminalId,Integer payChannelId,Integer tradeTypeId){
		if(payChannelId==null||terminalId==null||tradeTypeId==null){
			return Response.getError("参数[tradeTypeId,terminalId,payChannelId]都不可为空！");
		}

		return Response.getSuccess(null);
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
		String result = thirdPartyService.syncStatus(payChannelId,account,password,serialNum);
		return Response.getSuccess(result);
	}
}
