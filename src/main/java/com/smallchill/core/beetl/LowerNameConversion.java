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

import org.beetl.sql.core.NameConversion;
import org.beetl.sql.core.annotatoin.Table;

/**
 * @title 适用于oracle字段全部小写的NameConversion
 * @author zhuangqian
 * @email smallchill@163.com
 * @date 2015-12-25下午2:20:14
 * @copyright 2015
 */
public class LowerNameConversion extends NameConversion {

	@Override
	public String getTableName(Class<?> c) {
		Table table = (Table)c.getAnnotation(Table.class);
		if(table!=null){
			return table.name();
		}
		return c.getSimpleName().toLowerCase();
	}

	@Override
	public String getColName(Class<?> c, String attrName) {
		return attrName.toLowerCase();
	}

	@Override
	public String getPropertyName(Class<?> c, String colName) {
		return colName.toLowerCase();
	}

}
