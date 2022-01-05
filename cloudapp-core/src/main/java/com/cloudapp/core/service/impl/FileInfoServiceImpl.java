package com.cloudapp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudapp.core.dao.FileInfoDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.FileInfo;
import com.cloudapp.core.service.FileInfoService;

@Service
@Transactional(readOnly = true)
public class FileInfoServiceImpl implements FileInfoService {

	@Resource
	private FileInfoDao dao;
	
	@Override
	public List<FileInfo> findAll(FileInfo info) {
		return dao.findAll(info);
	}

	@Override
	public FileInfo get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(FileInfo info) throws DataAccessException {
		dao.insert(info);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(FileInfo info) throws DataAccessException {
		dao.update(info);
	}

	@Override
	public List<FileInfo> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	public void del(Integer id) {
		dao.delete(id);
	}
}