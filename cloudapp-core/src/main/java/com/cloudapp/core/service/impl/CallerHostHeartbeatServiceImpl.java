package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.CallerHostHeartbeatDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerHostHeartbeat;
import com.cloudapp.core.service.CallerHostHeartbeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CallerHostHeartbeatServiceImpl implements CallerHostHeartbeatService {

    @Autowired
    private CallerHostHeartbeatDao dao;

    @Override
    public List<CallerHostHeartbeat> findPage(Page page) {
        return dao.findPage(page);
    }

    @Override
    public List<CallerHostHeartbeat> findAll(CallerHostHeartbeat heartbeat) {
        return dao.findAll(heartbeat);
    }

    @Override
    public CallerHostHeartbeat get(Integer id) {
        return dao.get(id);
    }

    @Override
    public Integer getCount(Integer status) {
        return dao.getCount(status);
    }

    @Override
    public CallerHostHeartbeat getByMac(String mac) {
        return dao.getByMac(mac);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(CallerHostHeartbeat heartbeat) throws DataAccessException {
        dao.insert(heartbeat);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(CallerHostHeartbeat heartbeat) throws DataAccessException {
        dao.update(heartbeat);
    }

    @Override
    @Transactional(readOnly = false)
    public void del(Integer id) {
        dao.delete(id);
    }
}
