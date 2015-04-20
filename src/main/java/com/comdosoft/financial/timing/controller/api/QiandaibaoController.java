package com.comdosoft.financial.timing.controller.api;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.comdosoft.financial.timing.domain.trades.OrderResult;
import com.comdosoft.financial.timing.domain.trades.OrderResult.OrderList;
import com.comdosoft.financial.timing.domain.trades.PosQuery;
import com.comdosoft.financial.timing.domain.trades.Result;
import com.comdosoft.financial.timing.domain.trades.ResultInfo;
import com.comdosoft.financial.timing.domain.trades.ResultList;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.trades.TransactionRecordQuery;
import com.comdosoft.financial.timing.domain.trades.TransactionStatusRecord;
import com.comdosoft.financial.timing.service.QiandaiService;
import com.comdosoft.financial.timing.utils.HttpUtils;
import com.comdosoft.financial.timing.utils.StringUtils;

@RestController
@RequestMapping("/api/qiandaibao")
public class QiandaibaoController {
	
	private static final Logger Log = LoggerFactory.getLogger(QiandaibaoController.class);
	
	/*钱袋分配给接口合作方 md5key 的值*/
	private static final String MD5key = "AB14EF83C9204C268CA764AAF49D4D787C025837%$#@$&^%$@5610216-428D8A82-090E25849C03";
	
	@Autowired
	private QiandaiService qiandaiService;
	
	/**
	 * 消费成功通知
	 * @param transactionStatusRecord
	 * @return
	 */
	@RequestMapping(value = "/transactionStatus" , method = RequestMethod.POST)
	public String transactionStatus(@ModelAttribute("transactionStatusRecord")TransactionStatusRecord transactionStatusRecord){
		String sign = transactionStatusRecord.getSign();
		StringBuffer sb = new StringBuffer();
		sb.append("orderid=" + transactionStatusRecord.getOrderid());
		sb.append("agentno=" + transactionStatusRecord.getAgentno());
		sb.append("money=" + transactionStatusRecord.getMoney());
		sb.append("eqno=" + transactionStatusRecord.getEqno());
		sb.append("cardno=" + transactionStatusRecord.getCardno());
		sb.append("cardtype=" + transactionStatusRecord.getCardtype());
		sb.append(MD5key);
		String md5_str = StringUtils.encryption(sb.toString(), "MD5");
		Log.info("接受到的参数..." + sb.toString() + ",MD5:" + md5_str);
		
		if(!sign.equalsIgnoreCase(md5_str)){
			return "md5 match error";
		}
		
		TransactionStatusRecord temp = qiandaiService.getCommonItems(transactionStatusRecord.getEqno());
		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setTradeNumber(transactionStatusRecord.getOrderid());
		tradeRecord.setMerchantNumber(transactionStatusRecord.getAgentno());
		try {
			tradeRecord.setTradedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(transactionStatusRecord.getTime()));
		} catch (ParseException e) {
			Log.error("date convert error..." + e);
		}
		
		String money = transactionStatusRecord.getMoney();
		String fee = transactionStatusRecord.getFee();
		String agentId = temp.getAgent_id();
		String payChannelId = temp.getPay_channel_id();
		String customerId = temp.getCustomer_id();
		String cityId = temp.getCity_id();
		if(money != null){
			tradeRecord.setAmount(StringUtils.unitScale(money).intValue());
		}
		if(fee != null){
			tradeRecord.setPoundage(StringUtils.unitScale(fee).intValue());
		}
		tradeRecord.setTerminalNumber(transactionStatusRecord.getEqno());
		tradeRecord.setPayFromAccount(transactionStatusRecord.getCardno());
		if(agentId != null){
			tradeRecord.setAgentId(Integer.valueOf(agentId));
		}
		if(payChannelId != null){
			tradeRecord.setPayChannelId(Integer.valueOf(payChannelId));
		}
		if(customerId != null){
			tradeRecord.setCustomerId(Integer.valueOf(customerId));
		}
		if(cityId != null){
			tradeRecord.setCityId(Integer.valueOf(cityId));
		}
		
		qiandaiService.save(tradeRecord);
		return "ok";
	}
	
	/**
	 * 商户注册成功通知
	 * @param username
	 * @param agentno
	 * @param eqno
	 * @param time
	 * @param name
	 * @param remark
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/register" , method = RequestMethod.POST)
	public String register(String username,String agentno,String usercode,String time,String name,String remark,String sign){
	
		StringBuffer sb = new StringBuffer();
		sb.append("username=" + username);
		sb.append("agentno=" + agentno);
		sb.append("usercode=" + usercode);
		sb.append("time=" + time);
		sb.append(MD5key);
		String md5_str = StringUtils.encryption(sb.toString(), "MD5");
		Log.info("接受到的参数..." + "username=" + username + ",agentno=" + agentno + ",usercode=" + usercode + ",time=" + time +
				",name=" + name + ",remark=" + ",sign=" + sign + ",MD5:" + md5_str);
		if(!sign.equalsIgnoreCase(md5_str)){
			return "md5 match error";
		}
		
//		Terminal terminal = new Terminal();
//		try {
//			Date openedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
//			terminal.setAccount(username);
//			terminal.setMerchantNum(agentno);
//			terminal.setOpenedAt(openedAt);
//			terminal.setReserver1(remark);
//			terminal.setReserver2(usercode);
//			qiandaiService.update(terminal);//eqno没有怎么更新
//		} catch (ParseException e) {
//			Log.error("date convert error...",e);
//		} 
		
		return "ok";
	}
	
	/**
	 * 设备状态变化主动通知
	 * @param agentno
	 * @param time
	 * @param eqno
	 * @param status
	 * @param remark
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/equipmentStatus" , method = RequestMethod.POST)
	public String equipmentStatus(String agentno,String time,String eqno,String status,String remark,String sign){
		
		StringBuffer sb = new StringBuffer();
		sb.append("agentno=" + agentno);
		sb.append("time=" + time);
		sb.append("eqno=" + eqno);
		sb.append("status=" + status);
		sb.append(MD5key);
		String md5_str = StringUtils.encryption(sb.toString(), "MD5");
		Log.info("接受到的参数..." + sb.toString() + ",MD5:" + md5_str);
		
		if(!sign.equalsIgnoreCase(md5_str)){
			return "md5 match error";
		}
		qiandaiService.updateStatus(status, eqno);
		return "ok";
	}
	
	/**
	 * 商户费率发生变化通知
	 * @param agentno
	 * @param time
	 * @param credit_card_fee
	 * @param debit_card_fee
	 * @param credit_card_top_flag
	 * @param debit_card_top_flag
	 * @param credit_card_top_money
	 * @param debit_card_top_money
	 * @param remark
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/feeChange" , method = RequestMethod.POST)
	public String feeChange(String agentno,String time,String credit_card_fee,String debit_card_fee,String credit_card_top_flag ,
			String debit_card_top_flag,String credit_card_top_money,String debit_card_top_money,String remark,String sign){
		
		StringBuffer sb = new StringBuffer();
		sb.append("agentno=" + agentno);
		sb.append("time=" + time);
		sb.append("credit_card_fee=" + credit_card_fee);
		sb.append("debit_card_fee=" + debit_card_fee);
		sb.append("credit_card_top_flag=" + credit_card_top_flag);
		sb.append("debit_card_top_flag=" + debit_card_top_flag);
		sb.append("credit_card_top_money=" + credit_card_top_money);
		sb.append("debit_card_top_money=" + debit_card_top_money);
		sb.append(MD5key);
		String md5_str = StringUtils.encryption(sb.toString(), "MD5");
		Log.info("接受到的参数..." + "agentno=" + agentno + ",time=" + time + ",credit_card_fee=" + credit_card_fee + ",debit_card_fee=" + debit_card_fee
				+ "credit_card_top_flag=" + credit_card_top_flag + ",debit_card_top_flag=" + debit_card_top_flag + ",credit_card_top_money=" +
				credit_card_top_money + ",debit_card_top_money=" + debit_card_top_money + ",remark="+remark + ",sign="+sign+ ",MD5:" + md5_str);
		
		if(!sign.equalsIgnoreCase(md5_str)){
			return "md5 match error";
		}
		return "ok";
	}
	
	/**
	 * 根据 POS终端号查询开通状态
	 * @return
	 */
	@RequestMapping(value="/posQuery",method=RequestMethod.POST)
	public String posQuery(String eqno,String now,String remark,String sign){
		
		String url = "https://s.qiandai.com/SearchAgentInfo/getAgentInfoByEqno";
		
		Map<String,String> headers = new  HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		
		Map<String,String> params = new  HashMap<String, String>();
		params.put("eqno", eqno);
		params.put("now", now);
		params.put("remark", remark);
		params.put("sign", sign);
		
		Map<String,File> fileParams = new  HashMap<String, File>();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String result = "";
		try {
			result = HttpUtils.post(url, headers, params, fileParams, responseHandler);
		} catch (IOException e) {
			Log.error("error..." + e);
		}
		
		PosQuery pos = new PosQuery();
		pos = (PosQuery)StringUtils.parseJSONStringToObject(result, pos);
		StringBuffer sb = new StringBuffer();
		sb.append("code=" + pos.getCode());
		sb.append("eqno=" + pos.getEqno());
		sb.append(MD5key);
		String md5_str = StringUtils.encryption(sb.toString(), "MD5");
		Log.info("接受到的参数..." + sb.toString());
		
		if(!pos.getSign().equalsIgnoreCase(md5_str)){
			return "md5 match error";
		}
		qiandaiService.updateTerminal(pos.getCode(),pos.getEqno());
		return "ok";
	}
	
	/**
	 * 查询交易记录接口
	 * @return
	 */
	@RequestMapping(value="/transactionRecordQuery",method=RequestMethod.POST)
	public String transactionRecordQuery(String eqno,String querytype,String begintime,String endtime,String sign){		
		String url = "https://s.qiandai.com/SearchOrderList/showorder";
		
		Map<String,String> headers = new  HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		
		Map<String,String> params = new  HashMap<String, String>();
		params.put("eqno", eqno);
		params.put("querytype", querytype);
		params.put("begintime", begintime);
		params.put("endtime", endtime);
		params.put("sign", sign);
		
		Map<String,File> fileParams = new  HashMap<String, File>();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String result = "";
		try {
			result = HttpUtils.post(url, headers, params, fileParams, responseHandler);
		} catch (IOException e) {
			Log.error("error..." + e);
		}
		System.out.println(result);
		TransactionRecordQuery query = new TransactionRecordQuery();
		query = (TransactionRecordQuery)StringUtils.parseJSONStringToObject(result, query);
		String code = query.getCode();
		if("01".equals(code) || "02".equals(code)){
			return "{\"code\":-1,\"message\":\"交易不存在或其他错误\",\"result\":{\"total\":0,\"list\":[]}}";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("code=" + query.getCode());
		sb.append("agentno=" + query.getAgentno());
		sb.append("querytype=" + query.getQuerytype());
		sb.append("begintime=" + query.getBegintime());
		sb.append("endtime=" + query.getEndtime());
		sb.append(MD5key);
		String md5_str = StringUtils.encryption(sb.toString(), "MD5");
		Log.info("接受到的参数..." + sb.toString() + ",MD5:" + md5_str);
		if(!query.getSign().equalsIgnoreCase(md5_str)){
			return "{\"code\":-1,\"message\":\"md5签名不匹配\",\"result\":{\"total\":0,\"list\":[]}}";
		}

		String orderlistStr =  query.getOrderlist();
		OrderResult re = new OrderResult();
		re = (OrderResult)StringUtils.parseJSONStringToObject(orderlistStr,re);
		ResultInfo resultInfo = new ResultInfo();
		String result_code = re.getCcode();
		if("00".equals(result_code)){
			resultInfo.setCode(1);
			resultInfo.setMessage("success");
		}
		Result result1 = new Result();
		OrderList[] orderList = re.getOrderlist();
		int total = orderList.length;
		result1.setTotal(total);
		ResultList[] resultList = new ResultList[total];
		for(int i = 0;i < total;i++){
			ResultList resultListTemp = new ResultList();
			resultListTemp.setAmount(Integer.valueOf(orderList[i].getMoney()));
			resultListTemp.setId(null);
			resultListTemp.setPoundage(Integer.valueOf(orderList[i].getFee()));
			resultListTemp.setPayIntoAccount(null);
			resultListTemp.setTradedStatus("1");
			resultListTemp.setTrade_number(orderList[i].getOrderid());
			resultListTemp.setTradedTimeStr(orderList[i].getTime());
			resultListTemp.setPayFromAccount(orderList[i].getCardno());
			resultListTemp.setTerminalNumber(orderList[i].getEqno());
			resultList[i] = resultListTemp;
		}
		result1.setList(resultList);
		
		resultInfo.setResult(result1);
		return StringUtils.parseObjectToJSONString(resultInfo);
		
	}
	
}
