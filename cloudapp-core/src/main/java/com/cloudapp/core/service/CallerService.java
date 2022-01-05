package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Caller;
import com.cloudapp.core.entity.CallerCategory;
import com.cloudapp.core.entity.CallerGroup;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CallerService {

    public List<Caller> findPage(Page page);

    public List<Caller> findAll(Caller caller);

    public Caller get(Integer id);

    public void save(Caller caller) throws DataAccessException;

    public void update(Caller caller) throws DataAccessException;

    public void del(Integer id);

    public void delByArray(Integer[] ids) throws DataAccessException;

    public void delByMac(String mac) throws DataAccessException;

    public Caller getCaller(Caller caller);

    public void updateGroup(CallerGroup group);

    public void updateCategory(CallerCategory category);

}
