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
package com.smallchill.core.beetl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import com.smallchill.core.beetl.func.BeetlExt;
import com.smallchill.core.beetl.func.ShiroExt;
import com.smallchill.core.beetl.tag.DropDownTag;
import com.smallchill.core.beetl.tag.FootTag;
import com.smallchill.core.beetl.tag.HotBlogsTag;
import com.smallchill.core.beetl.tag.SelectTag;
import com.smallchill.core.beetl.tag.SideBarTag;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.toolbox.Paras;

/**
 * @title Beetl模板绑值
 * @author zhuangqian
 * @email smallchill@163.com
 * @date 2015-11-17上午9:02:38
 * @copyright 2015
 */
public class BeetlTemplate {
	private static GroupTemplate gt;
	
	public static GroupTemplate getGt() {
		return gt;
	}

	static {
		if (gt == null) {
			StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
			Configuration cfg = null;
			try {
				cfg = Configuration.defaultConfiguration();
			} catch (IOException e) {
				e.printStackTrace();
			}
			gt = new GroupTemplate(resourceLoader, cfg);
			registerTemplate(gt);
		}
	}

	public static void registerTemplate(GroupTemplate groupTemplate){
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		sharedVars.put("startTime", new Date());
		sharedVars.put("basePath", ConstConfig.basePath);
		groupTemplate.setSharedVars(sharedVars);

		groupTemplate.registerTag("hot", HotBlogsTag.class);
		groupTemplate.registerTag("select", SelectTag.class);
		groupTemplate.registerTag("sidebar", SideBarTag.class);
		groupTemplate.registerTag("dropdown", DropDownTag.class);
		groupTemplate.registerTag("foot", FootTag.class);

		groupTemplate.registerFunctionPackage("func", new BeetlExt());
		groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
	}
	
	public static String build(String str, Map<String, Object> paras) {
		Template t = gt.getTemplate(str);
		if (null == paras) {
			paras = Paras.create();
		}
		paras.put("ctxPath", Cst.me().getContextPath());
		for (String o : paras.keySet()) {
			t.binding(o, paras.get(o));
		}
		return t.render();
	}

	public static void buildTo(String str, Map<String, Object> paras, PrintWriter pw) {
		Template t = gt.getTemplate(str);
		if (null == paras) {
			paras = Paras.create();
		}
		paras.put("ctxPath", Cst.me().getContextPath());
		for (String o : paras.keySet()) {
			t.binding(o, paras.get(o));
		}
		t.renderTo(pw);
	}
}
