package com.cloudapp.core.dao;


import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.CusGroup;
import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface CusGroupDao extends Dao<CusGroup> {
}
