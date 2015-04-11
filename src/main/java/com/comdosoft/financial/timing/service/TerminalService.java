package com.comdosoft.financial.timing.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.DictionaryOpenPrivateInfo;
import com.comdosoft.financial.timing.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.timing.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.timing.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.timing.domain.zhangfu.Terminal;
import com.comdosoft.financial.timing.domain.zhangfu.TerminalTradeTypeInfo;
import com.comdosoft.financial.timing.mapper.zhangfu.DictionaryOpenPrivateInfoMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.OpeningApplieMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.OperateRecordMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.timing.mapper.zhangfu.TerminalTradeTypeInfoMapper;

@Service
public class TerminalService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TerminalService.class);

	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private OpeningApplieMapper openingApplieMapper;
	@Autowired
	private TerminalTradeTypeInfoMapper terminalTradeTypeInfoMapper;
	@Autowired
	private OperateRecordMapper operateRecordMapper;
	@Autowired
	private DictionaryOpenPrivateInfoMapper dictionaryOpenPrivateInfoMapper;
	@Value("${file.http.domain}")
	private String fileHttpRoot;
	
	public OpeningApplie findOpeningAppylByTerminalId(Integer terminalId){
		return openingApplieMapper.selectOpeningApplie(terminalId);
	}
	
	public Terminal findById(Integer id) {
		return terminalMapper.selectByPrimaryKey(id);
	}
	
	public void updateOpeningApply(OpeningApplie oa) {
		openingApplieMapper.updateByPrimaryKey(oa);
	}
	
	public Terminal findBySerial(String serial) {
		return terminalMapper.selectBySerial(serial);
	}
	
	public void updateTerminal(Terminal terminal) {
		terminalMapper.updateByPrimaryKey(terminal);
	}
	
	//更新TerminalTradeTypeInfo状态
	public void updateTerminalTradeTypeStatus(Integer status, Integer terminalId, Integer tradeTypeId){
		terminalTradeTypeInfoMapper.updateStatus(status, terminalId, tradeTypeId);
	}
	
	public void updateTerminalTradeTypeStatus(Integer status, Integer terminalId){
		terminalTradeTypeInfoMapper.updateStatus(status, terminalId, null);
	}
	
	@Cacheable("allOpenPrivateInfoMap")
	public Map<Integer, DictionaryOpenPrivateInfo> allOpenPrivateInfos(){
		List<DictionaryOpenPrivateInfo> openPrivateInfos = dictionaryOpenPrivateInfoMapper.selectAll();
		return openPrivateInfos.stream().collect(
				Collectors.toMap(DictionaryOpenPrivateInfo::getId, Function.identity()));
	}
	
	public List<TerminalTradeTypeInfo> findTerminalTradeTypeInfos(Integer terminalId){
		return terminalTradeTypeInfoMapper.selectTerminalTradeTypeInfos(terminalId);
	}
	
	public InputStream path2InputStream(String path){
		try {
			UrlResource ur = new UrlResource(fileHttpRoot+path);
			return ur.getInputStream();
		} catch (IOException e) {
			LOG.error("",e);
		}
		return null;
	}
	
	public Integer findNotTradeTypeStatus(Terminal terminal) {
		List<TerminalTradeTypeInfo> infos = terminalTradeTypeInfoMapper.selectTerminalTradeTypeInfos(terminal.getId());
		for(TerminalTradeTypeInfo info : infos) {
			if(info.getTradeTypeId() != DictionaryTradeType.ID_TRADE){
				return info.getStatus();
			}
		}
		return null;
	}
	
	public void updateNotTradeTypeStatus(Terminal terminal,String idCard,String name){
		List<TerminalTradeTypeInfo> infos = terminalTradeTypeInfoMapper.selectTerminalTradeTypeInfos(terminal.getId());
		byte terminalStatus = Terminal.STATUS_OPENED;
		for(TerminalTradeTypeInfo info : infos){
			if(info.getTradeTypeId() == DictionaryTradeType.ID_TRADE){
				if(info.getStatus() == TerminalTradeTypeInfo.STATUS_NO_OPEN){
					terminalStatus = Terminal.STATUS_PART_OPENED;//如果交易类型未开通，终端设为部分开通
				}
			}else{
				info.setStatus(TerminalTradeTypeInfo.STATUS_OPENED);
				terminalTradeTypeInfoMapper.updateByPrimaryKey(info);
			}
		}
		
		terminal.setStatus(terminalStatus);
		terminalMapper.updateByPrimaryKey(terminal);
		
		//将这次的传递过来的参数拼接存入到operate_records中
		saveOperateRecord(
				OperateRecord.TYPE_TRADE_RECORD,
				terminal.getId(),
				OperateRecord.TARGET_TYPE_TERMINAL,
				MessageFormat.format("terminal[{0}] idCard:{1},name:{2}", terminal.getSerialNum(), idCard,name));
	}
	
	public void recordSubmitFail(OpeningApplie oa,String type,String code,String msg){
		oa.setSubmitStatus(OpeningApplie.SUBMIT_STATUS_FAIL);
		updateOpeningApply(oa);
		//失败内容记录到operate_records
		saveOperateRecord(
				OperateRecord.TYPE_OPENING_APPLY,
				oa.getId(),
				OperateRecord.TARGET_TYPE_OPENING_APPYL,
				MessageFormat.format("[{0}] code:{1},msg:{2}", type, code,msg));
	}
	
	public void saveOperateRecord(Integer type,Integer targetId,Byte targetType,String content) {
		OperateRecord or = new OperateRecord();
		or.setCreatedAt(new Date());
		or.setUpdatedAt(new Date());
		or.setTypes(type);
		or.setOperateTargetId(targetId);
		or.setOperateTargetType(targetType);
		or.setContent(content);
		operateRecordMapper.insert(or);
	}
}
