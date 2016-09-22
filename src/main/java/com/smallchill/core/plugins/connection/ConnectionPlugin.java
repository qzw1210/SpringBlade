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
package com.smallchill.core.plugins.connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.beetl.sql.core.SQLManager;

import com.smallchill.core.config.BladeConfig;
import com.smallchill.core.interfaces.IPlugin;

public class ConnectionPlugin implements IPlugin{

	private static Map<String, SQLManager> pool = new ConcurrentHashMap<String, SQLManager>();
	
	public String MASTER = "master";
	
	public Map<String, SQLManager> getPool(){
		return pool;
	}
	
	private ConnectionPlugin() { }
	
	private static ConnectionPlugin me = new ConnectionPlugin();
	
	public static ConnectionPlugin init(){
		return me;
	}
	
	public void start() {
		try {
			for(String key : BladeConfig.getPool().keySet()){
				SQLManager sm = BladeConfig.getPool().get(key);
				pool.put(key, sm);
			}
			if(!pool.containsKey(MASTER)){
				throw new RuntimeException("BladeConfig必须注入key值为master的sqlManager!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		pool.clear();
	}

}
