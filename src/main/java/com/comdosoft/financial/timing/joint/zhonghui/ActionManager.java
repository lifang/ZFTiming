package com.comdosoft.financial.timing.joint.zhonghui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryOpenPrivateInfo;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalOpeningInfo;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalTradeTypeInfo;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;
import com.comdosoft.financial.timing.joint.zhonghui.transactions.LastTenTrans;
import com.comdosoft.financial.timing.joint.zhonghui.transactions.Transaction;
import com.comdosoft.financial.timing.joint.zhonghui.transactions.TransactionAction;
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
	public Page<TradeRecord> pullTrades(Terminal terminal, Integer tradeTypeId, PageRequest request) {
		PageRequest r = new PageRequest(1, 10);
		LastTenTrans ltt = new LastTenTrans(terminal.getAccount(), terminal.getPassword(), null, appVersion);
		TransactionAction.TransResult tr = (TransactionAction.TransResult)acts(ltt);
		if(!tr.isSuccess()){
			LOG.info("交易流水查询失败,code:{},msg:{}", tr.getRespCode(),tr.getRespMsg());
			return null;
		}
		List<Transaction> trans =  tr.getTransactions();
		List<TradeRecord> records = new ArrayList<>();
		for(Transaction tran : trans){
			TradeRecord record = new TradeRecord();
			record.setSysOrderId(Integer.toString(tran.getRespNo()));
			record.setAmount(tran.getAmount());
			record.setPaidCode(Integer.parseInt(tran.getRespCode()));
			record.setPoundage(tran.getFeeAmount());
			records.add(record);
		}
		return new Page<TradeRecord>(r, records, records.size());
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#submitOpeningApply(com.comdosoft.financial.timing.domain.zhangfu.Terminal, com.comdosoft.financial.timing.service.TerminalService)
	 */
	@Override
	public void submitOpeningApply(Terminal terminal,
			TerminalService terminalService) {
		
		LOG.info("start submit opening apply...");
		OpeningApplie oa = terminalService.findOpeningAppylByTerminalId(terminal.getId());
		LOG.info("opening apply id:{},status:{},activate status:{}",oa.getId(),oa.getStatus(),oa.getActivateStatus());
		if(oa.getStatus() != OpeningApplie.STATUS_WAITING_CHECKE
				&& oa.getStatus()!=OpeningApplie.STATUS_CHECK_FAIL){
			return;
		}
		
		//刷卡器激活
		if(oa.getActivateStatus() == OpeningApplie.ACTIVATE_STATUS_NO_ACTIVED) {
			LOG.info("apply [{}] start activate...",oa.getId());
			ActivateAction aa = new ActivateAction(terminal.getReserver2(), terminal.getSerialNum(), appVersion, product);
			ActivateAction.ActivateResult ar = (ActivateAction.ActivateResult)acts(aa);
			LOG.info("apply [{}] activate result... code:{},msg:{}",oa.getId(),ar.getRespCode(),ar.getRespMsg());
			if(ar.isSuccess()) {
				oa.setActivateStatus(OpeningApplie.ACTIVATE_STATUS_NO_REGISTED);
				terminalService.updateOpeningApply(oa);
			}else {
				terminalService.recordSubmitFail(oa,"刷卡器激活",ar.getRespCode(),ar.getRespMsg());
				return;
			}
		}
		
		//图片
		Map<String,InputStream> picMap = new HashMap<>();
		List<TerminalOpeningInfo> terminalOpeningInfos = oa.getTerminalOpeningInfos();
		Map<Integer,DictionaryOpenPrivateInfo> infos = terminalService.allOpenPrivateInfos();
		for(TerminalOpeningInfo info : terminalOpeningInfos){
			if(info.getTypes() == DictionaryOpenPrivateInfo.TYPE_IMAGE){
				String type = infos.get(info.getTargetId()).getQueryMark();
				picMap.put(type, terminalService.path2InputStream(info.getValue()));
			}
		}
		
		//用户注册
		if(oa.getActivateStatus() == OpeningApplie.ACTIVATE_STATUS_NO_REGISTED) {
			LOG.info("apply [{}] start regist...",oa.getId());
			RegistAction ra = new RegistAction(terminal.getSerialNum(), oa.getName(), oa.getPhone(),
					"123456", null, appVersion, product, picMap.get("signature"));
			Result r = (Result)acts(ra);
			LOG.info("apply [{}] regist result... code:{},msg:{}",oa.getId(),r.getRespCode(),r.getRespMsg());
			if(r.isSuccess()){
				terminal.setAccount(oa.getPhone());
				terminal.setPassword("123456");
				terminalService.updateTerminal(terminal);
				oa.setActivateStatus(OpeningApplie.ACTIVATE_STATUS_REGISTED);
				terminalService.updateOpeningApply(oa);
			}else {
				terminalService.recordSubmitFail(oa,"用户注册",r.getRespCode(),r.getRespMsg());
				return;
			}
		}
		
		//实名认证
		LOG.info("apply [{}] start real name auth...",oa.getId());
		RealnameAuthAction raa = new RealnameAuthAction(terminal.getAccount(), terminal.getPassword(),
				null, appVersion,
				oa.getMerchant().getLegalPersonName(),
				oa.getMerchant().getLegalPersonCardId(),
				terminalService.path2InputStream(oa.getMerchant().getCardIdFrontPhotoPath()),
				terminalService.path2InputStream(oa.getMerchant().getCardIdBackPhotoPath()));
		Result raar = (Result)acts(raa);
		LOG.info("apply [{}] real name auth result... code:{},msg:{}",
				oa.getId(),raar.getRespCode(),raar.getRespMsg());
		if(!raar.isSuccess()){
			terminalService.recordSubmitFail(oa,"实名认证",raar.getRespCode(),raar.getRespMsg());
			return;
		}
		
		//商户认证
		LOG.info("apply [{}] start merchant auth...",oa.getId());
		MerchantAuthAction maa = new MerchantAuthAction(terminal.getAccount(), terminal.getPassword(),
				null, appVersion,
				oa.getMerchantName(),
				oa.getCity().getName(),
				oa.getMerchant().getBusinessLicenseNo(),
				picMap.get("business"),
				picMap.get("businessPlace"),
				picMap.get("cashierDesk"));
		Result maar = (Result)acts(maa);
		LOG.info("apply [{}] merchant auth result... code:{},msg:{}",
				oa.getId(),maar.getRespCode(),maar.getRespMsg());
		if(!maar.isSuccess()){
			terminalService.recordSubmitFail(oa,"商户认证",maar.getRespCode(),maar.getRespMsg());
			return;
		}
		
		//账户认证
		LOG.info("apply [{}] start account auth...",oa.getId());
		AccountAuthAction aaa = new AccountAuthAction(terminal.getAccount(), terminal.getPassword(),
				null, appVersion,
				oa.getAccountBankName(),
				oa.getAccountBankNum(),
				oa.getMerchant().getLegalPersonName(),
				oa.getAccountBankCode(),
				picMap.get("card"));
		Result aaar = (Result)acts(aaa);
		LOG.info("apply [{}] account auth result... code:{},msg:{}",
				oa.getId(),aaar.getRespCode(),aaar.getRespMsg());
		if(!aaar.isSuccess()){
			terminalService.recordSubmitFail(oa,"账户认证",aaar.getRespCode(),aaar.getRespMsg());
			return;
		}
		
		//协议签名
		LOG.info("apply [{}] start sign auth...",oa.getId());
		SignAuthAction saa = new SignAuthAction(terminal.getAccount(), terminal.getPassword(),
				null, appVersion, picMap.get("signature"));
		Result saar = (Result)acts(saa);
		LOG.info("apply [{}] sign auth result... code:{},msg:{}",
				oa.getId(),saar.getRespCode(),saar.getRespMsg());
		if(!saar.isSuccess()){
			terminalService.recordSubmitFail(oa,"协议签名",aaar.getRespCode(),aaar.getRespMsg());
			return;
		}
		
		oa.setSubmitStatus(OpeningApplie.SUBMIT_STATUS_SUCCESS);
		terminalService.updateOpeningApply(oa);
	}
	
}
