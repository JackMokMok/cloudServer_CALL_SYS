package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.AllCallerDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.AllCaller;
import com.cloudapp.core.service.AllCallerService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AllCallerServiceImpl implements AllCallerService {

    @Resource
    private AllCallerDao dao;

    @Override
    public List<AllCaller> findPage(Page page) {
        return dao.findPage(page);
    }

    @Override
    public List<AllCaller> findAll(AllCaller allCaller) {
        return dao.findAll(allCaller);
    }

    @Override
    public AllCaller get(Integer id) {
        return dao.get(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(AllCaller allCaller) throws DataAccessException {
        dao.insert(allCaller);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(AllCaller allCaller) throws DataAccessException {
        dao.update(allCaller);
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
    public AllCaller getByCode(String code) {
        return dao.getByCode(code);
    }
}
