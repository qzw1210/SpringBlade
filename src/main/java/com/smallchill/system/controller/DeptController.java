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
package com.smallchill.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.meta.intercept.DeptIntercept;
import com.smallchill.system.model.Dept;

@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController{
	private static String LIST_SOURCE = "dept.list";
	private static String BASE_PATH = "/system/dept/";
	private static String CODE = "dept";
	private static String PERFIX = "tfw_dept";
	
	@RequestMapping("/")
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "dept.html";
	}
	
	
	@ResponseBody
	@RequestMapping(KEY_LIST)
	public Object list() {
		Object gird = paginate(LIST_SOURCE, new DeptIntercept());
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "dept_add.html";
	}
	
	@RequestMapping(KEY_ADD + "/{id}")
	public String add(@PathVariable String id, ModelMap mm) {
		if (StrKit.notBlank(id)) {
			mm.put("pId", id);
			mm.put("num", findLastNum(id));
		}
		mm.put("code", CODE);
		return BASE_PATH + "dept_add.html";
	}
	
	@RequestMapping(KEY_EDIT + "/{id}")
	public String edit(@PathVariable String id, ModelMap mm) {
		Dept Dept = Blade.create(Dept.class).findById(id);
		mm.put("model", JsonKit.toJson(Dept));
		mm.put("code", CODE);
		return BASE_PATH + "dept_edit.html";
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	public String view(@PathVariable String id, ModelMap mm) {
		Blade blade = Blade.create(Dept.class);
		Dept Dept = blade.findById(id);
		Dept parent = blade.findById(Dept.getPid());
		String pName = (null == parent) ? "" : parent.getSimplename();
		Paras rd = Paras.parse(Dept);
		rd.set("pName", pName);
		mm.put("model", JsonKit.toJson(rd));
		mm.put("code", CODE);
		return BASE_PATH + "dept_view.html";
	}
	
	@ResponseBody
	@RequestMapping(KEY_SAVE)
	public AjaxResult save() {
		Dept dept = mapping(PERFIX, Dept.class);
		boolean temp = Blade.create(Dept.class).save(dept);
		if (temp) {
			CacheKit.removeAll(DEPT_CACHE);
			return success("新增成功");
		} else {
			return error("新增失败");
		}
	}

	@ResponseBody
	@RequestMapping(KEY_UPDATE)
	public AjaxResult update() {
		Dept dept = mapping(PERFIX, Dept.class);
		dept.setVersion(getParameterToInt("VERSION", 0) + 1);
		boolean temp =  Blade.create(Dept.class).update(dept);
		if (temp) {
			CacheKit.removeAll(DEPT_CACHE);
			return success("修改成功");
		} else {
			return error("修改失败");
		}
	}

	@ResponseBody
	@RequestMapping(KEY_REMOVE)
	public AjaxResult remove() {
		int cnt = Blade.create(Dept.class).deleteByIds(getParameter("ids"));
		if (cnt > 0) {
			CacheKit.removeAll(DEPT_CACHE);
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	
	
	
	
	
	
	private int findLastNum(String id){
		try{
			Blade blade = Blade.create(Dept.class);
			Dept dept = blade.findFirstBy("pId = #{pId} order by num desc", Paras.create().set("pId", id));
			return dept.getNum() + 1;
		}
		catch(Exception ex){
			return 1;
		}
	}
	
	
}
