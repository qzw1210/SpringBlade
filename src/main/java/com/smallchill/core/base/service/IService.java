/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.core.base.service;

import java.util.List;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.interfaces.ICURD;
import com.smallchill.core.toolbox.support.BladePage;

public interface IService<M> {

	/**
	 * 根据主键找到实体类 
	 * @param id 主键
	 * @return M 实体类
	 */
	public M findById(Object id);

	/**
	 * 根据自定义sql找到实体类集合 
	 * @param sql 自定义sql模板
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return List<M> 实体类集合
	 */
	public List<M> find(String sql, Object modelOrMap);

	/**
	 * 根据实体类查询符合要求的前N条数据 
	 * @param topNum 返回数据总数
	 * @param model 实体类
	 * @return List<M> 实体类集合
	 */
	public List<M> findTop(int topNum, M model);
	
	/**
	 * 根据sql查询符合要求的前N条数据 
	 * @param topNum 返回数据总数
	 * @param sqlTemplate sql语句
	 * @return
	 */
	public List<M> findTop(int topNum, String sqlTemplate);
	
	/**
	 * 根据sql查询符合要求的前N条数据 
	 * @param topNum 返回数据总数
	 * @param sqlTemplate sql语句
	 * @param modelOrMap 实体类或map
	 * @return
	 */
	public List<M> findTop(int topNum, String sqlTemplate, Object modelOrMap);

	/**
	 * 找到所有的实体类集合 
	 * @return List<M> 实体类集合
	 */
	public List<M> findAll();

	/**
	 * 根据条件查询相匹配的数据 
	 * @param where 查询字段
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return List<M> 实体类集合
	 */
	public List<M> findBy(String where, Object modelOrMap);

	/**
	 * 根据条件查询相匹配的数据 
	 * @param columns 返回字段
	 * @param where 查询字段
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return List<M> 实体类集合
	 */
	public List<M> findBy(String columns, String where, Object modelOrMap);

	/**
	 * 根据实体类找到数据库相匹配数据 
	 * @param model 实体类
	 * @return List<M> 实体集合
	 */
	public List<M> findByTemplate(M model);

	/**
	 * 根据自定义sql语句查询数据 
	 * @param sql sql模板
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return M 实体类
	 */
	public M findFirst(String sql, Object modelOrMap);

	/**
	 * 根据where条件查询第一条数据 
	 * @param where 查询字段
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return M 实体类
	 */
	public M findFirstBy(String where, Object modelOrMap);

	/**
	 * 通用新增 
	 * @param model 实体类
	 * @return boolean
	 */
	public boolean save(M model);
	
	/**
	 * 新增返回int型主键 
	 * @param model 实体类
	 * @return int
	 */
	public int saveRtId(M model);

	/**
	 * 新增返回String型主键 
	 * @param model 实体类
	 * @return String
	 */
	public String saveRtStrId(M model);

	/**
	 * 新增一条数据,并自动将主键反射到字段中
	 * @param model
	 */
	public boolean saveAndSetKey(M model);

	/**
	 * 通用修改(null的不入库) 
	 * @param model 实体类
	 * @return boolean
	 */
	public boolean update(M model);

	/**
	 * 通用修改(null的也入库) 
	 * @param model
	 * @return boolean
	 */
	public boolean updateEveryCol(M model);

	/**
	 * 根据一个model更新一个表内所有数据(非特殊情况慎重使用) 
	 * @param model
	 * @return int
	 */
	public int updateAllRecords(M model);

	/**
	 * 根据条件修改 
	 * @param set 修改字段
	 * @param where 查询字段
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return boolean
	 */
	public boolean updateBy(String set, String where, Object modelOrMap);
	
	/**   
	 * 根据实体类集合修改
	 * @param list 实体类集合
	 * @return int[]
	*/
	public int[] updateBathById(List<M> list);

	/**
	 * 通用删除一条数据 
	 * @param id 主键值
	 * @return int 删除条数
	 */
	public int delete(Object id);

	/**
	 * 通用删除多条数据 
	 * @param ids 主键值集合
	 * @return int 删除条数
	 */
	public int deleteByIds(String ids);

	/**
	 * 根据字段名以及字段值删除多条数据
	 * @param col 字段名
	 * @param ids 键值集合
	 * @return int 删除条数
	 */
	public int deleteByCols(String col, String ids);


	/**   
	 * 根据表名 ,字段名以及字段值删除多条数据
	 * @param table 表名
	 * @param col 字段名
	 * @param ids 键值集合
	 * @return int 删除条数
	*/
	public int deleteTableByCols(String table, String col, String ids);
	
	/**   
	 * 根据sql模板删除
	 * @param sqlTemplate sql模板
	 * @return int 删除条数
	*/
	public int deleteBy(String sqlTemplate);

	/**   
	 * 根据条件删除
	 * @param where 查询字段
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return int 删除条数
	*/
	public int deleteBy(String where, Object modelOrMap);

	/**   
	 * 查询总数
	 * @return int
	*/
	public long total();

	/**   
	 * 查询符合通用实体类的总数
	 * @param model 通用实体
	 * @return long 总数
	*/
	public long count(M model);

	/**   
	 * 根据条件查询总数
	 * @param where 查询字段
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return int 总数
	*/
	public int count(String where, Object modelOrMap);

	/**   
	 * 查询自定义sql的总数
	 * @param sqlTemplate 自定义sql
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return int 总数
	*/
	public int countBy(String sqlTemplate, Object modelOrMap);
	
	/**   
	 * 对整表的分页
	 * @param start 当前分页数
	 * @param size 每页的数据量
	 * @return List<M> 实体类集合
	*/
	public List<M> getList(int start, int size);

	/**   
	 * 找出符合实体类条件的分页
	 * @param model 实体类
	 * @param start 当前分页数
	 * @param size 每页的数据量
	 * @return List<M> 实体类集合
	*/
	public List<M> getList(M model, int start, int size);
	
	/**   
	 * 找出符合实体类条件的分页
	 * @param sqlTemplate sql语句
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @param start 当前分页数
	 * @param size 每页的数据量
	 * @return List<M> 实体类集合
	*/
	public List<M> getList(String sqlTemplate, Object modelOrMap, int start, int size);
	
	/**
	 * 分页
	 * @param sqlTemplate sql语句
	 * @param paras	参数
	 * @param start	页号
	 * @param size	数量
	 * @return
	 */
	public BladePage<M> paginate(String sqlTemplate, Object paras, int start, int size);
	
	/**   
	 * 根据自定义sql查询是否存在
	 * @param sqlTemplate 自定义sql
	 * @param modelOrMap 实体类或者Map(查询条件)
	 * @return boolean
	*/
	public boolean isExist(String sqlTemplate, Object modelOrMap);

	/**   
	 * 将绑定的实体类字段信息输出到控制台
	*/
	public void createPojoToConsole();
	

	/**
	 * 通用新增
	 * 
	 * @param model  实体类
	 * @param ac	 aop上下文
	 * @return boolean
	 */
	public boolean save(M model, AopContext ac);

	/**
	 * 通用修改(null的不入库)
	 * 
	 * @param model  实体类
	 * @param ac	 aop上下文
	 * @return boolean
	 */
	public boolean update(M model, AopContext ac);

	/**
	 * 通用删除多条数据(物理)
	 * 
	 * @param ids   主键值集合
	 * @param ac	aop上下文
	 * @return int  删除条数
	 */
	public boolean removeByIds(String ids, AopContext ac);

	/**
	 * 通用删除多条数据(逻辑)
	 * 
	 * @param ids   主键值集合
	 * @param ac	aop上下文
	 * @return int  删除条数
	 */
	public boolean delByIds(String ids, AopContext ac);

	/**
	 * 通用还原多条数据
	 * 
	 * @param ids   主键值集合
	 * @param ac	aop上下文
	 * @return int  删除条数
	 */
	public boolean restoreByIds(String ids, AopContext ac);
	
	/**
	 * 通用新增
	 * 
	 * @param model 实体类
	 * @param ac	 aop上下文
	 * @param intercept 业务拦截器
	 * @return boolean
	 */
	public boolean save(M model, AopContext ac, ICURD intercept);

	/**
	 * 通用修改(null的不入库)
	 * 
	 * @param model  实体类
	 * @param ac	 aop上下文
	 * @param intercept 业务拦截器
	 * @return boolean
	 */
	public boolean update(M model, AopContext ac, ICURD intercept);

	/**
	 * 通用删除多条数据(物理)
	 * 
	 * @param ids    主键值集合
	 * @param ac	 aop上下文
	 * @param intercept 业务拦截器
	 * @return int 删除条数
	 */
	public boolean removeByIds(String ids, AopContext ac, ICURD intercept);

	/**
	 * 通用删除多条数据(逻辑)
	 * 
	 * @param ids    主键值集合
	 * @param ac	 aop上下文
	 * @param intercept 业务拦截器
	 * @return int 删除条数
	 */
	public boolean delByIds(String ids, AopContext ac, ICURD intercept);

	/**
	 * 通用还原多条数据
	 * 
	 * @param ids    主键值集合
	 * @param ac	 aop上下文
	 * @param intercept 业务拦截器
	 * @return int 删除条数
	 */
	public boolean restoreByIds(String ids, AopContext ac, ICURD intercept);
	
}
