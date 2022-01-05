package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.CustomerDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CusGroup;
import com.cloudapp.core.entity.Customer;
import com.cloudapp.core.service.CustomerService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao dao;
	
	@Override
	public List<Customer> findAll(Customer customer) {
		return dao.findAll(customer);
	}

	@Override
	public Customer get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(Customer customer) throws DataAccessException {
		dao.insert(customer);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Customer customer) throws DataAccessException {
		dao.update(customer);
	}
	
	/**
	 * 批量授权
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateAuth(Customer customer) throws DataAccessException {
		dao.updateAuth(customer);
	}
	
	/**
	 * 批量更新分组
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateGroup(CusGroup group) throws DataAccessException {
		dao.updateGroup(group);
	}

	@Override
	public List<Customer> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}
	
	@Override
	public Boolean isCheckCus(Customer customer) {
		Customer cus = new Customer();
		cus.setName(customer.getName());
		List<Customer> list = dao.findAll(cus);
		if (list == null || list.isEmpty()) {
			return false;
		} else {
			if(customer.getId()==null){
				return true;
			}else{
				for(Customer a: list){
					if(!a.getId().equals(customer.getId())){
						return true;
					}
				}
				return false;
			}
		}
	}

	@Override
	public List<Customer> getCustomers(Customer customer) {
		return dao.getCustomers(customer);
	}
}