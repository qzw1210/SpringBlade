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
package com.smallchill.core.plugins;

import java.util.List;

import com.smallchill.core.interfaces.IPlugin;

public class PluginManager implements IPlugin{
	private static List<IPlugin> plugins = PluginFactory.init().getPlugins();
	
	private static PluginManager me = new PluginManager();
	
	public static PluginManager init(){
		return me;
	}
	
	private PluginManager(){}

	public void start() {
		for(IPlugin plugin : plugins){
			plugin.start();
		}
	}

	public void stop() {
		for(IPlugin plugin : plugins){
			plugin.stop();
		}
		plugins.clear();
	}

}
