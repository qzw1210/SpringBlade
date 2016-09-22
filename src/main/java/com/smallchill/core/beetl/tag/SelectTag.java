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
package com.smallchill.core.beetl.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.beetl.core.Tag;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.interfaces.IQuery;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.ClassKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;

public class SelectTag extends Tag {

	@Override
	@SuppressWarnings("unchecked")
	public void render() {
		try {
			Map<String, String> param = (Map<String, String>) args[1];

			final String code = param.get("code");
			String name = param.get("name");
			int value = Func.toInt(param.get("value"));
			String token = (value > 0) ? "" : "token_";
			String type = param.get("type");
			String where = param.get("where");
			String required = param.get("required");
			String tail = param.get("tail");
			String inter = param.get("intercept");
			String sql = "";
			
			Map<String, Object> modelOrMap = Paras.createHashMap();
			
			IQuery intercept = Cst.me().getDefaultQueryFactory();
			
			String CACHE_NAME = ConstCache.DICT_CACHE;
			
			if (type.equals("dict")) {
				sql = "select num as ID,pId as PID,name as TEXT from  TFW_DICT where code=" + code + " and num>0";
				intercept = Cst.me().getDefaultSelectFactory().dictIntercept();
			} else if (type.equals("user")) {
				CACHE_NAME = ConstCache.USER_CACHE;
				sql = "select ID,name as TEXT from  TFW_USER where status=1";
				intercept = Cst.me().getDefaultSelectFactory().userIntercept();
			} else if (type.equals("dept")) {
				CACHE_NAME = ConstCache.DEPT_CACHE;
				sql = "select ID,PID,SIMPLENAME as TEXT from  TFW_DEPT";
				intercept = Cst.me().getDefaultSelectFactory().deptIntercept();
			} else if (type.equals("role")) {
				CACHE_NAME = ConstCache.ROLE_CACHE;
				sql = "select ID,name as TEXT from  TFW_ROLE";
				intercept = Cst.me().getDefaultSelectFactory().roleIntercept();
			} else if (type.equals("diy")) {
				CACHE_NAME = ConstCache.DIY_CACHE;
				type = type + "_" + param.get("source");
				if(StrKit.notBlank(where)){
					modelOrMap = JsonKit.parse(where, Map.class);
				}
				sql = Md.getSql(param.get("source"));
			}

			if(StrKit.notBlank(inter)) {
				intercept = ClassKit.newInstance(inter);
			}
			
			final String _sql = sql;
			final Map<String, Object> _modelOrMap = modelOrMap;
			final IQuery _intercept = intercept;
			
			List<Map<String, Object>> dict = CacheKit.get(CACHE_NAME, "dict_" + type + "_" + code + "_" + ShiroKit.getUser().getId(), new ILoader() {
				@Override
				public Object load() {
					return Db.selectList(_sql, _modelOrMap, new AopContext(), _intercept);
				}
			}); 

			StringBuilder sb = new StringBuilder();
			String sid = "_" + name.split("\\.")[1];
			sb.append("<select onchange=\"" +sid + "_selectChanged('" + sid + "')\" " + required + " class=\"form-control\" id=\"" + sid + "\"  name=\"" + token + name + "\">");
			sb.append("<option value></option>");
			
			for (Map<String, Object> dic : dict) {
				int id =  Func.toInt(dic.get("ID"));
				String selected = "";
				if (id == value) {
					selected = "selected";
				}
				sb.append("<option " + selected + " value=\"" + id + "\">" + dic.get("TEXT") + "</option>");
			}
			sb.append("</select>");
			
			
			sb.append("<script type=\"text/javascript\">");
			sb.append("		function " +sid + "_selectChanged(sid) {");
			sb.append("			$('#form_token').val(1);");
			sb.append("			$('#' + sid).attr('name','"+name+"');");
			if(StrKit.notBlank(tail)) {
				sb.append("			var options=$('#' + sid + ' option:selected');");
				sb.append("			$('#' + sid + '_ext').val(options.text());");
			}
			sb.append("		};");
			sb.append("</script>");
			if(StrKit.notBlank(tail)) {
				sb.append("<input type=\"hidden\" id=\"" + sid + "_ext\" name=\"" + name.split("\\.")[0] + "." + tail + "\">");
			}

			ctx.byteWriter.writeString(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
