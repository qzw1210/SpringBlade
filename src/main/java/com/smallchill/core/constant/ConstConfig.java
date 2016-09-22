package com.smallchill.core.constant;

import com.smallchill.core.listener.ConfigListener;

public interface ConstConfig {

	final static String dbType = ConfigListener.map.get("master.dbType");
	final static String driver = ConfigListener.map.get("master.driver");
	final static String url = ConfigListener.map.get("master.url");
	final static String username = ConfigListener.map.get("master.username");
	final static String password = ConfigListener.map.get("master.password");
	final static String initialSize = ConfigListener.map.get("druid.initialSize");
	final static String maxActive = ConfigListener.map.get("druid.maxActive");
	final static String minIdle = ConfigListener.map.get("druid.minIdle");
	final static String maxWait = ConfigListener.map.get("druid.maxWait");
	
	final static String basePath = ConfigListener.map.get("config.basePath");
	
}
