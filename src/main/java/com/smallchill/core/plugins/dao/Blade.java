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
package com.smallchill.core.plugins.dao;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.db.ClassDesc;
import org.beetl.sql.core.db.KeyHolder;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.annotation.DbName;
import com.smallchill.core.constant.Const;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.connection.ConnectionPlugin;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.support.BladePage;

/**
 * beetlsql 自动API封装dao工具
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class Blade {
	private static Map<Class<?>, Blade> pool = new ConcurrentHashMap<Class<?>, Blade>();
	private volatile SQLManager sql = null;
	private Class<?> modelClass;
	private String table;
	private String pk;
	private String dbName;

	private SQLManager getSqlManager() {
		if (null == sql) {
			synchronized (Blade.class) {
				if (null == sql) {
					DbName dbName = modelClass.getAnnotation(DbName.class);
					if (null == dbName){
						sql = dao();
						this.dbName = ConnectionPlugin.init().MASTER;
					} else {
						sql = dao(dbName.name());
						this.dbName = dbName.name();
					}
				}
			}
		}
		return sql;
	}
	
	/**
	 * 返回SQLManager(主库)
	 * @return
	 */
	public static SQLManager dao() {
		return dao(ConnectionPlugin.init().MASTER);
	}
	
	/**
	 * 返回SQLManager(其他数据源)
	 * @param name
	 * @return
	 */
	public static SQLManager dao(String name) {
		return ConnectionPlugin.init().getPool().get(name);
	}

	/**
	 * 返回针对实体封装的dao
	 * @param modelClass 实体类
	 * @return
	 */
	public static Blade create(Class<?> modelClass) {
		Blade blade = pool.get(modelClass);
		if (null == blade) {
			synchronized (Blade.class) {
				blade = pool.get(modelClass);
				if (null == blade) {
					DbName dbName = modelClass.getAnnotation(DbName.class);
					if (null == dbName){
						blade = new Blade(ConnectionPlugin.init().MASTER, modelClass);
					} else {
						blade = new Blade(dbName.name(), modelClass);
					}
					pool.put(modelClass, blade);
				}
			}
		}
		return blade;
	}

	private Blade() {
		
	}
	
	private Blade(String sourceName, Class<?> modelClass) {
		if(modelClass != Blade.class){
			setTable(modelClass);			
		}
		setSource(sourceName);
	}

	private void setTable(Class<?> modelClass) {
		this.modelClass = modelClass;
		Table Table = this.modelClass.getAnnotation(Table.class);
		if (null != Table) {
			this.table = Table.name();
		} else {
			throw new RuntimeException("未给 " + this.modelClass.getName() + " 绑定表名!");
		}
		BindID BindID = this.modelClass.getAnnotation(BindID.class);
		if (null != BindID) {
			this.pk = BindID.name();
		} else {
			throw new RuntimeException("未给 " + this.modelClass.getName() + " 绑定主键! ");
		}
	}
	
	private void setSource(String name) {
		if (null == sql) {
			synchronized (Blade.class) {
				if (null == sql) {
					sql = dao(name);
				}
			}
		}
		dbName = name;
	}
	
	/**
	 * 获取map
	 * @param columns		字段名
	 * @return
	 */
	public Map findOneColBy(String columns){
		List<Map> list = getSqlManager().execute(getSelectSql(columns) + getFromSql(), Map.class, Paras.create(), 1, 1);
		if(list.size() == 0){
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 获取map
	 * @param columns		字段名
	 * @param where			条件
	 * @param paras	实体类或map
	 * @return
	 */
	public Map findOneColBy(String columns, String where, Object paras){
		List<Map> list = getSqlManager().execute(getSelectSql(columns) + getFromSql() + getWhere(where), Map.class, paras, 1, 1);
		if (list.size() == 0){
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 获取map集合
	 * @param columns		字段名
	 * @param paras	实体类或map
	 * @return
	 */
	public List<Map> findColBy(String columns){
		List<Map> list = getSqlManager().execute(getSelectSql(columns) + getFromSql(), Map.class, Paras.create());
		return list;
	}
	
	/**
	 * 获取map集合
	 * @param columns		字段名
	 * @param where			条件
	 * @param paras	实体类或map
	 * @return
	 */
	public List<Map> findColBy(String columns, String where, Object paras){
		List<Map> list = getSqlManager().execute(getSelectSql(columns) + getFromSql() + getWhere(where), Map.class, paras);
		return list;
	}

	/**
	 * 根据主键查询一条数据
	 * @param id
	 * @return
	 */
	public <T> T findById(Object id) {
		try{
			return (T) getSqlManager().unique(this.modelClass, id);
		} catch (Exception ex){
			return null;
		}
	}

	/**
	 * 根据sql查询多条数据
	 * @param sqlTemplate sql语句
	 * @param paras  实体类或者Map(查询条件)
	 * @return
	 */
	public <T> List<T> find(String sqlTemplate, Object paras) {
		List<T> list = (List<T>) getSqlManager().execute(sqlTemplate, this.modelClass, paras);
		return list;
	}

	/**
	 * 查询第一条数据
	 * @param topNum 
	 * @param model
	 * @return
	 */
	public <T> T findTopOne(Object model) {
		List<T> list = (List<T>) getSqlManager().template(model, 1, 1);
		if(list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	/**
	 * 查询前几条数据
	 * @param topNum 
	 * @param model
	 * @return
	 */
	public <T> List<T> findTop(int topNum, Object model) {
		List<T> list = (List<T>) getSqlManager().template(model, 1, topNum);
		return list;
	}
	
	/**
	 * 查询前几条数据
	 * @param topNum
	 * @param sqlTemplate
	 * @return
	 */
	public <T> List<T> findTop(int topNum, String sqlTemplate) {
		List<T> list = (List<T>) getSqlManager().execute(sqlTemplate, this.modelClass, Paras.create(), 1, topNum);
		return list;
	}
	
	/**
	 * 查询前几条数据
	 * @param topNum
	 * @param sqlTemplate
	 * @param paras
	 * @return
	 */
	public <T> List<T> findTop(int topNum, String sqlTemplate, Object paras) {
		List<T> list = (List<T>) getSqlManager().execute(sqlTemplate, this.modelClass, paras, 1, topNum);
		return list;
	}

	/**
	 * 查询所有数据
	 * @return
	 */
	public <T> List<T> findAll() {
		List<T> all = (List<T>) getSqlManager().all(this.modelClass);
		return all;
	}

	/**
	 * 根据条件查询数据
	 * @param where			sql条件
	 * @param paras	实体类或者Map(查询条件)
	 * @return
	 */
	public <T> List<T> findBy(String where, Object paras) {
		List<T> models = (List<T>) getSqlManager().execute(getSelectSql() + getFromSql() + getWhere(where), this.modelClass, paras);
		return models;
	}

	/**
	 * 根据条件查询数据,指定返回字段
	 * @param columns		字段
	 * @param where			sql条件
	 * @param paras	实体类或者Map(查询条件)
	 * @return
	 */
	public <T> List<T> findBy(String columns, String where, Object paras) {
		List<T> models = (List<T>) getSqlManager().execute(getSelectSql(columns) + getFromSql() + getWhere(where), this.modelClass, paras);
		return models;
	}

	/**
	 * 根据实体类查询与之符合的数据
	 * @param model 实体类
	 * @return
	 */
	public <T> List<T> findByTemplate(Object model) {
		return (List<T>) getSqlManager().template(model);
	}

	/**
	 * 查询第一条数据
	 * @param sqlTemplate	sql语句
	 * @param paras	实体类或者Map(查询条件)
	 * @return
	 */
	public <T> T findFirst(String sqlTemplate, Object paras) {
		List<T> list = this.findTop(1, sqlTemplate, paras);
		if (list.size() == 0){
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 根据条件查询第一条数据
	 * @param where		 sql条件
	 * @param paras 实体类或者Map(查询条件)
	 * @return
	 */
	public <T> T findFirstBy(String where, Object paras) {
		List<T> list = this.findTop(1, getSelectSql() + getFromSql() + getWhere(where), paras);
		if (list.size() == 0){
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 新增一条数据
	 * @param model 实体类
	 * @return
	 */
	public boolean save(Object model) {
		return getSqlManager().insert(this.modelClass, model, false) > 0;
	}

	/**
	 * 新增一条数据,返回int型主键值
	 * @param model 实体类
	 * @return
	 */
	public int saveRtId(Object model) {
		KeyHolder key = new KeyHolder();
		int n = getSqlManager().insert(this.modelClass, model, key);
		if (n > 0) {
			return Integer.parseInt(key.getKey().toString());
		} else {
			return 0;
		}
	}

	/**
	 * 新增一条数据,返回String型主键值
	 * @param model
	 * @return
	 */
	public String saveRtStrId(Object model) {
		KeyHolder key = new KeyHolder();
		int n = getSqlManager().insert(this.modelClass, model, key);
		if (n > 0) {
			return key.getKey().toString();
		} else {
			return "";
		}
	}
	
	/**
	 * 新增一条数据,并自动将主键反射到字段中
	 * @param model
	 */
	public boolean saveAndSetKey(Object model){
		return getSqlManager().insert(this.modelClass, model, true) > 0;
	}

	/**
	 * 修改一条数据,只更新非空字段
	 * @param model 实体类
	 * @return
	 */
	public boolean update(Object model) {
		return baseUpdate(model, false);
	}

	/**
	 * 修改一条数据,为null的字段也更新
	 * @param model 实体类
	 * @return
	 */
	public boolean updateEveryCol(Object model) {
		return baseUpdate(model, true);
	}
	
	/**
	 * 修改一条数据
	 * @param model
	 * @param flag true代表更新null字段, false代表只更新非空字段
	 * @return
	 */
	private boolean baseUpdate(Object model, boolean flag) {
		SQLManager sql = getSqlManager();
		String table = sql.getNc().getTableName(this.modelClass);
		ClassDesc desc = sql.getMetaDataManager().getTable(table).getClassDesc(this.modelClass, sql.getNc());
		Method getterMethod = (Method) desc.getIdMethods().get(desc.getIdCols().get(0));
		Object idValue = null;
		try {
			idValue = getterMethod.invoke(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(Func.isEmpty(idValue)){
			throw new RuntimeException("未取到ID的值,无法修改!");
		}
		
		if(Cst.me().isOptimisticLock()){
			// 1.数据是否还存在
			String sqlExist = new StringBuffer("select * from ").append(table).append(" where ").append(pk).append(" = #{idValue} ").toString();
			Map modelOld = Db.init(dbName).selectOne(sqlExist, Paras.create().set("idValue", idValue));
			// 数据已经被删除
			if (null == modelOld) { 
				throw new RuntimeException("数据库中此数据不存在，可能数据已经被删除，请刷新数据后在操作");
			}
			// 2.乐观锁控制
			Paras modelForm = Paras.parse(model);
			if (modelForm.get(Const.OPTIMISTIC_LOCK.toLowerCase()) != null) { // 是否需要乐观锁控制
				int versionDB = Func.toInt(modelOld.get(Const.OPTIMISTIC_LOCK.toLowerCase())); // 数据库中的版本号
				int versionForm = Func.toInt(modelForm.get(Const.OPTIMISTIC_LOCK.toLowerCase())); // 表单中的版本号
				if (!(versionForm > versionDB)) {
					throw new RuntimeException("表单数据版本号和数据库数据版本号不一致，可能数据已经被其他人修改，请重新编辑");
				}
			}
		}
		if (flag) {
			return sql.updateById(model) > 0;
		} else {
			return sql.updateTemplateById(model) > 0;
		}
	}

	/**
	 * 根据实体类字段,修改所有表数据(慎用)
	 * @param model
	 * @return
	 */
	public int updateAllRecords(Object model) {
		return getSqlManager().updateAll(this.modelClass, model);
	}

	/**
	 * 更新条件修改数据
	 * @param set		 set条件
	 * @param where		 where条件
	 * @param paras 实体类或者Map(查询条件)
	 * @return
	 */
	public boolean updateBy(String set, String where, Object paras) {
		int n = getSqlManager().executeUpdate(getUpdateSql() + getSet(set) + getWhere(where), paras);
		return n > 0;
	}

	/**
	 * 根据实体类集合批量更新
	 * @param list 实体类集合
	 * @return
	 */
	public int[] updateBathById(List<?> list) {
		int[] n = getSqlManager().updateByIdBatch(list);
		return n;
	}

	/**
	 * 根据id删除数据
	 * @param id 主键值
	 * @return
	 */
	public int delete(Object id) {
		int cnt = getSqlManager().deleteById(this.modelClass, id);
		return cnt;
	}

	/**
	 * 根据sql语句删除数据
	 * @param sqlTemplate sql语句
	 * @return
	 */
	public int deleteBy(String sqlTemplate) {
		int result = getSqlManager().executeUpdate(sqlTemplate, null);
		return result;
	}

	/**
	 * 根据条件删除数据
	 * @param where where条件
	 * @param paras 实体类或者Map(查询条件)
	 * @return
	 */
	public int deleteBy(String where, Object paras) {
		int result = getSqlManager().executeUpdate(getDeleteSql(where), paras);
		return result;
	}

	/**
	 * 根据多个id集合删除数据
	 * @param ids id集合(1,2,3)
	 * @return
	 */
	public int deleteByIds(String ids) {
		String sqlTemplate = getDeleteSql(this.table, this.pk);
		Paras paras = Paras.create().set("ids", ids.split(","));
		int result = getSqlManager().executeUpdate(sqlTemplate, paras);
		return result;
	}

	/**
	 * 根据字段及值删除数据
	 * @param col	字段名
	 * @param ids	字段值集合(1,2,3)
	 * @return
	 */
	public int deleteByCols(String col, String ids) {
		String sqlTemplate = getDeleteSql(this.table, col);
		Paras paras = Paras.create().set("ids", ids.split(","));
		int result = getSqlManager().executeUpdate(sqlTemplate, paras);
		return result;
	}

	/**
	 * 根据表名、字段名、值删除数据
	 * @param table	表名
	 * @param col	字段名
	 * @param ids	字段值集合(1,2,3)
	 * @return
	 */
	public int deleteTableByCols(String table, String col, String ids) {
		String sqlTemplate = getDeleteSql(table, col);
		Paras paras = Paras.create().set("ids", ids.split(","));
		int result = getSqlManager().executeUpdate(sqlTemplate, paras);
		return result;
	}

	/**
	 * 查询总数
	 * @return
	 */
	public long total() {
		long n = getSqlManager().allCount(this.modelClass);
		return n;
	}

	/**
	 * 查询符合实体类数据的总数
	 * @param model
	 * @return
	 */
	public long count(Object model) {
		long total = getSqlManager().templateCount(model);
		return total;
	}

	/**
	 * 查询sql语句查询结果的总数
	 * @param sqlTemplate sql语句
	 * @param paras  实体类或者Map(查询条件)
	 * @return
	 */
	public int countBy(String sqlTemplate, Object paras) {
		int n = getSqlManager().execute(sqlTemplate, this.modelClass, paras).size();
		return n;
	}

	/**
	 * 根据where条件查询总数
	 * @param where		 where条件
	 * @param paras 实体类或者Map(查询条件)
	 * @return
	 */
	public int count(String where, Object paras) {
		int n = getSqlManager().execute(getCountSql() + getWhere(where), this.modelClass, paras).size();
		return n;
	}

	/**
	 * 获取list
	 * @param start	页号
	 * @param size	每页数量
	 * @return
	 */
	public <T> List<T> getList(int start, int size) {
		List<T> all = (List<T>) getSqlManager().all(this.modelClass, (start - 1) * size + 1, size);
		return all;
	}

	/**
	 * 获取list
	 * @param model 实体类
	 * @param start 页号
	 * @param size	数量
	 * @return
	 */
	public <T> List<T> getList(Object model, int start, int size) {
		List<T> all = (List<T>) getSqlManager().template(model, (start - 1) * size + 1, size);
		return all;
	}
	

	/**
	 * 获取list
	 * @param sqlTemplate sql语句
	 * @param paras	参数		  
	 * @param start	页号		
	 * @param size	数量
	 * @return
	 */
	public <T> List<T> getList(String sqlTemplate, Object paras, int start, int size) {
		List<T> all = (List<T>) getSqlManager().execute(sqlTemplate, this.modelClass, paras, (start - 1) * size + 1, size);
		return all;
	}
	

	/**
	 * 获取list
	 * @param sqlTemplate sql语句
	 * @param clazz	返回类型
	 * @param paras	参数
	 * @param start	页号
	 * @param size	数量
	 * @return
	 */
	public <T> List<T> getList(String sqlTemplate, Class<?> clazz, Object paras, int start, int size) {
		List<T> all = (List<T>) getSqlManager().execute(sqlTemplate, clazz, paras, (start - 1) * size + 1, size);
		return all;
	}
	
	/**
	 * 分页
	 * @param sqlTemplate sql语句
	 * @param paras	参数
	 * @param start	页号
	 * @param size	数量
	 * @return
	 */
	public <T> BladePage<T> paginate(String sqlTemplate, Object paras, int start, int size){
		List<T> rows = getList(sqlTemplate, paras, start, size);
		long count = Db.init(dbName).queryInt(getCountSql(sqlTemplate), paras).longValue();
		BladePage<T> page = new BladePage<>(rows, start, size, count);
		return page;
	}
	
	/**
	 * 分页
	 * @param sqlTemplate sql语句
	 * @param clazz	返回类型
	 * @param paras	参数
	 * @param start	页号
	 * @param size	数量
	 * @return
	 */
	public <T> BladePage<T> paginate(String sqlTemplate, Class<?> clazz, Object paras, int start, int size){
		List<T> rows = getList(sqlTemplate, clazz, paras, start, size);
		long count = Db.init(dbName).queryInt(getCountSql(sqlTemplate), paras).longValue();
		BladePage<T> page = new BladePage<>(rows, start, size, count);
		return page;
	}
	

	/**
	 * 是否存在
	 * 
	 * @param sqlTemplate
	 * @param paras
	 * @return
	 */
	public boolean isExist(String sqlTemplate, Object paras) {
		int count = getSqlManager().execute(sqlTemplate, this.modelClass, paras).size();
		if (count != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 将本类的字段打印到控制台
	 */
	public void createPojoToConsole() {
		try {
			getSqlManager().genPojoCodeToConsole(this.table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将指定表名的字段打印到控制台
	 * @param tableName
	 */
	public void createPojoToConsole(String tableName) {
		try {
			getSqlManager().genPojoCodeToConsole(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*************************************************************************************************/

	private String getSet(String set) {
		if (set != null && !set.isEmpty() && !set.trim().toUpperCase().startsWith("SET")) {
			set = " SET " + set + " ";
		}
		return set;
	}

	private String getWhere(String where) {
		if (where != null && !where.isEmpty() && !where.trim().toUpperCase().startsWith("WHERE")) {
			where = " WHERE " + where + " ";
		}
		return where;
	}

	private String getSelectSql() {
		return " SELECT * ";
	}

	private String getSelectSql(String columns) {
		return " SELECT " + columns + " ";
	}

	private String getFromSql() {
		return " FROM " + this.table + " ";
	}

	private String getUpdateSql() {
		return " UPDATE " + this.table + " ";
	}

	private String getDeleteSql(String where) {
		return " DELETE FROM " + this.table + " WHERE " + where + " ";
	}

	private String getDeleteSql(String table, String col) {
		return " DELETE FROM " + table + " WHERE " + col + " IN (#{join(ids)}) ";
	}

	private String getCountSql() {
		return " SELECT " + this.pk + " FROM " + this.table + " ";
	}
	
	private String getCountSql(String sqlTemplate) {
		return " SELECT COUNT(*) CNT FROM (" + sqlTemplate + ") a";
	}
}

