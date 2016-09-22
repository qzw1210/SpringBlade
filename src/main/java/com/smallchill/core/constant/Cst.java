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
package com.smallchill.core.constant;

import com.smallchill.core.intercept.CURDInterceptor;
import com.smallchill.core.intercept.QueryInterceptor;
import com.smallchill.core.intercept.SelectInterceptor;
import com.smallchill.core.interfaces.ICURD;
import com.smallchill.core.interfaces.ICheck;
import com.smallchill.core.interfaces.IGrid;
import com.smallchill.core.interfaces.ILog;
import com.smallchill.core.interfaces.IQuery;
import com.smallchill.core.interfaces.ISelect;
import com.smallchill.core.interfaces.IShiro;
import com.smallchill.core.listener.ConfigListener;
import com.smallchill.core.shiro.DefaultShiroFactroy;
import com.smallchill.core.toolbox.check.PermissionCheckFactory;
import com.smallchill.core.toolbox.file.DefaultFileProxyFactory;
import com.smallchill.core.toolbox.file.IFileProxy;
import com.smallchill.core.toolbox.grid.JqGridFactory;
import com.smallchill.core.toolbox.log.BladeLogFactory;

public class Cst {

	/**
	 * 开发模式
	 */
	private boolean devMode = false;

	/**
	 * 远程上传模式
	 */
	private boolean remoteMode = false;

	/**
	 * 全文索引开启
	 */
	private boolean luceneIndex = false;

	/**
	 * 上传下载路径(物理路径)
	 */
	private String remotePath = "D://blade";

	/**
	 * 上传路径(相对路径)
	 */
	private String uploadPath = "/upload";

	/**
	 * 下载路径
	 */
	private String downloadPath = "/download";

	/**
	 * 项目物理路径
	 */
	private String realPath = ConfigListener.map.get("realPath");

	/**
	 * 项目相对路径
	 */
	private String contextPath = ConfigListener.map.get("contextPath");

	/**
	 * 密码允许错误次数
	 */
	private int passErrorCount = 6;

	/**
	 * 密码锁定小时数
	 */
	private int passErrorHour = 6;
	
	/**
	 * 是否启用乐观锁
	 */
	private boolean optimisticLock = true;

	/**
	 * 默认grid分页工厂类
	 */
	private IGrid defaultGridFactory = new JqGridFactory();

	/**
	 * 默认日志处理工厂类
	 */
	private ILog defaultLogFactory = new BladeLogFactory();

	/**
	 * 默认自定义权限检查工厂类
	 */
	private ICheck defaultCheckFactory = new PermissionCheckFactory();

	/**
	 * 默认shirorealm工厂类
	 */
	private IShiro defaultShiroFactory = new DefaultShiroFactroy();
	
	/**
	 * 默认文件上传转换工厂类
	 */
	private IFileProxy defaultFileProxyFactory = new DefaultFileProxyFactory();
	
	/**
	 * 默认CURD工厂类
	 */
	private ICURD defaultCURDFactory = new CURDInterceptor();
	
	/**
	 * 默认分页工厂类
	 */
	private IQuery defaultPageFactory = new QueryInterceptor();
	
	/**
	 * 默认查询工厂类
	 */
	private IQuery defaultQueryFactory = new QueryInterceptor();
	
	/**
	 * 默认select查询工厂类
	 */
	private ISelect defaultSelectFactory = new SelectInterceptor();

	private static final Cst me = new Cst();

	private Cst() {

	}

	public static Cst me() {
		return me;
	}

	public boolean isDevMode() {
		return devMode;
	}

	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}

	public boolean isRemoteMode() {
		return remoteMode;
	}

	public void setRemoteMode(boolean remoteMode) {
		this.remoteMode = remoteMode;
	}

	public boolean isLuceneIndex() {
		return luceneIndex;
	}

	public void setLuceneIndex(boolean luceneIndex) {
		this.luceneIndex = luceneIndex;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public int getPassErrorCount() {
		return passErrorCount;
	}

	public void setPassErrorCount(int passErrorCount) {
		this.passErrorCount = passErrorCount;
	}

	public int getPassErrorHour() {
		return passErrorHour;
	}

	public void setPassErrorHour(int passErrorHour) {
		this.passErrorHour = passErrorHour;
	}

	public String getUploadRealPath() {
		return (remoteMode ? remotePath : realPath) + uploadPath;
	}

	public String getUploadCtxPath() {
		return contextPath + uploadPath;
	}

	public String getRealPath() {
		return realPath;
	}

	public String getContextPath() {
		return contextPath;
	}

	public boolean isOptimisticLock() {
		return optimisticLock;
	}

	public void setOptimisticLock(boolean optimisticLock) {
		this.optimisticLock = optimisticLock;
	}

	public IGrid getDefaultGridFactory() {
		return defaultGridFactory;
	}

	public void setDefaultGridFactory(IGrid defaultGridFactory) {
		this.defaultGridFactory = defaultGridFactory;
	}
	
	public ILog getDefaultLogFactory() {
		return defaultLogFactory;
	}

	public void setDefaultLogFactory(ILog defaultLogFactory) {
		this.defaultLogFactory = defaultLogFactory;
	}

	public ICheck getDefaultCheckFactory() {
		return defaultCheckFactory;
	}

	public void setDefaultCheckFactory(ICheck defaultCheckFactory) {
		this.defaultCheckFactory = defaultCheckFactory;
	}

	public IShiro getDefaultShiroFactory() {
		return defaultShiroFactory;
	}

	public void setDefaultShiroFactory(IShiro defaultShiroFactory) {
		this.defaultShiroFactory = defaultShiroFactory;
	}

	public IFileProxy getDefaultFileProxyFactory() {
		return defaultFileProxyFactory;
	}

	public void setDefaultFileProxyFactory(IFileProxy defaultFileProxyFactory) {
		this.defaultFileProxyFactory = defaultFileProxyFactory;
	}

	public ICURD getDefaultCURDFactory() {
		return defaultCURDFactory;
	}

	public void setDefaultCURDFactory(ICURD defaultCURDFactory) {
		this.defaultCURDFactory = defaultCURDFactory;
	}

	public IQuery getDefaultPageFactory() {
		return defaultPageFactory;
	}

	public void setDefaultPageFactory(IQuery defaultPageFactory) {
		this.defaultPageFactory = defaultPageFactory;
	}

	public IQuery getDefaultQueryFactory() {
		return defaultQueryFactory;
	}

	public void setDefaultQueryFactory(IQuery defaultQueryFactory) {
		this.defaultQueryFactory = defaultQueryFactory;
	}

	public ISelect getDefaultSelectFactory() {
		return defaultSelectFactory;
	}

	public void setDefaultSelectFactory(ISelect defaultSelectFactory) {
		this.defaultSelectFactory = defaultSelectFactory;
	}
	
	
}
