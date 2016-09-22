package com.smallchill.test.example.dao;

import java.util.List;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.platform.model.Notice;

/**
 * javabean模块
 *
 */
public class ExampleBlade extends BladeController{
	
	public void one(){
		Blade dao = Blade.create(Notice.class);
		//根据主键值查询一条数据
		Notice notice = dao.findById(getParameter("id"));
		System.out.println(notice);
	}
	
	public void more(){
		Blade dao = Blade.create(Notice.class);
		//指定表名、查询条件查询多条数据
		List<Notice> list = dao.findBy("tb_tfw_tzgg", "f_it_xl = #{xl},f_it_cjr = #{cjr}", Paras.create().set("xl", 1).set("cjr", 1));
		System.out.println(list);
	}
	
	public void other(){
		Blade dao = Blade.create(Notice.class);
		//指定表名、查询条件查询多条数据(完整sql)
		List<Notice> list = dao.find("select * from tb_tfw_tzgg where f_it_xl = #{xl} and f_it_cjr = #{cjr}", Paras.create().set("xl", 1).set("cjr", 1));
		System.out.println(list);
	}
	
	public void save(){
		Blade dao = Blade.create(Notice.class);
		//自动映射前缀为notice.xx类型的数据
		//uri : /notice/save?notice.f_vc_bt=123&notice.f_dt_cjsj=2016-02-02&notice.f_it_cjr=1
		Notice notice = mapping("notice", Notice.class);
		//根据model新增
		int id = dao.saveRtId(notice);
		//新增成功后自动返回id
		System.out.println(id);
	}
	
	public void update(){
		Blade dao = Blade.create(Notice.class);
		//自动映射前缀为notice.xx类型的数据
		Notice notice = mapping("notice", Notice.class);
		//根据model修改
		dao.update(notice);
	}
	
	public void remove(){
		Blade dao = Blade.create(Notice.class);
		//指定表名、字段名根据传入的集合删除多条数据
		dao.deleteByIds("1,2,3,4,5");
	}
	
	public void count(){
		Blade dao = Blade.create(Notice.class);
		Notice notice = new Notice();
		notice.setF_vc_bt("this is title");
		//查询出tb_tfw_tzgg表所有f_vc_bt字段值为"this is title"的总数
		dao.count(notice);
	}
	
	public void countBy(){
		Blade dao = Blade.create(Notice.class);
		//查询出tb_tfw_tzgg表所有f_vc_bt字段值为"this is title"并且f_it_cjr字段为1的总数
		dao.count("f_vc_bt = #{bt} and f_it_cjr = #{cjr}", Paras.create().set("bt", "this is title").set("cjr", 1));
	}
	
}
