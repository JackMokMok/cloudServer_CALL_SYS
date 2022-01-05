package com.cloudapp.core.dao;


import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.CusGroup;
import com.cloudapp.core.entity.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@MyBatisRepository
public interface CustomerDao extends Dao<Customer> {
	
	public void updateGroup(CusGroup group) throws DataAccessException;;
	
	public void updateAuth(Customer customer) throws DataAccessException;

	public List<Customer> getCustomers(Customer customer) throws DataAccessException;
}
