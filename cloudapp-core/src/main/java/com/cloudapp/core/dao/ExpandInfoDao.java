package com.cloudapp.core.dao;


import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.ExpandInfo;
import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface ExpandInfoDao extends Dao<ExpandInfo> {
	
	public ExpandInfo getByMac(String mac);
	
}
