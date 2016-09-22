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

import java.math.BigDecimal;
import java.util.List;

import org.beetl.sql.core.SQLResult;
import org.beetl.sql.core.db.KeyHolder;

import com.smallchill.core.toolbox.support.BladePage;

/**
 * beetlsql  MarkDown模式sql管理工具
 */
public class Md {

	private static volatile MdManager mdManager = null;

	public static MdManager init(String name) {
		return MdManager.init(name);
	}

	private Md() {}
	
	private static MdManager getMdManager() {
		if (null == mdManager) {
			synchronized (Md.class) {
				mdManager = MdManager.init();
			}
		}
		return mdManager;
	}
	
	
	/************   ↓↓↓   ********    mapper    *********   ↓↓↓   **************/
	
	/**
	 * 获取Mapper
	 * @param mapperInterface mapper接口类
	 * @return
	 */
	public static <T> T getMapper(Class<T> mapperInterface){
		return getMdManager().getMapper(mapperInterface);
	}
	
	
	/************   ↓↓↓   ********     通用     *********   ↓↓↓   ****************/
	
	/**
	 * 获取一条数据
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @param clazz 返回类型
	 * @return
	 */
	public static <T> T selectOne(String sqlId, Object paras, Class<T> clazz){
		return getMdManager().selectOne(sqlId, paras, clazz);
	}
	
	/**
	 * 获取一条数据
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @param clazz 返回类型
	 * @return
	 */
	public static <T> T selectUnique(String sqlId, Object paras, Class<T> clazz){
		return getMdManager().selectUnique(sqlId, paras, clazz);
	}
	
	/**
	 * 获取多条数据
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @param clazz 返回类型
	 * @return
	 */
	public static <T> List<T> selectList(String sqlId, Object paras, Class<T> clazz){
		return getMdManager().selectList(sqlId, paras, clazz);
	}
	
	/**
	 * 获取str
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @return
	 */
	public static String queryStr(String sqlId, Object paras){
		return getMdManager().queryStr(sqlId, paras);
	}

	/**
	 * 获取integer
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @return
	 */
	public static Integer queryInt(String sqlId, Object paras){
		return getMdManager().queryInt(sqlId, paras);
	}
	
	/**
	 * 获取long
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @return
	 */
	public static Long queryLong(String sqlId, Object paras){
		return getMdManager().queryLong(sqlId, paras);
	}
	
	/**
	 * 获取decimal
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @return
	 */
	public static BigDecimal queryDecimal(String sqlId, Object paras){
		return getMdManager().queryDecimal(sqlId, paras);
	}
	
	/**
	 * 分页
	 * @param sqlId sqlId
	 * @param clazz	返回类型
	 * @param paras	参数
	 * @param pageNum	页号
	 * @param pageSize	数量
	 * @return
	 */
	public static <T> BladePage<T> paginate(String sqlId, Class<T> clazz, Object paras, int pageNum, int pageSize){
		return getMdManager().paginate(sqlId, clazz, paras, pageNum, pageSize);
	}
	
	
	/**
	 * 分页
	 * @param sqlId sqlId
	 * @param clazz	返回类型
	 * @param paras	参数
	 * @param pageNum	页号
	 * @param pageSize	数量
	 * @param orderBy	排序
	 * @return
	 */
	public static <T> BladePage<T> paginate(String sqlId, Class<T> clazz, Object paras, int pageNum, int pageSize, String orderBy){
		return getMdManager().paginate(sqlId, clazz, paras, pageNum, pageSize, orderBy);
	}
	
	/**
	 * 新增
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @return
	 */
	public static int insert(String sqlId, Object paras){
		return getMdManager().insert(sqlId, paras);
	}
	
	/**
	 * 新增
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @param keyName 主键名
	 * @return
	 */
	public static KeyHolder insert(String sqlId, Object paras, String keyName) {
		return getMdManager().insert(sqlId, paras, keyName);
	}
	
	/**
	 * 新增
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @param holder 主键holder
	 * @param keyName 主键名
	 * @return
	 */
	public static int insert(String sqlId, Object paras, KeyHolder holder, String keyName) {
		return getMdManager().insert(sqlId, paras, holder, keyName);
	}
	
	/**修改
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @return
	 */
	public static int update(String sqlId, Object paras){
		return getMdManager().update(sqlId, paras);
	}
	
	/**批量修改
	 * @param sqlId sqlId
	 * @param list 参数
	 * @return
	 */
	public static int[] updateBatch(String sqlId, List<?> list){
		return getMdManager().updateBatch(sqlId, list);
	}
	
	/**删除
	 * @param sqlId sqlId
	 * @param paras 参数
	 * @return
	 */
	public static int delete(String sqlId, Object paras){
		return getMdManager().delete(sqlId, paras);
	}
	
	/**
	 * 根据sqlId获取beetlsql的sql语句
	 * @param sqlId
	 * @return
	 */
	public static String getSql(String sqlId) {
		return getMdManager().getSql(sqlId);
	}
	
	/**
	 * 根据sqlId获取beetlsql的sql语句
	 * @param sqlId
	 * @param paras
	 * @return
	 */
	public static SQLResult getSQLResult(String sqlId, Object paras){
		return getMdManager().getSQLResult(sqlId, paras);
	}
	

}
