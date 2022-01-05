package com.cloudapp.core.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.FileInfo;

public interface FileInfoService {
	
	public List<FileInfo> findPage(Page page);

	public List<FileInfo> findAll(FileInfo info);
	
	public FileInfo get(Integer id);

	public void save(FileInfo info) throws DataAccessException;

	public void update(FileInfo info) throws DataAccessException;
	
	public void del(Integer id);
}