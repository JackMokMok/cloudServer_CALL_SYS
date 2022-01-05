package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerHostHeartbeat;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CallerHostHeartbeatService {

    public List<CallerHostHeartbeat> findPage(Page page);

    public List<CallerHostHeartbeat> findAll(CallerHostHeartbeat heartbeat);

    public CallerHostHeartbeat get(Integer id);

    public Integer getCount(Integer status);

    public CallerHostHeartbeat getByMac(String mac);

    public void save(CallerHostHeartbeat heartbeat) throws DataAccessException;

    public void update(CallerHostHeartbeat heartbeat) throws DataAccessException;

    public void del(Integer id);

}
