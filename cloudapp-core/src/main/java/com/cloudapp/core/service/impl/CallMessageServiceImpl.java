package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.CallMessageDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallMessage;
import com.cloudapp.core.service.CallMessageService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CallMessageServiceImpl implements CallMessageService {

	@Resource
	private CallMessageDao dao;
	
	@Override
	public List<CallMessage> findAll(CallMessage msg) {
		return dao.findAll(msg);
	}

	@Override
	public CallMessage get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(CallMessage msg) throws DataAccessException {
		dao.insert(msg);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(CallMessage msg) throws DataAccessException {
		dao.update(msg);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateByCustomer(CallMessage msg) throws DataAccessException {
		dao.updateByCustomer(msg);
	}

	@Override
	public List<CallMessage> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delByDay(Integer day) {
		dao.delByDay(day);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delByCustomerId(Integer id) {
		dao.delByCustomerId(id);
	}
}