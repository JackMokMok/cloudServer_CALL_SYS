package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CusGroup;
import com.cloudapp.core.entity.Customer;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CustomerService {
	
	public List<Customer> findPage(Page page);

	public List<Customer> findAll(Customer customer);
	
	public Customer get(Integer id);

	public void save(Customer customer) throws DataAccessException;

	public void update(Customer customer) throws DataAccessException;
	
	public void updateAuth(Customer customer) throws DataAccessException;
	
	public void updateGroup(CusGroup group) throws DataAccessException;

	public void del(Integer id);
	
	public Boolean isCheckCus(Customer customer);

	public List<Customer> getCustomers(Customer customer);
}