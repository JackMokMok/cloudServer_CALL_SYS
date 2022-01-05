package com.cloudapp.core.dao;

import org.springframework.stereotype.Repository;
import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.Role;

@Repository
@MyBatisRepository
public interface RoleDao extends Dao<Role> {
}
