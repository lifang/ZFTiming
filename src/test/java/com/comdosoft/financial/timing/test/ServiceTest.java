package com.comdosoft.financial.timing.test;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.service.TradeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mvc.xml")
@ActiveProfiles("local")
public class ServiceTest {
	
	@Autowired
	private TradeService tradeService;
	
	@Test
	public void allTradeTypesTest(){
		Map<Integer,DictionaryTradeType> allTypes = tradeService.allTradeTypes();
		System.out.println(allTypes.size());
		Map<Integer,DictionaryTradeType> allTypesNew = tradeService.allTradeTypes();
		System.out.println(allTypesNew.size());
	}

}
