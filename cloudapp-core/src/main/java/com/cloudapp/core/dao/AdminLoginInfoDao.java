package com.cloudapp.core.dao;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.AdminLoginInfo;

@Repository
@MyBatisRepository
public interface AdminLoginInfoDao extends Dao<AdminLoginInfo> {
	
	public void delByDay(Integer day) throws DataAccessException;
}
