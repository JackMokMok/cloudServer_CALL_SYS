package com.cloudapp.core.dao;

import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.Caller;
import com.cloudapp.core.entity.CallerCategory;
import com.cloudapp.core.entity.CallerGroup;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface CallerDao extends Dao<Caller> {

    public void delByArray(Integer[] ids) throws DataAccessException;

    public void delByMac(String mac) throws DataAccessException;

    public Caller getCaller(Caller caller);

    public void updateGroup(CallerGroup group);

    public void updateCategory(CallerCategory category);

}
