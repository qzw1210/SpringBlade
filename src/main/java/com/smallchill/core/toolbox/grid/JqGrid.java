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
package com.smallchill.core.toolbox.grid;

import java.util.List;

/**
 * @title JqGrid封装bean
 * @author zhuangqian
 * @email smallchill@163.com
 * @date 2016-1-18下午4:07:57
 * @copyright 2016
 */
public class JqGrid<E> {
	/** 返回结果集  **/
	private List<E> rows;

	/** 返回的总页数  **/
	private long total;

	/** 返回的当前页数  **/
	private long page;

	/** 一共有多少数据  **/
	private long records;

	public JqGrid() {

	}
	
	public JqGrid(List<E> rows, long total, long page, long records) {
		super();
		this.rows = rows;
		this.total = total;
		this.page = page;
		this.records = records;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

}
