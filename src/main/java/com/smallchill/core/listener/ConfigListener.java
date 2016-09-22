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
package com.smallchill.core.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.smallchill.core.constant.Const;
import com.smallchill.core.toolbox.kit.PropKit;

public class ConfigListener implements ServletContextListener {

	public static final Map<String, String> map = new HashMap<String, String>();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		map.clear();
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		ServletContext sc = evt.getServletContext();
		// 项目路径
		map.put("realPath", sc.getRealPath("/").replaceFirst("/", ""));
		map.put("contextPath", sc.getContextPath());

		Properties prop = PropKit.use(Const.PropertyFile).getProperties();
		for (Object name : prop.keySet()) {
			map.put(name.toString(), prop.get(name).toString());
		}
	}

}
