package com.cloudapp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudapp.core.dao.AdminLoginInfoDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.AdminLoginInfo;
import com.cloudapp.core.service.AdminLoginInfoService;

@Service
@Transactional(readOnly = true)
public class AdminLoginInfoServiceImpl implements AdminLoginInfoService {

	@Resource
	private AdminLoginInfoDao dao;
	
	@Override
	public List<AdminLoginInfo> findAll(AdminLoginInfo info) {
		return dao.findAll(info);
	}

	@Override
	public AdminLoginInfo get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(AdminLoginInfo info) throws DataAccessException {
		dao.insert(info);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(AdminLoginInfo info) throws DataAccessException {
		dao.update(info);
	}

	@Override
	public List<AdminLoginInfo> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	public void del(Integer id) {
		dao.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delByDay(Integer day) {
		dao.delByDay(day);
	}
}