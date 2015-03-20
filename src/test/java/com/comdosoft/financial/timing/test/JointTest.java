package com.comdosoft.financial.timing.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.comdosoft.financial.timing.joint.JointManager;
import com.comdosoft.financial.timing.joint.hanxin.AccountRegistRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mvc.xml")
@ActiveProfiles("local")
public class JointTest {
	
	@Autowired
	@Qualifier("hanxin")
	private JointManager hxActionManager;
	
	@Autowired
	@Qualifier("zhonghui")
	private JointManager zhActionManager;
	
	@Test
	public void login(){
		
		String terminalId = "30709B402D08";
		
		AccountRegistRequest arr = new AccountRegistRequest();
		arr.setTerminalId(terminalId);
		arr.setMerchantName("gookin");
		arr.setLegalManName("吴国清");
		arr.setLegalManIdcard("320219198512093530");
		arr.setSettleAccountType("2");
		arr.setSettleAccount("吴国清");
		arr.setSettleAccountNo("6222021102020761436");
		arr.setSettleAgency("中国工商银行苏州园区支行");
		arr.setAccountPwd("111111");
		arr.setMobileNum("13862223435");
		hxActionManager.acts(arr, (result) ->{
			System.out.println(result);
		});
		
//		String terminalId = "30709B68800A";
//		String account = "13909585846";
//		String pwd = "585846";
		
//		String terminalId = "30709B5F310A";
//		String account = "15000849705";
//		String pwd = "123456";
//		
//		LoginRequest request = new LoginRequest();
//		request.setAccountName(account);
//		request.setAccountPwd(pwd);
//		request.setTerminalId(terminalId);
//		hxActionManager.acts(request, (result) ->{
//			System.out.println(result);
//			LoginRequest.LoginResponse response = (LoginRequest.LoginResponse)result;
//			FindPwdRequest fpr = new FindPwdRequest();
//			fpr.setTerminalId(terminalId);
//			fpr.setMerchantId(response.getMerchantId());
//			fpr.setPhoneNum(response.getAccountName());
//			fpr.setPwd("223456");
//			fpr.setSettleAccountName(response.getSettleAccount());
//			fpr.setSetttleAccountNo(response.getSettleAccountNo());
//			hxActionManager.acts(fpr, (r)-> {
//				System.out.println(r);
//			});
//		});
//		
//		EnquiryListRequest elr = new EnquiryListRequest();
//		elr.setTerminalId(terminalId);
//		hxActionManager.acts(elr, (result) -> {
//			System.out.println(result);
//		});
//		
//		EnquiryRequest er = new EnquiryRequest();
//		er.setTerminalId(terminalId);
//		er.setMerchantOrderId("000001000218");
//		er.setTransType("100000");
//		er.setPlatformId("27366568516");
//		hxActionManager.acts(er, (result)->{
//			System.out.println(result);
//		});
		
//		AccountEnquiryRequest aer = new AccountEnquiryRequest();
//		aer.setTerminalId(terminalId);
//		aer.setMerchantId("173665683999061");
//		hxActionManager.acts(aer, (result)-> {
//			System.out.println(result);
//		});
		
//		SettBankListRequest sblr = new SettBankListRequest();
//		sblr.setTerminalId(terminalId);
//		hxActionManager.acts(sblr, (result)-> {
//			System.out.println(result);
//		});
		
//		FindPwdRequest fpr = new FindPwdRequest();
//		fpr.setTerminalId("30709B402D08");
//		hxActionManager.acts(fpr, (result)-> {
//			System.out.println(result);
//		});
	}
	
//	public static void main(String[] args) throws IOException {
//		Action action = new ActivateAction("FB36016D03BF5BCD", "501010034132", "zf.zfmini.1.3.1111", "SHZF");
		//{"respTime":"20150211142625","isSuccess":true,"respCode":"SUCCESS","respMsg":"成功","serialType":"0.5","signatureFlag":true}
//		Action action = new RegistAction("501010034132", "gookin",
//				"13862223435", "gookin", "460,0,6157,55153", Const.APP_VERSION, Const.PRODUCT, new File("f:/test.png"));
		//{"respTime":"20150211144336","isSuccess":true,"respCode":"SUCCESS","respMsg":"祝贺您成功注册, 请登录后进行资质审核."}
//		Action action = new LoginAction("13862223435","gookin","460,0,6157,55153",Const.APP_VERSION,Const.PRODUCT);
		//{"respTime":"20150211151520","isSuccess":true,"respCode":"SUCCESS","respMsg":"认证有未提交情况，请注意提交","status":"0001","name":"gookin","cardTail":"NULL","ksnNo":"501010034132","bluetoothName":"","serialType":"0.5","model":"zfmini","nextReqNo":2,"argument":{},"sessionKey":"DEB5731FB8F18182F59CCF6D1650222C","keyCheck":"5BEA4CF6","isBluetooth":false}
//		Action action = new RealnameAuthAction("13862223435","gookin","460,0,6157,55153",
//				"gookin", "32021919851202222x", new File("f:/test1.png"), new File("f:/test1.png"));
		//{"respTime":"20150211160010","isSuccess":true,"respCode":"SUCCESS","respMsg":"成功","reqNo":"10"}
//		Action action = new MerchantAuthAction("13862223435","gookin","460,0,6157,55153",
//				"最后一次测试中文", "suzhou", "licence",
//				new File("f:/test1.png"), new File("f:/test1.png"), new File("f:/test1.png"));
//		Action action = new AccountAuthAction("13862223435","gookin","460,0,6157,55153","test",
//				"6222021102020111111", "gookin","test",new File("f:/test1.png"));
//		Action action = new SignAuthAction("13862223435","gookin","460,0,6157,55153",new File("f:/test1.png"));
//		Action action = new FindBankAction("中国工商银行苏州");
//		Action action = new LastTrans("13862223435","gookin","460,0,6157,55153");
//		Action action = new LastTenTrans("13862223435","gookin","460,0,6157,55153");
//		Action.acts(action.setHandler((result)->{
//			System.out.println(result);
//		}));
//	}

}
