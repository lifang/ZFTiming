package com.comdosoft.financial.timing.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.trades.Profit;
import com.comdosoft.financial.timing.domain.trades.TradeRecord;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.SupportTradeType;
import com.comdosoft.financial.timing.mapper.trades.ProfitMapper;
import com.comdosoft.financial.timing.mapper.trades.TradeRecordMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.DictionaryTradeTypeMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.SupportTradeTypeMapper;
import com.comdosoft.financial.timing.utils.page.PageRequest;

@Service
public class TradeService {
	
	@Autowired
	private DictionaryTradeTypeMapper dictionaryTradeTypeMapper;
	@Autowired
	private TradeRecordMapper tradeRecordMapper;
	@Autowired
	private SupportTradeTypeMapper supportTradeTypeMapper;
	@Autowired
	private ProfitMapper profitMapper;
	
	@Cacheable("tradeTypes")
	public Map<Integer, DictionaryTradeType> allTradeTypes(){
		List<DictionaryTradeType> typeList = dictionaryTradeTypeMapper.selectAll();
		return typeList.stream().collect(
				Collectors.toMap(DictionaryTradeType::getId, Function.identity()));
	}

	public List<TradeRecord> searchNonCalculatedRecord(){
		PageRequest request = new PageRequest(1, 10);
		return tradeRecordMapper.selectPage(request,
				TradeRecord.ATTACH_STATUS_NO_CALCULATED,
				TradeRecord.TRADE_STATUS_SUCCESS);
	}
	
	public void updateRecord(TradeRecord tradeRecord){
		tradeRecordMapper.updateByPrimaryKey(tradeRecord);
	}
	
	/**
	 * 查询通道的交易费率
	 * @param channelId
	 * @param tradeTypeId
	 * @return
	 */
	public SupportTradeType selectSupportTradeType(Integer channelId,Integer tradeTypeId){
		return supportTradeTypeMapper.selectBaseSupportTradeType(channelId, tradeTypeId);
	}
	
	public void saveProfit(Profit profit) {
		profitMapper.insert(profit);
	}
}
