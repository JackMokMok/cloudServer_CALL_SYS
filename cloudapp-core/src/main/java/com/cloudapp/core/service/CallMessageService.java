package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallMessage;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CallMessageService {
	
	public List<CallMessage> findPage(Page page);

	public List<CallMessage> findAll(CallMessage msg);
	
	public CallMessage get(Integer id);

	public void save(CallMessage msg) throws DataAccessException;

	public void update(CallMessage msg) throws DataAccessException;
	
	public void updateByCustomer(CallMessage msg) throws DataAccessException;
	
	public void del(Integer id);
	
	/*
	 * 删除 day天前的信息
	 */
	public void delByDay(Integer day);
	
	
	public void delByCustomerId(Integer id);
}