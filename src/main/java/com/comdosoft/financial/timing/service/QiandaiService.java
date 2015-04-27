package com.comdosoft.financial.timing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.trades.TransactionStatusRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalTradeTypeInfo;
import com.comdosoft.financial.timing.mapper.trades.TransactionStatusMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.CommonItemsMapper;

@Service
public class QiandaiService {
	
	@Autowired
	private TransactionStatusMapper transactionStatusMapper;
	
	@Autowired
	private CommonItemsMapper commonItemsMapper;
	
	public CommonItemsMapper getCommonItemsMapper() {
		return commonItemsMapper;
	}

	public void setCommonItemsMapper(CommonItemsMapper commonItemsMapper) {
		this.commonItemsMapper = commonItemsMapper;
	}

	/*根据设备编号查询agent_id，pay_channel_id，customer_id和city_id*/
	public TransactionStatusRecord getCommonItems(String eqno){
		return commonItemsMapper.getCommonItems(eqno);
	}
	
	/*消费成功通知*/
	public void save(TradeRecord tradeRecord){
		transactionStatusMapper.insert(tradeRecord);
	}
	
	/*根据商户注册成功通知更新*/
	public void update(Terminal terminal){
			commonItemsMapper.update(terminal);
	}
	
	/*根据设备状态变化主动通知更新*/
	public void updateStatus(String status,String eqno){
		if("1".equals(status)){
			commonItemsMapper.updateStatus1(Terminal.STATUS_OPENED,eqno);	
		}else if("2".equals(status)){
			commonItemsMapper.updateStatus2(Terminal.STATUS_CANCELED,eqno);
		}
	}

	/*根据 POS终端号查询开通状态更新*/
	public void updateTerminal(String code,String eqno) {
		if("00".equals(code)){
			commonItemsMapper.updateTerminal(Terminal.STATUS_OPENED,eqno);
			commonItemsMapper.updateTerminalTradeTypeInfo(TerminalTradeTypeInfo.STATUS_OPENED,eqno);
		}else if("102".equals(code)){
			commonItemsMapper.updateTerminal(Terminal.STATUS_NO_OPEN,eqno);
			commonItemsMapper.updateTerminalTradeTypeInfo(TerminalTradeTypeInfo.STATUS_NO_OPEN,eqno);
		}
		
	}
	
	public Terminal findBySerial(String serial){
		return commonItemsMapper.findBySerial(serial);
	}
	
	
}
