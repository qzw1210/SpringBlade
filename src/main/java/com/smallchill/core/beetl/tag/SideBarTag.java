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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beetl.core.Tag;

import com.smallchill.common.vo.TreeNode;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.kit.CacheKit;

public class SideBarTag extends Tag {
	
	private List<TreeNode> nodeList = new ArrayList<TreeNode>();
	
	@Override
	@SuppressWarnings("unchecked")
	public void render() {
		try {
			String MENU_CACHE = ConstCache.MENU_CACHE;
			Map<String, String> param = (Map<String, String>) args[1];

			final Object userId = param.get("userId");
			final Object roleId = param.get("roleId");
			String ctxPath =Cst.me().getContextPath();

			Map<String, Object> userRole = CacheKit.get(MENU_CACHE, "role_ext_" + userId, new ILoader() {
				@Override
				public Object load() {
					return Db.selectOne("select * from TFW_ROLE_EXT where USERID=#{userId}", Paras.create().set("userId", userId));
				}
			}); 


			String roleIn = "0";
			String roleOut = "0";
			if (!Func.isEmpty(userRole)) {
				Paras rd = Paras.parse(userRole);
				roleIn = rd.getStr("ROLEIN");
				roleOut = rd.getStr("ROLEOUT");
			}
			final StringBuilder sql = new StringBuilder();
			sql.append("select * from TFW_MENU  ");
			sql.append(" where ( ");
			sql.append("	 (status=1)");
			sql.append("	 and (icon is not null and icon not LIKE '%btn%' and icon not LIKE '%icon%') ");
			sql.append("	 and (id in (select menuId from TFW_RELATION where roleId in (" + roleId + ")) or id in (" + roleIn + "))");
			sql.append("	 and id not in(" + roleOut + ")");
			sql.append("	)");
			sql.append(" order by levels,pCode,num");

			List<Map<String, Object>> sideBar = CacheKit.get(MENU_CACHE, "sideBar_" + userId, new ILoader() {
				@Override
				public Object load() {
					return Db.selectList(sql.toString());
				}
			}); 
			
			for (Map<String, Object> side : sideBar) {
				TreeNode node = new TreeNode();
				Paras rd = Paras.parse(side);
				node.setId(rd.getStr("CODE"));
				node.setParentId(rd.getStr("PCODE"));
				node.setName(rd.getStr("NAME"));
				node.setIcon(rd.getStr("ICON"));
				node.setIsParent(false);
				nodeList.add(node);
			}

			new TreeNode().buildNodes(nodeList);

			StringBuilder sb = new StringBuilder();

			for (Map<String, Object> side : sideBar) {
				if (Func.toInt(side.get("LEVELS")) == 1) {
					String firstMenu = "";
					String subMenu = "";
					String href = Func.isEmpty(side.get("URL")) ? "#" : ctxPath + side.get("URL") + "";
					String addtabs = Func.isEmpty(side.get("URL")) ? "" : "data-addtabs=\"" + side.get("CODE") + "\"";

					firstMenu += "<li >";
					firstMenu += "	<a data-url=\"" + href + "\" " + addtabs + " data-title=\"" + side.get("NAME") + "\" data-icon=\"fa " + side.get("ICON") + "\" class=\"" + getDropDownClass(Func.toStr(side.get("CODE")),"dropdown-toggle") + " tmsp-pointer\">";
					firstMenu += "		<i class=\"menu-icon fa " + side.get("ICON") + "\"></i>";
					firstMenu += "		<span class=\"menu-text\">" + side.get("NAME") + "</span>";
					firstMenu += "		<b class=\"arrow " + getDropDownClass(Func.toStr(side.get("CODE")),"fa fa-angle-down") + "\"></b>";
					firstMenu += "	</a>";
					firstMenu += "	<b class=\"arrow\"></b>";

					subMenu = this.reloadMenu(sideBar, Func.toStr(side.get("CODE")), firstMenu, 1, ctxPath);// 寻找子菜单

					sb.append(subMenu);
				}
			}
			
			//版权校验
			sb.append("<script type=\"text/javascript\">");
			sb.append(" $(function(){");
			sb.append("  setTimeout(function(){");
			sb.append("  var $supporter = $(\"#support_tonbusoft\");");
			sb.append("  $supporter.addClass('bigger-120');");
			sb.append("  var name = $supporter.html();");
			sb.append("  var index = layer;");
			sb.append("  if(index == undefined){");
			sb.append("    alert(\"该产品版权归 smallchill@163.com 所有，请勿盗版！\");");
			sb.append("    return;");
			sb.append("  }");
			sb.append("  if(name == undefined){");
			sb.append("    layer.alert(\"该产品版权归 smallchill@163.com 所有，请勿盗版！\", {icon: 2,title:\"侵权警告\"});");
			sb.append("    return;");
			sb.append("  } else if(!(name.indexOf(\"smallchill@163.com\") >= 0 && $supporter.is(\"span\") && !$supporter.is(\":hidden\"))){");
			sb.append("    layer.alert(\"该产品版权归 smallchill@163.com 所有，请勿盗版！\", {icon: 2,title:\"侵权警告\"});");
			sb.append("    return;");
			sb.append("  }");
			sb.append("  }, 1800);");
			sb.append(" });");
			sb.append("</script>");
			
			ctx.byteWriter.writeString(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载子菜单
	 * 
	 * @param sideBar
	 *            菜单集合
	 * @param pCode
	 *            父编号
	 * @param menuCode
	 *            选中菜单编号
	 * @param pStr
	 *            父HTML
	 * @param levels
	 *            层级
	 * @return String 返回子菜单HTML集
	 */
	public String reloadMenu(List<Map<String, Object>> sideBar, String pCode, String pStr, int levels, String ctxPath) {
		String Str = "";
		String subStr = "";
		for (Map<String, Object> subside : sideBar) {
			Paras rd = Paras.parse(subside);
			int _levels = rd.getInt("LEVELS");
			String _code = rd.getStr("CODE");
			String _pCode = rd.getStr("PCODE");
			String _url = rd.getStr("URL");
			String _icon = rd.getStr("ICON");
			String _name = rd.getStr("NAME");
			if ((_pCode.equals(pCode) && _levels > levels)) {
				String href = Func.isEmpty(_url) ? "#" : ctxPath + _url + "";
				String addtabs = Func.isEmpty(_url) ? "" : "data-addtabs=\"" + _code + "\"";

				Str += "<li>";
				Str += "	<a data-url=\"" + href + "\" " + addtabs + " data-title=\"" + _name + "\" data-icon=\"fa " + _icon + "\" class=\"" + getDropDownClass(_code, "dropdown-toggle") + " tmsp-pointer\">";
				Str += "		<i class=\"menu-icon fa " + _icon + "\"></i>";
				Str += _name;
				Str += "		<b class=\"arrow " + getDropDownClass(_code,"fa fa-angle-down") + "\"></b>";
				Str += "	</a>";
				Str += "	<b class=\"arrow\"></b>";

				subStr = this.reloadMenu(sideBar, _code, Str, _levels, ctxPath);// 递归寻找子菜单

				Str = Func.isEmpty(subStr) ? Str : subStr;
			}

		}
		if (Str.length() > 0) {
			pStr += (Func.isEmpty(pStr)) ? Str : "<ul class=\"submenu\">" + Str + "</ul>";
			pStr += "</li>";
			return pStr;
		} else {
			return "";
		}

	}
	
	public String getDropDownClass(String code,String dropdownclass){
		Iterator<TreeNode> it = nodeList.iterator();
		while (it.hasNext()) {
			TreeNode n = (TreeNode) it.next();
			if(n.getId().equals(code)&&n.isParent()){
				return dropdownclass;
			}
		}
		return "";
	}

}
