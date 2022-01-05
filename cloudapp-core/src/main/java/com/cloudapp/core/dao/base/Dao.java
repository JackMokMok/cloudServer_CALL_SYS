package com.cloudapp.core.dao.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cloudapp.core.entity.base.Entity;

/**
 * 数据库访问基类接口（通用增删改查）
 * 
 * @author 陈远
 */
public interface Dao<T extends Entity> {

	/**
	 * 获取对象
	 * 
	 * @param id
	 *            主键
	 */
	public T get(Serializable id);

	/**
	 * 新增对象
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	public void insert(T entity) throws DataAccessException;

	/**
	 * 更新对象（所有字段更新）
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	public void update(T entity) throws DataAccessException;

	/**
	 * 更新对象（选择性更新，针对entity中属性不为null的字段更新）
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	public void updateSelective(T entity) throws DataAccessException;

	/**
	 * 删除对象
	 * 
	 * @param id
	 *            主键
	 * @throws DataAccessException
	 */
	public void delete(Serializable id) throws DataAccessException;

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public List<T> findPage(Page page);

	public List<T> findAll(T entity);

}
