package com.comdosoft.financial.timing.joint.zhonghui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalTradeTypeInfo;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.utils.page.Page;
import com.comdosoft.financial.timing.utils.page.PageRequest;

public class ActionManager implements JointManager{
	
	private static final Logger LOG = LoggerFactory.getLogger(ActionManager.class);
	
	private String baseUrl;
	private String product;
	private String appVersion;

	@Override
	public JointResponse acts(JointRequest request) {
		if(!(request instanceof Action)){
			throw new IllegalArgumentException();
		}
		Action action = (Action)request;
		try {
			return action.process(this);
		} catch (IOException e) {
			LOG.error("",e);
		}
		return null;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProduct() {
		return product;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#syncStatus(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String syncStatus(String account, String passwd,
			Terminal terminal,TerminalService terminalService) {
		LoginAction login = new LoginAction(account, passwd, null, appVersion, product);
		LoginAction.LoginResult result = (LoginAction.LoginResult)acts(login);
		if(result == null) {
			return null;
		}
		String status = result.getStatus();
		OpeningApplie oa = terminalService.findOpeningAppylByTerminalId(terminal.getId());
		oa.setApplyStatus(status);
		
		if("2222".equals(status)){//开通成功
			oa.setStatus(OpeningApplie.STATUS_CHECK_SUCCESS);
			terminalService.updateTerminalTradeTypeStatus(
					TerminalTradeTypeInfo.STATUS_OPENED, terminal.getId(), DictionaryTradeType.ID_TRADE);
			List<TerminalTradeTypeInfo> infos = terminalService.findTerminalTradeTypeInfos(terminal.getId());
			byte terminalStatus = Terminal.STATUS_OPENED;
			for(TerminalTradeTypeInfo info : infos) {
				if(info.getStatus()==TerminalTradeTypeInfo.STATUS_NO_OPEN){
					terminalStatus = Terminal.STATUS_PART_OPENED;
					break;
				}
			}
			terminal.setStatus(terminalStatus);
		}else if(status.indexOf('3')!=-1){//失败，状态中包含3
			oa.setStatus(OpeningApplie.STATUS_CHECK_FAIL);
			terminal.setStatus(Terminal.STATUS_NO_OPEN);
		}
		
		String serialType = result.getSerialType();
		if(serialType!=null){
			String[] types = serialType.split("+");
			terminal.setBaseRate(Integer.parseInt(types[0]));
			terminal.setTopCharge(Integer.parseInt(types[1]));
		}
		terminalService.updateTerminal(terminal);
		terminalService.updateOpeningApply(oa);
		return status;
	}

	@Override
	public Page<JointManager.Bank> bankList(String keyword, PageRequest request, String serialNum) {
		if(!StringUtils.hasLength(keyword)){
			keyword = "银行";
		}
		//从第三方查询银行
		FindBankAction fba = new FindBankAction(keyword, request.getPageSize(), request.getPage());
		FindBankAction.BankResult result = (FindBankAction.BankResult)acts(fba);
		//组装成接口数据
		List<FindBankAction.Bank> banks = result.getBanks();
		List<JointManager.Bank> bankResult = new ArrayList<JointManager.Bank>();
		for(FindBankAction.Bank b : banks){
			JointManager.Bank rb = new JointManager.Bank();
			rb.setName(b.getBankDeposit());
			rb.setNo(b.getUnionBankNo());;
			bankResult.add(rb);
		}
		return new Page<JointManager.Bank>(request, bankResult, result.getTotal());
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#pullTrades(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void pullTrades(Integer terminalId, Integer tradeTypeId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#submitOpeningApply(com.comdosoft.financial.timing.domain.zhangfu.Terminal, com.comdosoft.financial.timing.service.TerminalService)
	 */
	@Override
	public void submitOpeningApply(Terminal terminal,
			TerminalService terminalService) {
		// TODO Auto-generated method stub
		
	}

}
