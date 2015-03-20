package com.comdosoft.financial.timing.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.mapper.zhangfu.DictionaryTradeTypeMapper;

@Service
public class TradeService {
	
	@Autowired
	private DictionaryTradeTypeMapper dictionaryTradeTypeMapper;
	
	@Cacheable("tradeTypes")
	public Map<Integer, DictionaryTradeType> allTradeTypes(){
		List<DictionaryTradeType> typeList = dictionaryTradeTypeMapper.selectAll();
		return typeList.stream().collect(
				Collectors.toMap(DictionaryTradeType::getId, Function.identity()));
	}

	
}
