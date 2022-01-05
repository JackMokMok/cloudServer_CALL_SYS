package com.cloudapp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudapp.core.constants.AdminStatusConstant;
import com.cloudapp.core.dao.AdminDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Admin;
import com.cloudapp.core.service.AdminService;
import com.cloudapp.lib.StringUtil;
import com.cloudapp.lib.TimeUtil;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminDao adminDao;

	@Override
	public List<Admin> findPage(Page page) {
		return adminDao.findPage(page);
	}
	
	@Override
	public List<Admin> findAll(Admin admin) {
		return adminDao.findAll(admin);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateSelfInfo(Admin admin) {
		Admin po = adminDao.get(admin.getId());
		po.setPassword(admin.getPassword());
		adminDao.update(admin);
	}

	@Override
	public Admin get(Integer id) {
		return adminDao.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Admin entity) throws DataAccessException {
		entity.setCreateTime(TimeUtil.now());
		adminDao.insert(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Admin entity) throws DataAccessException {
		adminDao.update(entity);
	}

	@Override
	public Admin login(String username, String password) {
		Admin user = new Admin();
		user.setUsername(username);
		user.setPassword(StringUtil.encPassword(password));
		user.setStatus(AdminStatusConstant.COMMON.getIndex());
		return adminDao.login(user);
	}
	
	@Override
	public Admin loginByOpenid(String openid) {
		Admin user = new Admin();
		user.setWxOpenid(openid);
		user.setStatus(AdminStatusConstant.COMMON.getIndex());
		return adminDao.loginByOpenid(user);
	}
	
	@Override
	public void del(String[] ids) {
		for (String id : ids) {
			if (StringUtil.isValid(id)) {
				adminDao.delete(Integer.valueOf(id));
			}
		}
	}
	
	@Override
	public void del(Integer id) {
		adminDao.delete(id);
	}
	
	@Override
	public Admin findUser(Admin user){
		if (user != null) {
			Admin checkUser = new Admin();
			checkUser.setUsername(user.getUsername());
			List<Admin> result = adminDao.findAll(checkUser);
			if(result.size() >= 1) {
				return result.get(0);
			}
		}
		return null;
	}

	@Override
	public Boolean isCheckName(Integer id, String name) {
		Admin admin = new Admin();
		admin.setUsername(name);
		List<Admin> list = adminDao.findAll(admin);
		if (list == null || list.isEmpty()) {
			return false;
		} else {
			if(id==null){
				return true;
			}else{
				for(Admin a: list){
					if(!a.getId().equals(id)){
						return true;
					}
				}
				return false;
			}
		}
	}
	
}
