package com.smallchill.core.meta;

import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.base.model.BaseModel;

public class MetaTool {
	/**
	 * 获取javabean对应的表名
	 * 
	 * @param clazz
	 *            javabean.class
	 * @return String
	 */
	public String getTableName(Class<? extends BaseModel> clazz) {
		return clazz.getAnnotation(Table.class).name();
	}
}
