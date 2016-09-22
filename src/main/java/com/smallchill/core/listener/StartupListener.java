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
package com.smallchill.core.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.smallchill.core.config.BladeConfig;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.interfaces.IPluginFactroy;
import com.smallchill.core.plugins.PluginFactory;
import com.smallchill.core.plugins.PluginManager;
import com.smallchill.core.plugins.connection.ConnectionPlugin;

/**
 * 启动监听器
 * 
 */
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			globalConstants(Cst.me());
			registerPlugins();
			globalSettings();
			afterBladeStart();
		}
	}
	
	/**   
	 * 全局配置
	*/
	private void globalConstants(Cst me){
		BladeConfig.getConf().globalConstants(me);
	}

	/**
	 * 插件的启用
	 */
	private void registerPlugins() {
		IPluginFactroy plugins = PluginFactory.init();
		plugins.register(ConnectionPlugin.init());
		BladeConfig.getConf().registerPlugins(plugins);//自定义配置插件	
		PluginManager.init().start();
	}
	
	/**   
	 * 全局配置
	*/
	private void globalSettings(){
		BladeConfig.getConf().globalSettings();
	}
	
	/**   
	 * 系统启动后执行
	*/
	private void afterBladeStart(){
		BladeConfig.getConf().afterBladeStart();
	}
	
}