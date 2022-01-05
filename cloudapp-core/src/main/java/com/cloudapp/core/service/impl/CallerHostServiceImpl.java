package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.CallerHostDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerHost;
import com.cloudapp.core.service.CallerHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CallerHostServiceImpl implements CallerHostService {

    @Autowired
    private CallerHostDao dao;

    @Override
    public List<CallerHost> findAll(CallerHost callerHost) {
        return dao.findAll(callerHost);
    }

    @Override
    public List<CallerHost> findAllPage(Page page) {
        return dao.findAllPage(page);
    }

    @Override
    public CallerHost get(Integer id) {
        return dao.get(id);
    }


    @Transactional(readOnly = false)
    public void save(CallerHost callerHost) throws DataAccessException {
        dao.insert(callerHost);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(CallerHost callerHost) throws DataAccessException {
        dao.update(callerHost);
    }

    @Override
    public List<CallerHost> findPage(Page page) {
        return dao.findPage(page);
    }

    @Override
    @Transactional(readOnly = false)
    public void del(Integer id) {
        dao.delete(id);
    }

    @Override
    public CallerHost getCallerHost(CallerHost callerHost) {
        return dao.getCallerHost(callerHost);
    }

    @Override
    public Integer getCount(CallerHost callerHost) {
        return dao.getCount(callerHost);
    }
}
