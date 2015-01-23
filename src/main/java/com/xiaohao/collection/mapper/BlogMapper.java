package com.xiaohao.collection.mapper;

import com.xiaohao.collection.entity.Blog;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * BlogMapper数据库操作接口类
 * 
 **/

public interface BlogMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Blog  selectById(@Param("id") Long id);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteById(@Param("id") Long id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(Blog record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(Blog record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByIdSelective(Blog record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateById(Blog record);

}