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

import java.util.Map;

import com.smallchill.core.meta.MetaIntercept;

/**
 *  定义CURD元数据接口
 */
public interface IMeta {
	
	/**
	 * 对应的业务拦截器
	 * 
	 * @return String
	 */
	Class<? extends MetaIntercept> intercept();
	
	/**
	 * 对应的controllerKey
	 * 
	 * @return String
	 */
	String controllerKey();
	
	/**
	 * 前端字段的表名前缀
	 * 
	 * @return String
	 */
	String paraPerfix();

	/**
	 * 前端字段混淆<br>
	 * map.put("前端字段","数据库字段");
	 * 
	 * @return Map<String,Object>
	 */
	Map<String, Object> switchMap();

	/**
	 * 增改查页面转向<br>
	 * Map<String, String> renderMap = new HashMap<>();<br>
	 * renderMap.put(ConstCurd.KEY_INDEX, "/demo/demo.html");<br>
	 * renderMap.put(ConstCurd.KEY_ADD, "/demo/demo_add.html");<br>
	 * renderMap.put(ConstCurd.KEY_EDIT, "/demo/demo_edit.html");<br>
	 * renderMap.put(ConstCurd.KEY_VIEW, "/demo/demo_view.html");<br>
	 * 
	 * @return Map<String,String>
	 */
	Map<String, String> renderMap();

	/**
	 * 列表页的数据源<br>
	 * Map<String, String> sourceMap = new HashMap<>();<br>
	 * sourceMap.put(ConstCurd.KEY_INDEX,"DemoMapper.list");<br>
	 * sourceMap.put(ConstCurd.KEY_EDIT, "DemoMapper.edit");<br>
	 * sourceMap.put(ConstCurd.KEY_VIEW, "DemoMapper.view");<br>
	 * 
	 * @return Map<String,String>
	 */
	Map<String, String> sourceMap();

}
