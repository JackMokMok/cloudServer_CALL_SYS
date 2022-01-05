package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.AllCaller;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface AllCallerService {

    public List<AllCaller> findPage(Page page);

    public List<AllCaller> findAll(AllCaller allCaller);

    public AllCaller get(Integer id);

    public void save(AllCaller allCaller) throws DataAccessException;

    public void update(AllCaller allCaller) throws DataAccessException;

    public void del(Integer id);

    public void delByArray(Integer[] ids) throws DataAccessException;

    public AllCaller getByCode(String code);

}
