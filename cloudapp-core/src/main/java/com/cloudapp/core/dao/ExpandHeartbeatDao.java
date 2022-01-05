package com.cloudapp.core.dao;


import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.ExpandHeartbeat;
import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface ExpandHeartbeatDao extends Dao<ExpandHeartbeat> {
	
	public ExpandHeartbeat getByMac(String mac);
}
