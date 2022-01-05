package com.cloudapp.core.dao;


import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.CallerGroup;
import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface CallerGroupDao extends Dao<CallerGroup> {
}
