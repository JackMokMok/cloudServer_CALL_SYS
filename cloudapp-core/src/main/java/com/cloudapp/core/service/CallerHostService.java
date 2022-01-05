package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerHost;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface CallerHostService {

    public List<CallerHost> findPage(Page page);

    public List<CallerHost> findAllPage(Page page);

    public List<CallerHost> findAll(CallerHost callerHost);

    public CallerHost get(Integer id);

    public void save(CallerHost callerHost) throws DataAccessException;

    public void update(CallerHost callerHost) throws DataAccessException;

    public void del(Integer id);

    public CallerHost getCallerHost(CallerHost callerHost);

    public Integer getCount(CallerHost callerHost);

}
