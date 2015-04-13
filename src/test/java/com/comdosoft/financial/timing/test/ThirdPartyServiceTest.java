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
	
	@Test
	public void testSubmitOpeningApply() throws Exception{
		thirdPartyService.submitOpeningApply(125, 3);
		Thread.sleep(10000000000l);
	}

}
