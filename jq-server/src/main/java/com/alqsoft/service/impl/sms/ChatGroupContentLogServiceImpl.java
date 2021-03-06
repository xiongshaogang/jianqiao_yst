package com.alqsoft.service.impl.sms;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.filter.DynamicSpecifications;
import org.alqframework.orm.filter.SearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.sms.ChatGroupContentLogDao;
import com.alqsoft.entity.sms.ChatGroupContentLog;
import com.alqsoft.service.sms.ChatGroupContentLogService;
import com.alqsoft.utils.easyuiweb.EasyWebUtils;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: ChatGroupContentLogServiceImpl.java
 * @Description: 描述
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午2:48:05 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Service
@Transactional(readOnly=true)
public class ChatGroupContentLogServiceImpl implements ChatGroupContentLogService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(ChatGroupContentLogServiceImpl.class);
	@Autowired
	private ChatGroupContentLogDao smsDao;
	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			smsDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public ChatGroupContentLog get(Long arg0) {
		
		return smsDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public ChatGroupContentLog saveAndModify(ChatGroupContentLog arg0) {
		return smsDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<ChatGroupContentLog>> getChatGroupContentLogPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<ChatGroupContentLog> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				ChatGroupContentLog.class);
		Page<ChatGroupContentLog> Page = smsDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(ChatGroupContentLog.class, Page);
	}

	/**
	 * 通过唯一标识获取用户群发日志表
	 */
	@Override
	public ChatGroupContentLog getChatGroupContentLogByUniqueKey(String uniqueKey) {
		return smsDao.getChatGroupContentLogByUniqueKey(uniqueKey);
	}
	
	@Override
	public EasyuiWebResult<List<ChatGroupContentLog>> getChatGroupContentLogPageByMobile(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<ChatGroupContentLog> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				ChatGroupContentLog.class);
		Page<ChatGroupContentLog> Page = smsDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyWebUtils.returnPage(ChatGroupContentLog.class, Page);
	}
}
