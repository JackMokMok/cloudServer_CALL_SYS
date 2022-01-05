package com.cloudapp.core.dao;

import org.springframework.stereotype.Repository;

import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.Admin;

@Repository
@MyBatisRepository
public interface AdminDao extends Dao<Admin> {

	// 用户登陆（使用crop, user, pass）
	public Admin login(Admin user);
	
	// 用户登陆（使用 wx_openid）
	public Admin loginByOpenid(Admin user);

}
