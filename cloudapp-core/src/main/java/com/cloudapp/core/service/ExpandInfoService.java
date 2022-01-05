package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.ExpandInfo;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface ExpandInfoService {
	
	public List<ExpandInfo> findPage(Page page);

	public List<ExpandInfo> findAll(ExpandInfo info);
	
	public ExpandInfo get(Integer id);
	
	public ExpandInfo getByMac(String mac);

	public void save(ExpandInfo info) throws DataAccessException;

	public void update(ExpandInfo info) throws DataAccessException;

	public void del(Integer id);
}