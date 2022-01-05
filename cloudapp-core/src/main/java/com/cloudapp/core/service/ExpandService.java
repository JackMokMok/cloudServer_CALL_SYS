package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Expand;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface ExpandService {
	
	public List<Expand> findPage(Page page);
	
	public List<Expand> findAllPage(Page page);

	public List<Expand> findAll(Expand device);
	
	public Expand get(Integer id);
	
	public void save(Expand expand) throws DataAccessException;

	public void update(Expand expand) throws DataAccessException;
	
	public void del(Integer id);

	public Expand getByMac(String mac);
}