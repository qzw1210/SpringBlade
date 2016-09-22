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
package com.smallchill.core.toolbox.log;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.common.vo.OperationLog;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.constant.Const;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.interfaces.ILog;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.kit.CacheKit;

/**
 * 系统默认日志记录
 * 
 */
public class BladeLogFactory implements ILog {

	public String[] logPatten() {
		String[] patten = { "login", "logout", "grant", "save", "update", "remove", "del", "delete", "restore" };
		return patten;
	}

	public Paras logMaps() {
		Paras rd = Paras.create()
				.set("login", "登录")
				.set("logout", "登出")
				.set("grant", "授权")
				.set("save", "新增")
				.set("update", "修改")
				.set("remove", "删除")
				.set("del", "删除")
				.set("delete", "删除")
				.set("restore", "还原");
		return rd;
	}

	public boolean isDoLog() {
		@SuppressWarnings("rawtypes")
		Map map = CacheKit.get(ConstCache.SYS_CACHE, "parameter_log", new ILoader() {
			@Override
			public Object load() {
				return Db.selectOne("select para from tfw_parameter where code = #{code}", Paras.create().set("code", Const.PARA_LOG_CODE));
			}
		}); 
		if(map.get("para").equals("1")){
			return true;
		}
		return false;
	}
	
	public boolean doLog(ShiroUser user, String msg, String logName, HttpServletRequest request, boolean succeed) {
		if (null == user) {
			return true;
		}
		try {
			OperationLog log = new OperationLog();
			log.setMethod(msg);
			log.setCreatetime(new Date());
			log.setSucceed((succeed)?"1":"0");
			log.setUserid(Func.toStr(user.getId()));
			log.setLogname(logName);
			boolean temp = Blade.create(OperationLog.class).save(log);
			return temp;
		} catch (Exception ex) {
			return false;
		}
	}

}
