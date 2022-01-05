package com.cloudapp.core.dao;


import org.springframework.stereotype.Repository;

import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.FileInfo;

@Repository
@MyBatisRepository
public interface FileInfoDao extends Dao<FileInfo> {
}
