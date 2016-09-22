package com.smallchill.core.constant;


public interface Const {

	/**
	 * 当前版本号
	 */
	final static String FRAMEWORK_VERSION = "1.0";

	/**
	 * 配置文件
	 */
	final static String PropertyFile = "config.properties";

	/**
	 * 字符编码
	 */
	final static String Encoding = "UTF-8";

	/**
	 * view层根目录
	 */
	final static String baseViewPath = "/WEB-INF/view/";
	/**
	 * 登陆地址(接口)
	 */
	final static String loginPath = "/login/";
	/**
	 * 登陆地址(路径)
	 */
	final static String loginRealPath = "/login.html";
	/**
	 * 首页面地址(路径)
	 */
	final static String indexRealPath = "/index.html";
	/**
	 * 400页面地址
	 */
	final static String error400Path = "/error/400.html";
	/**
	 * 401页面地址
	 */
	final static String error401Path = "/error/401.html";
	/**
	 * 404页面地址
	 */
	final static String error404Path = "/error/404.html";
	/**
	 * 403页面地址
	 */
	final static String error403Path = "/error/403.html";
	/**
	 * 500页面地址
	 */
	final static String error500Path = "/error/500.html";
	/**
	 * 无权限地址
	 */
	final static String noPermissionPath = "/error/permission.html";
	/**
	 * 下载地址
	 */
	final static String downloadPath = "/download";

	/**
	 * 定义用户sessionkey
	 */
	final static String USER_SESSION_KEY = "user";

	/**
	 * 定义日志参数
	 */
	final static String PARA_LOG_CODE = "101";

	/**
	 * 定义乐观锁字段
	 */
	final static String OPTIMISTIC_LOCK = "VERSION";

	/**
	 * 定义mybatis分页插件的排序字段
	 */
	final static String ORDER_BY_STR = "orderBy";

	/**
	 * 定义mybatis条件封装的值
	 */
	final static String SQL_EX_STR = "sqlEx";
	
	/**
	 * 定义跳转常量
	 */
	final static String redirect = "redirect:";

}
