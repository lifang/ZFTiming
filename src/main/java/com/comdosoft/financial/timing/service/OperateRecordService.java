/**
 * 
 */
package com.comdosoft.financial.timing.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.timing.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.timing.mapper.zhangfu.OperateRecordMapper;

/**
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年4月16日 下午1:30:38
 */
@Service
public class OperateRecordService {
	
	@Autowired
	private OperateRecordMapper operateRecordMapper;
	
	public void saveOperateRecord(Integer type ,String content) {
		OperateRecord or = new OperateRecord();
		or.setCreatedAt(new Date());
		or.setUpdatedAt(new Date());
		or.setTypes(type);
		or.setContent(content);
		operateRecordMapper.insert(or);
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
