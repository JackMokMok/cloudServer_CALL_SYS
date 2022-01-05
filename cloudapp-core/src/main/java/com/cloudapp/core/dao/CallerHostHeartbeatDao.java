package com.cloudapp.core.dao;

import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.CallerHostHeartbeat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface CallerHostHeartbeatDao extends Dao<CallerHostHeartbeat> {

    public CallerHostHeartbeat getByMac(String mac);

    public Integer getCount(@Param("status") Integer status);

}
