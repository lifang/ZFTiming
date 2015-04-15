package com.comdosoft.financial.timing.joint.hanxin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.protocol.HttpClientContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryOpenPrivateInfo;
import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalOpeningInfo;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalTradeTypeInfo;
import com.comdosoft.financial.timing.joint.JointException;
import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.JointRequest;
import com.comdosoft.financial.timing.joint.JointResponse;
import com.comdosoft.financial.timing.joint.hanxin.EnquiryListRequest.OrderInfo;
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
	public String syncStatus(Terminal terminal,TerminalService terminalService){
		LoginRequest request = new LoginRequest();
		request.setAccountName(terminal.getAccount());
		request.setAccountPwd(terminal.getPassword());
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

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#pullTrades(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<TradeRecord> pullTrades(Terminal terminal, Integer tradeTypeId, PageRequest request) {
		EnquiryListRequest elreq = new EnquiryListRequest();
		elreq.setTerminalId(terminal.getSerialNum());
		DateTime time = DateTime.now().minusMonths(3);
		elreq.setBeginTime(time.toString(DateTimeFormat.forPattern("yyyyMMddHHmmss")));
		elreq.setPlatformId(terminal.getReserver1());
		elreq.setCurPage(request.getPage());
		elreq.setPageCount(request.getPageSize());
		EnquiryListRequest.EnquiryListResponse elresp = (EnquiryListRequest.EnquiryListResponse)acts(elreq);
		if(!elresp.isSuccess()){
			return null;
		}
		List<OrderInfo> infos = elresp.getOrderInfos();
		List<TradeRecord> records = new ArrayList<>();
		//把order info转化成trade record
		for(OrderInfo info : infos) {
			TradeRecord tr = new TradeRecord();
			tr.setAmount(info.getTransAmt());
			tr.setTradeNumber(info.getTransId());
			tr.setSysOrderId(info.getMerchantOrderId());
			tr.setTypes(Byte.valueOf(info.getTransType()));
			records.add(tr);
		}
		return new Page<TradeRecord>(request, records, elresp.getTotalCount());
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#submitOpeningApply(com.comdosoft.financial.timing.domain.zhangfu.Terminal, com.comdosoft.financial.timing.service.TerminalService)
	 */
	@Override
	public void submitOpeningApply(Terminal terminal,
			TerminalService terminalService) {
		LOG.info("start submit opening apply...");
		OpeningApplie oa = terminalService.findOpeningAppylByTerminalId(terminal.getId());
		LOG.info("opening apply [{}] status:{},activate status:{}",oa.getId(),oa.getStatus(),oa.getActivateStatus());
		if(oa.getStatus() != OpeningApplie.STATUS_WAITING_CHECKE
				&& oa.getStatus()!=OpeningApplie.STATUS_CHECK_FAIL){
			return;
		}
		if(oa.getActivateStatus() != OpeningApplie.ACTIVATE_STATUS_REGISTED) {
			//账户申请
			AccountRegistRequest arreq = new AccountRegistRequest();
			arreq.setTerminalId(terminal.getSerialNum());
			arreq.setMerchantName(oa.getMerchantName());
			arreq.setLegalManName(oa.getMerchant().getLegalPersonName());
			arreq.setLegalManIdcard(oa.getMerchant().getLegalPersonCardId());
			arreq.setMobileNum(oa.getPhone());
			arreq.setPersonalMerRegNo(oa.getMerchant().getBusinessLicenseNo());
			arreq.setTaxNo(oa.getMerchant().getTaxRegisteredNo());
			arreq.setOccNo(oa.getMerchant().getOrganizationCodeNo());
			arreq.setSettleAccountType(oa.getTypes().toString());
			arreq.setSettleAccount(oa.getAccountBankName());
			arreq.setSettleAccountNo(oa.getAccountBankNum());
			arreq.setSettleAgency(oa.getAccountBankCode());
			arreq.setAccountPwd("123456");
			LOG.info("apply [{}] start regist...",oa.getId());
			ResponseBean arrsp = (ResponseBean)acts(arreq);
			LOG.info("apply [{}] regist response code:{},desc:{}",oa.getId(),arrsp.getRespCode(),arrsp.getRespDesc());
			if(arrsp.isSuccess()){
				terminal.setMerchantNum(((AccountRegistRequest.AccountRegistResponse) arrsp).getMerchantId());
				terminalService.updateTerminal(terminal);
				oa.setActivateStatus(OpeningApplie.ACTIVATE_STATUS_REGISTED);
				terminalService.updateOpeningApply(oa);
			}else{
				terminalService.recordSubmitFail(oa,"账户申请",arrsp.getRespCode(),arrsp.getRespDesc());
				return;
			}
		}
		//图片上传
		List<TerminalOpeningInfo> terminalOpeningInfos = oa.getTerminalOpeningInfos();
		Map<Integer,DictionaryOpenPrivateInfo> infos = terminalService.allOpenPrivateInfos();
		for(TerminalOpeningInfo info : terminalOpeningInfos){
			if(info.getTypes() == DictionaryOpenPrivateInfo.TYPE_IMAGE){
				LOG.info("apply [{}] start image upload... type:{},value:{}",oa.getId(),
						infos.get(info.getTargetId()).getQueryMark(),info.getValue());
				PicUploadRequest pureq = new PicUploadRequest();
				pureq.setMerchantId(terminal.getMerchantNum());
				pureq.setPic(terminalService.path2File((info.getValue())));
				pureq.setPicType(infos.get(info.getTargetId()).getQueryMark());
				PicUploadRequest.PicUploadResponse puresp = (PicUploadRequest.PicUploadResponse)acts(pureq);
				LOG.info("apply [{}] image upload response code:{},desc:{}",oa.getId(),puresp.getRespCode(),puresp.getRespDesc());
				if(!puresp.isSuccess()){
					terminalService.recordSubmitFail(oa,"图片上传",puresp.getRespCode(),puresp.getRespDesc());
					return;
				}
			}
		}
		oa.setSubmitStatus(OpeningApplie.SUBMIT_STATUS_SUCCESS);
		terminalService.updateOpeningApply(oa);
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#modifyPwd(com.comdosoft.financial.timing.domain.zhangfu.Terminal, com.comdosoft.financial.timing.service.TerminalService, java.lang.String)
	 */
	@Override
	public void modifyPwd(Terminal terminal, TerminalService terminalService,
			String newPwd) throws JointException {
		OpeningApplie oa = terminalService.findOpeningAppylByTerminalId(terminal.getId());
		FindPwdRequest fpr = new FindPwdRequest();
		fpr.setMerchantId(terminal.getMerchantNum());
		fpr.setTerminalId(terminal.getSerialNum());
		fpr.setPhoneNum(terminal.getAccount());
		fpr.setPwd(newPwd);
		fpr.setSettleAccountName(oa.getAccountBankName());
		fpr.setSetttleAccountNo(oa.getAccountBankNum());
		fpr.setPhoneNum(oa.getPhone());
		FindPwdRequest.FindPwdResponse fpresp = (FindPwdRequest.FindPwdResponse)acts(fpr);
		if(!fpresp.isSuccess()){
			throw new JointException("第三方调用失败,code:["+fpresp.getRespCode()+"]"
					+ ",desc:["+fpresp.getRespDesc()+"]");
		}
		terminal.setPassword(newPwd);
		terminalService.updateTerminal(terminal);
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#resetPwd(com.comdosoft.financial.timing.domain.zhangfu.Terminal, com.comdosoft.financial.timing.service.TerminalService)
	 */
	@Override
	public void resetPwd(Terminal terminal, TerminalService terminalService) throws JointException {
		throw new JointException("翰鑫不支持此接口.");
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#resetDevice(com.comdosoft.financial.timing.domain.zhangfu.Terminal, com.comdosoft.financial.timing.service.TerminalService)
	 */
	@Override
	public void resetDevice(Terminal terminal, TerminalService terminalService)
			throws JointException {
		throw new JointException("翰鑫不支持此接口.");
	}

	/* (non-Javadoc)
	 * @see com.comdosoft.financial.timing.joint.JointManager#replaceDevice(com.comdosoft.financial.timing.domain.zhangfu.Terminal, com.comdosoft.financial.timing.service.TerminalService)
	 */
	@Override
	public void replaceDevice(Terminal terminal, String newSerialNum, TerminalService terminalService)
			throws JointException {
		throw new JointException("翰鑫不支持此接口.");
	}
}
