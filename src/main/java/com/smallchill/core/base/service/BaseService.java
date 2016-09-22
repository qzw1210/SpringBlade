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
import com.smallchill.core.constant.ConstCurd;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.interfaces.ICURD;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.kit.ClassKit;
import com.smallchill.core.toolbox.support.BladePage;

@SuppressWarnings({ "unchecked" })
public class BaseService<M> implements IService<M>, ConstCurd {
	
	private Class<M> modelClass;
	private Blade dao;

	private void setModelClass(Class<M> modelClass) {
		this.modelClass = modelClass;
	}

	public BaseService() {
		this.setModelClass(ClassKit.getSuperClassGenricFirstType(this.getClass()));
	}

	private Blade getSqlMananger() {
		if(null == dao){
			synchronized (BaseService.class) {
				if(null == dao){
					dao = Blade.create(modelClass);
				}
			}
		}
		return dao;
	}

	public M findById(Object id) {
		Blade dao = getSqlMananger();
		return dao.findById(id);
	}

	public List<M> find(String sqlTemplate, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.find(sqlTemplate, modelOrMap);
	}

	public List<M> findTop(int topNum, M model) {
		Blade dao = getSqlMananger();
		return dao.findTop(topNum, model);
	}
	
	public List<M> findTop(int topNum, String sqlTemplate) {
		Blade dao = getSqlMananger();
		return dao.findTop(topNum, sqlTemplate);
	}
	
	public List<M> findTop(int topNum, String sqlTemplate, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.findTop(topNum, sqlTemplate, modelOrMap);
	}

	public List<M> findAll() {
		Blade dao = getSqlMananger();
		return dao.findAll();
	}

	public List<M> findBy(String where, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.findBy(where, modelOrMap);
	}

	public List<M> findBy(String columns, String where, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.findBy(columns, where, modelOrMap);
	}

	public List<M> findByTemplate(M model) {
		Blade dao = getSqlMananger();
		return dao.findByTemplate(model);
	}

	public M findFirst(String sqlTemplate, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.findFirst(sqlTemplate, modelOrMap);
	}

	public M findFirstBy(String where, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.findFirstBy(where, modelOrMap);
	}

	public boolean save(M model) {
		Blade dao = getSqlMananger();
		return dao.save(model);
	}

	public int saveRtId(M model) {
		Blade dao = getSqlMananger();
		return dao.saveRtId(model);
	}

	public String saveRtStrId(M model) {
		Blade dao = getSqlMananger();
		return dao.saveRtStrId(model);
	}

	public boolean saveAndSetKey(M model){
		Blade dao = getSqlMananger();
		return dao.saveAndSetKey(model);
	}
	
	public boolean update(M model) {
		Blade dao = getSqlMananger();
		return dao.update(model);
	}

	public boolean updateEveryCol(M model) {
		Blade dao = getSqlMananger();
		return dao.updateEveryCol(model);
	}

	public int updateAllRecords(M model) {
		Blade dao = getSqlMananger();
		return dao.updateAllRecords(model);
	}

	public boolean updateBy(String set, String where, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.updateBy(set, where, modelOrMap);
	}

	public int[] updateBathById(List<M> list) {
		Blade dao = getSqlMananger();
		return dao.updateBathById(list);
	}

	public int delete(Object id) {
		Blade dao = getSqlMananger();
		return dao.delete(id);
	}

	public int deleteByIds(String ids) {
		Blade dao = getSqlMananger();
		return dao.deleteByIds(ids);
	}

	public int deleteByCols(String col, String ids) {
		Blade dao = getSqlMananger();
		return dao.deleteByCols(col, ids);
	}

	public int deleteTableByCols(String table, String col, String ids) {
		Blade dao = getSqlMananger();
		return dao.deleteTableByCols(table, col, ids);
	}

	public int deleteBy(String sqlTemplate) {
		Blade dao = getSqlMananger();
		return dao.deleteBy(sqlTemplate);
	}

	public int deleteBy(String where, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.deleteBy(where, modelOrMap);
	}

	public long total() {
		Blade dao = getSqlMananger();
		return dao.total();
	}

	public long count(M model) {
		Blade dao = getSqlMananger();
		return dao.count(model);
	}

	public int count(String where, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.count(where, modelOrMap);
	}

	public int countBy(String sqlTemplate, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.countBy(sqlTemplate, modelOrMap);
	}

	public List<M> getList(int start, int size) {
		Blade dao = getSqlMananger();
		return dao.getList(start, size);
	}

	public List<M> getList(M model, int start, int size) {
		Blade dao = getSqlMananger();
		return dao.getList(model, start, size);
	}

	public List<M> getList(String sqlTemplate, Object modelOrMap, int start, int size) {
		Blade dao = getSqlMananger();
		return dao.getList(sqlTemplate, modelOrMap, start, size);
	}

	public BladePage<M> paginate(String sqlTemplate, Object paras, int start, int size) {
		Blade dao = getSqlMananger();
		return dao.paginate(sqlTemplate, paras, start, size);
	}

	public boolean isExist(String sqlTemplate, Object modelOrMap) {
		Blade dao = getSqlMananger();
		return dao.isExist(sqlTemplate, modelOrMap);
	}

	public void createPojoToConsole() {
		Blade dao = getSqlMananger();
		dao.createPojoToConsole();
	}

	public boolean save(M model, AopContext ac) {
		return save(model, ac, Cst.me().getDefaultCURDFactory());
	}

	public boolean update(M model, AopContext ac) {
		return update(model, ac, Cst.me().getDefaultCURDFactory());
	}

	public boolean removeByIds(String ids, AopContext ac) {
		return removeByIds(ids, ac, Cst.me().getDefaultCURDFactory());
	}

	public boolean delByIds(String ids, AopContext ac) {
		return delByIds(ids, ac, Cst.me().getDefaultCURDFactory());
	}

	public boolean restoreByIds(String ids, AopContext ac) {
		return restoreByIds(ids, ac, Cst.me().getDefaultCURDFactory());
	}
	
	public boolean save(M model, AopContext ac, ICURD intercept) {
		if (null != intercept) {
			ac.setObject(model);
			intercept.saveBefore(ac);
		}
		String rtid = this.saveRtStrId(model);
		boolean tempAfter = true;
		if (null != intercept && rtid.length() > 0) {
			ac.setId(rtid);
			tempAfter = intercept.saveAfter(ac);
		}
		return (rtid.length() > 0 && tempAfter);
	}

	public boolean update(M model, AopContext ac, ICURD intercept) {
		if (null != intercept) {
			ac.setObject(model);
			intercept.updateBefore(ac);
		}
		boolean temp = this.update(model);
		boolean tempAfter = true;
		if (null != intercept && temp) {
			tempAfter = intercept.updateAfter(ac);
		}
		return (temp && tempAfter);
	}

	public boolean removeByIds(String ids, AopContext ac, ICURD intercept) {
		if (null != intercept) {
			ac.setId(ids);
			intercept.removeBefore(ac);
		}
		int n = this.deleteByIds(ids);
		boolean tempAfter = true;
		if (null != intercept && n > 0) {
			tempAfter = intercept.removeAfter(ac);
		}
		return (n > 0 && tempAfter);
	}

	public boolean delByIds(String ids, AopContext ac, ICURD intercept) {
		if (null != intercept) {
			ac.setId(ids);
			intercept.delBefore(ac);
		}
		boolean temp = this.updateBy(ac.getSql(), ac.getWhere(), ac.getParam());
		boolean tempAfter = true;
		if (null != intercept && temp) {
			tempAfter = intercept.delAfter(ac);
		}
		return (temp && tempAfter);
	}

	public boolean restoreByIds(String ids, AopContext ac, ICURD intercept) {
		if (null != intercept) {
			ac.setId(ids);
			intercept.restoreBefore(ac);
		}
		boolean temp = this.updateBy(ac.getSql(), ac.getWhere(), ac.getParam());
		boolean tempAfter = true;
		if (null != intercept && temp) {
			tempAfter = intercept.restoreAfter(ac);
		}
		return (temp && tempAfter);
	}


}
