package com.cloudapp.core.dao;


import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.AllCaller;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface AllCallerDao extends Dao<AllCaller> {

    public void delByArray(Integer[] ids) throws DataAccessException;

    public AllCaller getByCode(String code);

}
