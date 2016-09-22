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

import javax.servlet.http.HttpServletRequest;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.toolbox.Paras;

/**
 *  日志记录接口
 */
public interface ILog {
	
	/**
	 * 定义日志拦截的方法
	 */
	String[] logPatten();
	
	/**
	 * 定义日志拦截的方法名
	 */
	Paras logMaps();
	
	/**
	 * 是否需要记录日志
	 */
	boolean isDoLog();
	
	/**   
	 * 日志记录
	 * @param user 当前用户
	 * @param msg  返回消息
	 * @param request
	 * @return boolean
	*/
	boolean doLog(ShiroUser user, String msg, String logName, HttpServletRequest request, boolean succeed);
}
