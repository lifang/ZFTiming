package com.comdosoft.financial.timing.joint;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.utils.page.Page;
import com.comdosoft.financial.timing.utils.page.PageRequest;


public interface JointManager {
	
	JointResponse acts(JointRequest request);
	
	/**
	 * 同步状态
	 * @return
	 */
	String syncStatus(String account,String passwd,Terminal terminal,TerminalService terminalService);
	
	/**
	 * 结算银行列表
	 * @param keyword  关键字
	 * @param pageSize
	 * @param page
	 * @param payChannelId
	 */
	Page<Bank> bankList(String keyword,PageRequest request, String serialNum);
	
	/**
	 * 从第三方拉去交易流水
	 * @param terminalId	终端号
	 * @param tradeTypeId	交易类型
	 */
	Page<TradeRecord> pullTrades(Terminal terminal,Integer tradeTypeId,PageRequest request);
	
	/**
	 * 提交申请资料
	 * @param terminal
	 * @param terminalService
	 */
	void submitOpeningApply(Terminal terminal,TerminalService terminalService);
	
	class Bank{
		private String no;
		private String name;
		public String getNo() {
			return no;
		}
		public void setNo(String no) {
			this.no = no;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
