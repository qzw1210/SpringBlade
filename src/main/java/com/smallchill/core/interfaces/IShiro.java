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
package com.smallchill.core.interfaces;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.SimpleAuthenticationInfo;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.common.vo.User;

/**
 * 定义shirorealm所需数据的接口
 *
 */
public interface IShiro {
	User user(String account);

	ShiroUser shiroUser(User user);

	@SuppressWarnings("rawtypes")
	List<Map> findPermissionsByRoleId(Object userId, String roleId);

	String findRoleNameByRoleId(String roleId);
	
	SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName);
}
