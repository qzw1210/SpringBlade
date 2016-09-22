package com.smallchill.system.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beetl.sql.core.db.TableDesc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smallchill.core.base.controller.CurdController;
import com.smallchill.core.beetl.BeetlMaker;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.interfaces.IMeta;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.meta.factory.GenerateFactory;
import com.smallchill.system.model.Generate;

@Controller
@RequestMapping("/generate")
public class GenerateController extends CurdController<Generate> {

	@Override
	protected Class<? extends IMeta> metaFactoryClass() {
		return GenerateFactory.class;
	}
	
	@ResponseBody
	@RequestMapping("/pojo/{table}")
	public String createPojo(@PathVariable String table) {
		try {
			Blade.dao().genPojoCodeToConsole(table);
			return "[ " + table + " ] pojo生成成功,请查看控制台";
		} catch (Exception e) {
			return "[ " + table + " ] pojo生成失败:" + e.getMessage();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pojo/{slave}/{table}")
	public String createPojoSlave(@PathVariable String slave, @PathVariable String table) {
		try {
			Blade.dao(slave).genPojoCodeToConsole(table);
			return "[ " + table + " ] pojo生成成功,请查看控制台";
		} catch (Exception e) {
			return "[ " + table + " ] pojo生成失败:" + e.getMessage();
		}
	}
	
	@ResponseBody
	@RequestMapping("/sql/{table}")
	public String createSql(@PathVariable String table) {
		try {
			Blade.dao().genSQLTemplateToConsole(table);
			return "[ " + table + " ] sql生成成功,请查看控制台";
		} catch (Exception e) {
			return "[ " + table + " ] sql生成失败:" + e.getMessage();
		}
	}
	
	@ResponseBody
	@RequestMapping("/sql/{slave}/{table}")
	public String createSqlSlave(@PathVariable String slave, @PathVariable String table) {
		try {
			Blade.dao(slave).genSQLTemplateToConsole(table);
			return "[ " + table + " ] sql生成成功,请查看控制台";
		} catch (Exception e) {
			return "[ " + table + " ] sql生成失败:" + e.getMessage();
		}
	}
	
	@ResponseBody
	@RequestMapping("/code")
	public AjaxResult gencode(){
		String ids = getParameter("ids");
		List<Generate> list = Blade.create(Generate.class).findBy("id in (#{join(ids)})", Paras.create().set("ids", ids.split(",")));

		for (Generate gen : list) {
			boolean flag = false;
			
			String realPath = gen.getRealpath() + File.separator + "src" + File.separator + "main";
			String packageName = gen.getPackagename();
			String modelName = gen.getModelname();
			String upperModelName = StrKit.firstCharToUpperCase(modelName);
			String lowerModelName = StrKit.firstCharToLowerCase(modelName);
			
			String tableName = gen.getTablename();
			String pkName = gen.getPkname();
			String path = realPath + File.separator + "java" + File.separator + packageName.replace(StrKit.DOT, File.separator);
			String resourcesPath = realPath + File.separator + "resources";
			String webappPath = realPath + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "view";
			
			//java
			String controllerPath = path + File.separator + "controller" + File.separator + upperModelName + "Controller.java";
			String modelPath = path + File.separator + "model" + File.separator + upperModelName + ".java";
			String servicePath = path + File.separator + "service" + File.separator + upperModelName + "Service.java";
			String serviceimplPath = path + File.separator + "service" + File.separator + "impl" + File.separator + upperModelName + "ServiceImpl.java";
			
			//resources
			String sqlPath = resourcesPath + File.separator + "beetlsql" + File.separator + upperModelName + ".md";
			
			//webapp
			String indexPath = webappPath + File.separator + "gen" + File.separator + lowerModelName + File.separator + lowerModelName + ".html";
			String addPath = webappPath + File.separator + "gen" + File.separator + lowerModelName + File.separator + lowerModelName + "_add.html";
			String editPath = webappPath + File.separator + "gen" + File.separator + lowerModelName + File.separator + lowerModelName + "_edit.html";
			String viewPath = webappPath + File.separator + "gen" + File.separator + lowerModelName + File.separator + lowerModelName + "_view.html";
			
			Map<String, String> pathMap = new HashMap<>();
			pathMap.put("controllerPath", controllerPath);
			pathMap.put("modelPath", modelPath);
			pathMap.put("servicePath", servicePath);
			pathMap.put("serviceimplPath", serviceimplPath);
			pathMap.put("sqlPath", sqlPath);
			pathMap.put("indexPath", indexPath);
			pathMap.put("addPath", addPath);
			pathMap.put("editPath", editPath);
			pathMap.put("viewPath", viewPath);
			
			for (Map.Entry<String, String> entry : pathMap.entrySet()) {  
				File file = new File(entry.getValue());
				if (file.exists()) {
					flag = true;
					break;
				} else {
					file.getParentFile().mkdirs();
				}
			}
			
			if (flag) {
				continue;
			}
			
			//java
			String baseTemplatePath = Cst.me().getRealPath() + "WEB-INF"+ File.separator + "view" + File.separator + "common" + File.separator + "_template" + File.separator;
			String controllerTemplatePath = baseTemplatePath + "_controller" + File.separator + "_controller.bld";
			String modelTemplatePath = baseTemplatePath + "_model" + File.separator +  "_model.bld";
			String serviceTemplatePath = baseTemplatePath + "_service" + File.separator + "_service.bld";
			String serviceimplTemplatePath = baseTemplatePath + "_service" + File.separator + "_impl" + File.separator + "_serviceimpl.bld";
			
			//resources
			String sqlTemplatePath = baseTemplatePath + "_sql" + File.separator + "_sql.bld";
			
			//webapp
			String indexTemplatePath = baseTemplatePath + "_view" + File.separator + "_index.bld";
			String addTemplatePath = baseTemplatePath + "_view" + File.separator + "_add.bld";
			String editTemplatePath = baseTemplatePath + "_view" + File.separator + "_edit.bld";
			String viewTemplatePath = baseTemplatePath + "_view" + File.separator + "_view.bld";
			
			Paras rd = Paras.create();
			rd.set("realPath", realPath);
			rd.set("packageName", packageName);
			rd.set("modelName", upperModelName);
			rd.set("lowerModelName", lowerModelName);
			rd.set("tableName", tableName);
			rd.set("pkName", pkName);
			
			//java
			BeetlMaker.makeHtml(controllerTemplatePath, rd, controllerPath);
			BeetlMaker.makeHtml(modelTemplatePath, rd, modelPath);
			BeetlMaker.makeHtml(serviceTemplatePath, rd, servicePath);
			BeetlMaker.makeHtml(serviceimplTemplatePath, rd, serviceimplPath);
			
			//resources
			BeetlMaker.makeHtml(sqlTemplatePath, rd, sqlPath);
			
			//webapp
			final TableDesc tableDesc = Blade.dao().getMetaDataManager().getTable(tableName);
			Set<String> cols = tableDesc.getIdNames();
			rd.set("cols", cols);
			
			BeetlMaker.makeHtml(indexTemplatePath, rd, indexPath);
			BeetlMaker.makeHtml(addTemplatePath, rd, addPath);
			BeetlMaker.makeHtml(editTemplatePath, rd, editPath);
			BeetlMaker.makeHtml(viewTemplatePath, rd, viewPath);
			
		}
		
		return success("生成成功,已经存在的文件将会跳过!");
	}
}
