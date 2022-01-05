package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.ExpandHeartbeat;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface ExpandHeartbeatService {
	
	public List<ExpandHeartbeat> findPage(Page page);

	public List<ExpandHeartbeat> findAll(ExpandHeartbeat heartbeat);
	
	public ExpandHeartbeat get(Integer id);
	
	public ExpandHeartbeat getByMac(String mac);

	public void save(ExpandHeartbeat heartbeat) throws DataAccessException;

	public void update(ExpandHeartbeat heartbeat) throws DataAccessException;

	public void del(Integer id);
}