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

import com.comdosoft.financial.timing.scheduling.CalculateProfit;

/**
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年4月16日 下午1:39:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mvc.xml")
@ActiveProfiles("demo")
public class SchedueTest {
	
	@Autowired
	private CalculateProfit calculateProfit;

	@Test
	public void calculate() {
		calculateProfit.job();
	}
}
