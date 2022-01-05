package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.ExpandHeartbeatDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.ExpandHeartbeat;
import com.cloudapp.core.service.ExpandHeartbeatService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExpandHeartbeatServiceImpl implements ExpandHeartbeatService {

	@Resource
	private ExpandHeartbeatDao dao;
	
	@Override
	public List<ExpandHeartbeat> findAll(ExpandHeartbeat heartbeat) {
		return dao.findAll(heartbeat);
	}

	@Override
	public ExpandHeartbeat get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public ExpandHeartbeat getByMac(String mac) {
		return dao.getByMac(mac);
	}

	@Transactional(readOnly = false)
	public void save(ExpandHeartbeat heartbeat) throws DataAccessException {
		dao.insert(heartbeat);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(ExpandHeartbeat heartbeat) throws DataAccessException {
		dao.update(heartbeat);
	}

	@Override
	public List<ExpandHeartbeat> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}
}