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
import java.util.List;
import java.util.Map;

import org.beetl.core.Tag;

import com.smallchill.common.vo.TreeNode;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.plugins.dao.Md;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.CacheKit;

public class DropDownTag extends Tag {
	
	@Override
	@SuppressWarnings("unchecked")
	public void render() {
		try {
			Map<String, String> param = (Map<String, String>) args[1];
			List<TreeNode> nodeList = new ArrayList<TreeNode>();
			String codes = param.get("code");
			String name = param.get("name");
			Object value = param.get("value");
			String token = (value.toString().equals("0")) ? "token_" : "";
			String type = param.get("type");
			String required = param.get("required");
			int lev = 99;
			String code = "";
			String sql = "";
			if (type.equals("dict")) {
			    code = codes.split("_")[0];
			    lev = (codes.split("_").length > 1) ? Func.toInt(codes.split("_")[1]) : 99;
				sql = "select ID,(case when PID is null then 0 else PID end) PID,NUM,name as TEXT from  TMSP_DICT where code=" + code + " and num>0";
			} else if (type.equals("user")) {
				sql = "select ID,ID NUM,0 as PID,name as TEXT from  TMSP_USER where status=1";
			} else if (type.equals("dept")) {
				sql = "select ID,(case when PID is null then 0 else PID end) PID,NUM,SIMPLENAME as TEXT from  TMSP_DEPT WHERE num>0";
			} else if (type.equals("role")) {
				sql = "select ID,(case when PID is null then 0 else PID end) PID,NUM,name as TEXT from  TMSP_ROLE";
			} else if (type.equals("diy")) {
				type = type + "_" + param.get("source");
				sql = Md.getSql(param.get("source")); //.getNamespaceSql(param.get("source"));
			}
 
			final String sqlstr = sql;
			
			List<Map<String, Object>> dict = CacheKit.get(ConstCache.DICT_CACHE,"dropdown_"+ type + "_" + code, new ILoader() {
				@Override
				public Object load() {
					return Db.selectList(sqlstr);
				}
			});

			for (Map<String, Object> d : dict) {
				TreeNode node = new TreeNode();
				node.setId(d.get("ID").toString());
				node.setParentId(d.get("PID").toString());
				node.setNum(d.get("NUM").toString());
				node.setName(d.get("TEXT").toString());
				node.setIsParent(false);
				node.setHasParent(false);
				nodeList.add(node);
			}
			
		    new TreeNode().buildNodes(nodeList);
			
			String sid = "_" + name.split("\\.")[1];

			ctx.byteWriter.writeString(getDropDownHtml(nodeList, sid, name, value, lev, token, required));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getDropDownHtml(List<TreeNode> nodeList, String id, String name, Object value, int lev, String token, String required){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"input-group-btn\">");
		sb.append(" <input type=\"hidden\" id=\"" + id + "\" " + required + " name=\"" + token + name + "\" value=\"" + value + "\"/>");
		sb.append(" <button data-toggle=\"dropdown\" class=\"btn btn-sx btn-white dropdown-toggle\">");
		sb.append("	 <span id=\"_DROPDOWN" + id + "\">" + findNameByNum(nodeList, value) + "</span> ");
		sb.append("  <i class=\"ace-icon fa fa-angle-down icon-on-right\"></i>");
		sb.append(" </button>");
		sb.append(" <ul class=\"dropdown-menu\">");
		sb.append("  <li><a href=\"#\" tabindex=\"-1\" onclick=\"$('#_DROPDOWN" + id + "').html('请选择');$('#" + id + "').val('');$('#form_token').val(1);$('#" + id + "').attr('name','"+name+"');\">清空</a></li>");
		sb.append("  <li class=\"divider\" style=\"margin-top:1px;\"></li>");
		for (TreeNode treeNode : nodeList) {
			sb.append(reloadMenu(treeNode, 0, id, name, lev));
		}
		sb.append(" </ul>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private String reloadMenu(TreeNode treeNode, int levels, String id, String name, int lev){
		StringBuilder sm = new StringBuilder();		
		int parentSize = (Func.isEmpty(treeNode.getParent())) ? 0 : treeNode.getParent().size();
		if(treeNode.isParent() && parentSize == levels && lev > levels + 1){
			List<TreeNode> linkedList = treeNode.getChildren();	
			sm.append("<li class=\"dropdown-hover\">");
			sm.append(" <a href=\"#\"  tabindex=\"-1\" class=\"clearfix\">");
			sm.append("  <span class=\"pull-left\" >" + treeNode.getName() + "</span>");
			sm.append("  <i class=\"ace-icon fa fa-caret-right pull-right\"></i>");
			sm.append(" </a>");
			sm.append(" <ul class=\"dropdown-menu\">");
			for (TreeNode subNode : linkedList) { 
				sm.append(reloadMenu(subNode, levels + 1, id, name, lev));
			}
			sm.append(" </ul>");
			sm.append("</li>");
		} else if(treeNode.isParent() && parentSize == levels && lev == levels + 1){
			sm.append("<li><a href=\"#\" tabindex=\"-1\" onclick=\"$('#_DROPDOWN" + id + "').html('" + treeNode.getName() + "');$('#" + id + "').val('" + treeNode.getNum() + "');$('#form_token').val(1);$('#" + id + "').attr('name','"+name+"');\">" + treeNode.getName() + "</a></li>");
		} else if(parentSize > 0){
			if(parentSize == levels && lev >= levels + 1){
				sm.append("<li><a href=\"#\" tabindex=\"-1\" onclick=\"$('#_DROPDOWN" + id + "').html('" + treeNode.getName() + "');$('#" + id + "').val('" + treeNode.getNum() + "');$('#form_token').val(1);$('#" + id + "').attr('name','"+name+"');\">" + treeNode.getName() + "</a></li>");
			}
		} else {
			sm.append("<li><a href=\"#\" tabindex=\"-1\" onclick=\"$('#_DROPDOWN" + id + "').html('" + treeNode.getName() + "');$('#" + id + "').val('" + treeNode.getNum() + "');$('#form_token').val(1);$('#" + id + "').attr('name','"+name+"');\">" + treeNode.getName() + "</a></li>");
		}
		return sm.toString();
	}
	
	private String findNameByNum(List<TreeNode> nodeList, Object num){
		for (TreeNode treeNode : nodeList) {
			if(treeNode.getNum().equals(num.toString())){
				return treeNode.getName();
			}
		}
		return "请选择";
	}
	

}
