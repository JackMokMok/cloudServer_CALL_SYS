package com.cloudapp.core.dao;

import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.CallMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface CallMessageDao extends Dao<CallMessage> {
	
	public void delByDay(Integer day) throws DataAccessException;
	
	public void updateByCustomer(CallMessage msg) throws DataAccessException;
	
	public void delByCustomerId(Integer id) throws DataAccessException;
}
