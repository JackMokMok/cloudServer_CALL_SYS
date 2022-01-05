package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.CallerDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Caller;
import com.cloudapp.core.entity.CallerCategory;
import com.cloudapp.core.entity.CallerGroup;
import com.cloudapp.core.service.CallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CallerServiceImpl implements CallerService {

    @Autowired
    private CallerDao dao;

    @Override
    public List<Caller> findPage(Page page) {
        return dao.findPage(page);
    }

    @Override
    public List<Caller> findAll(Caller caller) {
        return dao.findAll(caller);
    }

    @Override
    public Caller get(Integer id) {
        return dao.get(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Caller caller) throws DataAccessException {
        dao.insert(caller);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Caller caller) throws DataAccessException {
        dao.update(caller);
    }

    @Override
    @Transactional(readOnly = false)
    public void del(Integer id) {
        dao.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void delByArray(Integer[] ids) throws DataAccessException {
        dao.delByArray(ids);
    }

    @Override
    public void delByMac(String mac) throws DataAccessException {
        dao.delByMac(mac);
    }

    @Override
    public Caller getCaller(Caller caller) {
        return dao.getCaller(caller);
    }

    @Override
    public void updateGroup(CallerGroup group) {
        dao.updateGroup(group);
    }

    @Override
    public void updateCategory(CallerCategory category) {
        dao.updateCategory(category);
    }
}
