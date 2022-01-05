package com.cloudapp.core.dao;

import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerHost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@MyBatisRepository
public interface CallerHostDao extends Dao<CallerHost> {

    public List<CallerHost> findAllPage(Page page);

    public CallerHost getCallerHost(CallerHost callerHost);

    public Integer getCount(CallerHost callerHost);

}
