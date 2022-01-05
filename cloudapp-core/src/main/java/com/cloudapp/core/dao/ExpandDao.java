package com.cloudapp.core.dao;


import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Expand;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@MyBatisRepository
public interface ExpandDao extends Dao<Expand> {
	
	public List<Expand> findAllPage(Page page);

	public Expand getByMac(String mac);
	
}
