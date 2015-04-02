package com.comdosoft.financial.timing.joint.hanxin;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalTradeTypeInfo;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;
import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.utils.HttpUtils;
import com.comdosoft.financial.timing.utils.page.Page;
import com.comdosoft.financial.timing.utils.page.PageRequest;

public class ActionManager implements JointManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ActionManager.class);
	
	private HttpClientContext context = HttpClientContext.create();
	private String url;
	private String rsaKey;
	
	@Override
	public JointResponse acts(JointRequest request) {
		if(!(request instanceof RequestBean)){
			throw new IllegalArgumentException();
		}
		RequestBean bean = (RequestBean)request;
		LOG.debug("request bean:...{}",bean.toString());
		try {
			String sendData = bean.generateBody(this);
			return HttpUtils.post(url, sendData, context, bean);
		} catch (Exception e) {
			LOG.error("",e);
		}
		return null;
	}
	
	public JointRequest createLogin(String phoneNum, String password, String terminalId) {
		LoginRequest request = new LoginRequest();
		request.setAccountName(phoneNum);
		request.setAccountPwd(password);
		request.setTerminalId(terminalId);
		return request;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setRsaKey(String rsaKey) {
		this.rsaKey = rsaKey;
	}

	public String getRsaKey() {
		return rsaKey;
	}

	@Override
	public String syncStatus(String account, String passwd,
			Terminal terminal, TerminalService terminalService) {
		LoginRequest request = new LoginRequest();
		request.setAccountName(account);
		request.setAccountPwd(passwd);
		request.setTerminalId(terminal.getSerialNum());
		LoginRequest.LoginResponse response = (LoginRequest.LoginResponse)acts(request);
		if(response==null) {
			return null;
		}
		String accountStatus = response.getAccountStatus();
		Integer intStatus = Integer.valueOf(accountStatus);
		if(intStatus == 0){//审核中
			//不做处理
		}
		
		OpeningApplie oa = terminalService.findOpeningAppylByTerminalId(terminal.getId());
		if(intStatus == 1) {//审核成功
			oa.setStatus(OpeningApplie.STATUS_CHECK_SUCCESS);
			terminalService.updateOpeningApply(oa);
			terminal.setStatus(Terminal.STATUS_OPENED);
			terminalService.updateTerminal(terminal);
			terminalService.updateTerminalTradeTypeStatus(
					TerminalTradeTypeInfo.STATUS_OPENED, terminal.getId());
		}
		
		if(intStatus == 2) {//审核失败
			oa.setStatus(OpeningApplie.STATUS_CHECK_FAIL);
			terminalService.updateOpeningApply(oa);
			terminal.setStatus(Terminal.STATUS_NO_OPEN);
			terminalService.updateTerminal(terminal);
		}
		
		return accountStatus;
	}

	@Override
	public Page<JointManager.Bank> bankList(String keyword, PageRequest request, String serialNum) {
		SettBankListRequest r = new SettBankListRequest();
		r.setTerminalId(serialNum);
		SettBankListRequest.SettBankListResponse response = 
				(SettBankListRequest.SettBankListResponse)acts(r);
		List<SettBankListRequest.Bank> rBanks = response.getBankList();
		List<JointManager.Bank> resultAll = new ArrayList<>();
		for(SettBankListRequest.Bank rBank : rBanks) {
			if(StringUtils.isEmpty(keyword)
					||rBank.getBankName().contains(keyword)){
				resultAll.add(convert(rBank));
			}
		}
		int from = request.getOffset();
		int to = request.getOffset()+request.getPageSize();
		if(to > resultAll.size()){
			to = resultAll.size();
		}
		if(from >= to){
			return new Page<JointManager.Bank>(request, new ArrayList<>(), resultAll.size());
		}
		List<JointManager.Bank> result = resultAll.subList(from, to);
		return new Page<JointManager.Bank>(request, result, resultAll.size());
	}

	private JointManager.Bank convert(SettBankListRequest.Bank b) {
		JointManager.Bank bank = new JointManager.Bank();
		bank.setName(b.getBankName());
		bank.setNo(b.getBankNo());
		return bank;
	}
}
