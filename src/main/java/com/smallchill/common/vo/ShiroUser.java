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
package com.smallchill.common.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.StrKit;


public class ShiroUser implements Serializable {

	private static final long serialVersionUID = 6847303349754497231L;
	
	private Object id;// 主键
	private Object deptId;// 部门id
	private String deptName;// 部门名称
	private String loginName;// 账号
	private String name;// 姓名
	private List<String> roleList;// 角色集
	private String roles;// 角色集
	private Object subDepts;// 子部门集合
	private Object subRoles;// 子角色集合
	private Object subUsers;// 子账号集合

	public ShiroUser(Object id, Object deptId, String loginName, String name, List<String> roleList) {
		this.id = id;
		this.deptId = deptId;
		this.deptName = Func.getDeptName(deptId);
		this.loginName = loginName;
		this.name = name;
		this.roleList = roleList;
		this.roles = CollectionKit.join(roleList.toArray(), ",");
		
		// 递归查找子部门id集合
		String deptSql;
		String subDepts = null;
		if (Func.isOracle()) {
			deptSql = "select wm_concat(ID) subDepts from (select ID,PID,SIMPLENAME from TFW_DEPT start with ID in (#{join(deptIds)}) connect by prior ID=PID order by ID) a where a.ID not in (#{join(deptIds)})";
			subDepts = Db.queryStr(deptSql, Paras.create().set("deptIds", deptId.toString().split(",")));
		} else {
			String[] arr = deptId.toString().split(",");
			StringBuilder sb = new StringBuilder();
			for (String deptid : arr) {
				deptSql = "select queryChildren(#{deptid},'tfw_dept') as subdepts";
				String str = Db.queryStr(deptSql, Paras.create().set("deptid", deptid));
				sb.append(str).append(",");
			}
			subDepts = StrKit.removeSuffix(sb.toString(), ",");
		}
		this.subDepts = subDepts;
		
		// 递归查找子角色id集合
		String roleSql;
		String subRoles = null;
		if (Func.isOracle()) {
			roleSql = "select wm_concat(ID) subRoles from (select ID,PID,NAME from TFW_ROLE start with ID in (#{join(roleIds)}) connect by prior ID=PID order by ID) a where a.ID not in (#{join(roleIds)})";
			subRoles = Db.queryStr(roleSql, Paras.create().set("roleIds", roleList));
		} else {
			StringBuilder sb = new StringBuilder();
			for (String roleid : roleList) {
				roleSql = "SELECT queryChildren(#{deptid},'tfw_role') as subroles";
				String str = Db.queryStr(roleSql, Paras.create().set("deptid", roleid));
				sb.append(str).append(",");
			}
			subRoles = StrKit.removeSuffix(sb.toString(), ",");
		}
		this.subRoles = subRoles;
		
		// 查找子角色对应账号id集合
		List<Map<String, Object>> listUser = CacheKit.get(ConstCache.USER_CACHE, "user_all_list", new ILoader() {
			@Override
			public Object load() {
				return Db.selectList("SELECT * FROM TFW_USER");
			}
		});
		
		String[] subrolestr = Func.toStr(this.subRoles).split(",");
		StringBuilder sbUser = new StringBuilder();
		for (Map<String, Object> map : listUser) {
			for (String str : subrolestr) {
				if (Func.toStr(map.get("ROLEID")).indexOf(str) >= 0 && (("," + sbUser.toString() + ",").indexOf("," + Func.toStr(map.get("ID")) + ",") == -1)) {
					Func.builder(sbUser, Func.toStr(map.get("ID")) + ",");
				}
			}
		}
		
		this.subUsers = StrKit.removeSuffix(sbUser.toString(), ","); 
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getDeptId() {
		return deptId;
	}

	public void setDeptId(Object deptId) {
		this.deptId = deptId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Object getSubDepts() {
		return subDepts;
	}

	public void setSubDepts(Object subDepts) {
		this.subDepts = subDepts;
	}

	public Object getSubRoles() {
		return subRoles;
	}

	public void setSubRoles(Object subRoles) {
		this.subRoles = subRoles;
	}

	public Object getSubUsers() {
		return subUsers;
	}

	public void setSubUsers(Object subUsers) {
		this.subUsers = subUsers;
	}
}