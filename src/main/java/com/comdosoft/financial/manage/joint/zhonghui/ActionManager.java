package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.JointHandler;
import com.comdosoft.financial.manage.joint.JointManager;
import com.comdosoft.financial.manage.joint.JointRequest;
import com.comdosoft.financial.manage.joint.zhonghui.UploadFileAction.Type;
import com.comdosoft.financial.manage.joint.zhonghui.transactions.DateTrans;
import com.comdosoft.financial.manage.joint.zhonghui.transactions.LastTenTrans;
import com.comdosoft.financial.manage.joint.zhonghui.transactions.LastTrans;
import com.comdosoft.financial.manage.joint.zhonghui.transactions.NoTrans;

public class ActionManager implements JointManager{
	
	private static final Logger LOG = LoggerFactory.getLogger(ActionManager.class);
	
	private String baseUrl;
	private String product;
	private String appVersion;
	

	@Override
	public void acts(JointRequest request, JointHandler handler) {
		if(!(request instanceof Action)){
			throw new IllegalArgumentException();
		}
		Action action = (Action)request;
		Result result = null;;
		try {
			result = action.process(this);
		} catch (IOException e) {
			LOG.error("",e);
		}
		if(handler!=null&&result!=null) {
			handler.handle(result);
		}
	}
	
	/**
	 * 登陆
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param appVersion
	 * @param product
	 * @return
	 */
	public JointRequest createLogin(String phoneNum, String password, String position){
		return new LoginAction(phoneNum, password, position,
				appVersion, product);
	}
	
	public JointRequest createLogin(String phoneNum, String password){
		return createLogin(phoneNum, password, null);
	}
	
	/**
	 * 
	 * @param keyword
	 * @param max
	 * @param p
	 * @return
	 */
	public Action createFindBank(String keyword, int max, int p){
		return new FindBankAction(keyword, max, p);
	}
	
	/**
	 * 账户认证
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param bankDeposit
	 * @param accountNo
	 * @param name
	 * @param unionBankNo
	 * @param card
	 * @return
	 */
	public Action createAccountAuth(String phoneNum, String password, String position,
			String bankDeposit, String accountNo, String name,
			String unionBankNo, File card){
		return new AccountAuthAction(phoneNum, password, position,
				bankDeposit, accountNo, name,
				unionBankNo, card);
	}
	
	/**
	 * 刷卡器激活
	 * @param licenseCode
	 * @param ksnNo
	 * @param appVersion
	 * @param product
	 * @return
	 */
	public Action createActivate(String licenseCode, String ksnNo){
		return new ActivateAction(licenseCode, ksnNo, appVersion,
				product);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param ksnNo
	 * @param model
	 * @return
	 */
	public Action createDeviceReplace(String phoneNum, String password, String position,
			String ksnNo, String model){
		return new DeviceReplaceAction(phoneNum, password, position,
				ksnNo, model);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param idNumber
	 * @param ksnNo
	 * @return
	 */
	public Action createDeviceReset(String phoneNum, String password, String position,
			String idNumber, String ksnNo){
		return new DeviceResetAction(phoneNum, password, position,
				idNumber, ksnNo);
	}
	
	/**
	 * 商户认证
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param companyName
	 * @param regPlace
	 * @param businessLicense
	 * @param business
	 * @param businessPlace
	 * @param cashierDesk
	 * @return
	 */
	public Action createMerchantAuth(String phoneNum, String password, String position,
			String companyName, String regPlace,
			String businessLicense,File business, File businessPlace, File cashierDesk) {
		return new MerchantAuthAction(phoneNum, password, position,
				companyName, regPlace,
				businessLicense,business, businessPlace, cashierDesk);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param newPassword
	 * @return
	 */
	public Action createPwdChange(String phoneNum, String password, String position,
			String newPassword){
		return new PwdChangeAction(phoneNum, password, position,
				newPassword);
	}
	
	/**
	 * 
	 * @param accountNo
	 * @param ksnNo
	 * @param mobile
	 * @param product
	 * @param appVersion
	 * @param requireBasekey
	 * @return
	 */
	public Action createPwdReset(String accountNo, String ksnNo, String mobile, boolean requireBasekey){
		return new PwdResetAction(accountNo, ksnNo, mobile,
				product, appVersion, requireBasekey);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param name
	 * @param idNumber
	 * @param personal
	 * @param personalBack
	 * @return
	 */
	public Action createRealnameAuth(String phoneNum, String password, String position,
			String name, String idNumber, File personal, File personalBack){
		return new RealnameAuthAction(phoneNum, password, position,
				name, idNumber, personal, personalBack);
	}
	
	/**
	 * 
	 * @param ksnNo
	 * @param name
	 * @param mobile
	 * @param password
	 * @param registPosition
	 * @param appVersion
	 * @param product
	 * @param signature
	 * @return
	 */
	public Action createRegist(String ksnNo, String name, String mobile,
			String password, String registPosition, File signature){
		return new RegistAction(ksnNo, name, mobile,
				password, registPosition, appVersion,
				product, signature);
	}
	
	/**
	 * 协议签名
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param signature
	 * @return
	 */
	public Action createSignAuth(String phoneNum, String password,
			String position, File signature){
		return new SignAuthAction(phoneNum, password,
				position, signature);
	}

	/**
	 * 文件上传
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param type
	 * @param photo
	 * @return
	 */
	public Action createUploadFile(String phoneNum, String password, String position,
			Type type, File photo){
		return new UploadFileAction(phoneNum, password, position,
				type, photo);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param date
	 * @return
	 */
	public Action createDateTrans(String phoneNum, String password, String position,
			Date date){
		return new DateTrans(phoneNum, password, position, date);
	}
	
	
	/**
	 * 
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @return
	 */
	public Action createLastTenTrans(String phoneNum, String password, String position){
		return new LastTenTrans(phoneNum, password, position);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @return
	 */
	public Action createLastTrans(String phoneNum, String password, String position){
		return new LastTrans(phoneNum, password, position);
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param password
	 * @param position
	 * @param respNo
	 * @return
	 */
	public Action createNoTrans(String phoneNum, String password, String position,int respNo){
		return new NoTrans(phoneNum, password, position,respNo);
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

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getProduct() {
		return product;
	}

	public String getAppVersion() {
		return appVersion;
	}

}
