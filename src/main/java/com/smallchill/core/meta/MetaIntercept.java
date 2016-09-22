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
package com.smallchill.core.meta;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstCurd;
import com.smallchill.core.interfaces.ICURD;
import com.smallchill.core.interfaces.IQuery;
import com.smallchill.core.interfaces.IRender;
import com.smallchill.core.toolbox.ajax.AjaxResult;

/**
 * @title 业务curd拦截器<br>
 *        可以在增删改查前后进行操作<br>
 *        (适用于后端校验、多表操作等)<br>
 *        已自带事务回滚机制,无需自行设置<br>
 *        如果出错直接抛异常即可回滚<br>
 * @author zhuangqian
 * @email smallchill@163.com
 * @date 2016-1-22下午1:54:21
 * @copyright 2016
 */
public class MetaIntercept extends MetaTool implements IQuery, IRender, ICURD{

	protected AjaxResult result = new AjaxResult();

	/**
	 * 查询前操作
	 * 
	 * @param ac
	 */
	public void queryBefore(AopContext ac) {

	}

	/**
	 * 查询后操作
	 * 
	 * @param ac
	 */
	public void queryAfter(AopContext ac) {

	}

	/**
	 * 列表转向前操作
	 * 
	 * @param ac
	 */
	public void renderIndexBefore(AopContext ac) {

	}

	/**
	 * 新增转向前操作
	 * 
	 * @param ac
	 */
	public void renderAddBefore(AopContext ac) {

	}

	/**
	 * 修改转向前操作
	 * 
	 * @param ac
	 */
	public void renderEditBefore(AopContext ac) {

	}

	/**
	 * 查看转向前操作
	 * 
	 * @param ac
	 */
	public void renderViewBefore(AopContext ac) {

	}

	/**
	 * 主表新增前操作
	 * 
	 * @param ac
	 */
	public void saveBefore(AopContext ac) {

	}

	/**
	 * 主表新增后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean saveAfter(AopContext ac) {
		return true;
	}

	/**
	 * 新增全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	public AjaxResult saveSucceed(AopContext ac) {
		return result.addSuccess(ConstCurd.SAVE_SUCCESS_MSG);
	}

	/**
	 * 主表修改前操作
	 * 
	 * @param ac
	 */
	public void updateBefore(AopContext ac) {

	}

	/**
	 * 主表修改后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean updateAfter(AopContext ac) {
		return true;
	}

	/**
	 * 修改全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	public AjaxResult updateSucceed(AopContext ac) {
		return result.addSuccess(ConstCurd.UPDATE_SUCCESS_MSG);
	}

	/**
	 * 物理删除前操作
	 * 
	 * @param ac
	 */
	public void removeBefore(AopContext ac) {

	}

	/**
	 * 物理删除后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean removeAfter(AopContext ac) {
		return true;
	}

	/**
	 * 物理删除全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	public AjaxResult removeSucceed(AopContext ac) {
		return result.addSuccess(ConstCurd.DEL_SUCCESS_MSG);
	}
	
	/**
	 * 逻辑删除前操作
	 * 
	 * @param ac
	 */
	public void delBefore(AopContext ac) {

	}

	/**
	 * 逻辑删除后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean delAfter(AopContext ac) {
		return true;
	}
	
	/**
	 * 逻辑删除后操作(事务外)
	 * 
	 * @param ac
	 */
	public AjaxResult delSucceed(AopContext ac) {
		return result.addSuccess(ConstCurd.DEL_SUCCESS_MSG);
	}
	
	/**
	 * 主表还原前操作
	 * 
	 * @param ac
	 */
	public void restoreBefore(AopContext ac) {

	}

	/**
	 * 主表还原后操作(事务内)
	 * 
	 * @param ac
	 */
	public boolean restoreAfter(AopContext ac) {
		return true;
	}
	
	/**
	 * 还原全部完毕后操作(事务外)
	 * 
	 * @param ac
	 */
	public AjaxResult restoreSucceed(AopContext ac) {
		return result.addSuccess(ConstCurd.RESTORE_SUCCESS_MSG);
	}
	
}
