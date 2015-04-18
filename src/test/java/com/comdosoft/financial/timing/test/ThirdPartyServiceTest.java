/**
 * 
 */
package com.comdosoft.financial.timing.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.comdosoft.financial.timing.service.TerminalService;
import com.comdosoft.financial.timing.service.ThirdPartyService;

/**
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年4月13日 下午2:59:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mvc.xml")
@ActiveProfiles("demo")
public class ThirdPartyServiceTest {
	
	@Autowired
	private ThirdPartyService thirdPartyService;
	@Autowired
	private TerminalService terminalService;
	
	@Test
	public void testSubmitOpeningApply() throws Exception{
//		thirdPartyService.submitOpeningApply(247);
//		Thread.sleep(10000000000l);
	}
	
	@Test
	public void testResetPwd() throws Exception{
//		thirdPartyService.resetPwd(125);
	}
	
	@Test
	public void testModifyPwd() throws Exception {
//		Terminal terminal = terminalService.findById(125);
//		thirdPartyService.modifyPwd(3, "123454", terminal);
	}
	
	@Test
	public void tradesTest() throws Exception {
		thirdPartyService.pullTrades(259, 1, 1, 20);
	}

}
