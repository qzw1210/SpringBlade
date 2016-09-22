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
package com.smallchill.core.toolbox.grid;

import java.util.HashMap;
import java.util.Map;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.constant.Const;
import com.smallchill.core.interfaces.IGrid;
import com.smallchill.core.interfaces.IQuery;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.support.SqlKeyword;

/**
 * @title grid工厂基类,封装通用分页方法
 * @author zhuangqian
 * @email smallchill@163.com
 * @date 2016-1-23上午9:41:48
 * @copyright 2016
 */
public abstract class BaseGridFactory implements IGrid{

	/**
	 * 封装grid返回数据类型
	 * 
	 * @param slaveName
	 *            数据库别名
	 * @param page
	 *            当前页号
	 * @param rows
	 *            每页的数量
	 * @param sql
	 *            数据源
	 * @param para
	 *            额外条件 {"id":1,"title":"test"}
	 * @param sort
	 *            排序列名 (id)
	 * @param order
	 *            排序方式 (desc)
	 * @param intercept
	 *            业务拦截器
	 * @param ctrl
	 *            控制器
	 * @return Object
	 */
	protected Object basePaginate(String slaveName, Integer page, Integer rows, String source, String para, String sort, String order, IQuery intercept, BladeController ctrl) {
		if (source.toLowerCase().indexOf("select") == -1) {
			return paginateById(slaveName, page, rows, source, para, sort, order, intercept, ctrl);
		} else {
			return paginateBySql(slaveName, page, rows, source, para, sort, order, intercept, ctrl);
		}
	}
	
	private Object paginateById(String slaveName, Integer page, Integer rows, String sqlId, String para, String sort, String order, IQuery intercept, BladeController ctrl) {	
		String sqlTemplate = Md.getSql(sqlId);
		return paginateBySql(slaveName, page, rows, sqlTemplate, para, sort, order, intercept, ctrl);
	}

	private Object paginateBySql(String slaveName, Integer page, Integer rows, String sqlTemplate, String para, String sort, String order, IQuery intercept, BladeController ctrl) {
		String sqlex = SqlKeyword.getWhere(para);
		Map<String, Object> map = getSqlMap(para, sort, order);	
		String statement = "select * from (" + sqlTemplate + ") blade_statement";
		
		// 查询前拦截
		AopContext ac = null;
		if (null != intercept) {
			ac = new AopContext(ctrl);
			ac.setSql(statement + sqlex);
			ac.setCondition("");
			ac.setParam(map);
			intercept.queryBefore(ac);
			statement = statement + (StrKit.isBlank(ac.getWhere()) ? (sqlex + ac.getCondition()) : ac.getWhere());
		} else {
			statement = statement + sqlex;
		}

		Object list = null;
		String orderBy = (Func.isEmpty(map.get(Const.ORDER_BY_STR))) ? " " : (" order by " + Func.toStr(map.get(Const.ORDER_BY_STR)));
		if(StrKit.notBlank(slaveName)){
			list = Db.init(slaveName).paginate(statement + orderBy, Map.class, map, page, rows);			
		} else {
			list = Db.paginate(statement + orderBy, Map.class, map, page, rows);
		}

		// 查询后拦截
		if (null != intercept) {
			ac.setObject(list);
			intercept.queryAfter(ac);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Object> getSqlMap(String para, String sort, String order){
		Map<String, Object> map = JsonKit.parse(Func.isEmpty(Func.decodeUrl(para)) ? null : Func.decodeUrl(para), HashMap.class);
		if (Func.isEmpty(map)) {
			map = new HashMap<>();
		}
		map.put(Const.ORDER_BY_STR, Func.isAllEmpty(sort, order) ? "" : (sort + " " + order));
		return map;
	}
}
