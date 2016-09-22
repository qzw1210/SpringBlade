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

import com.smallchill.core.constant.Cst;



/**
 *  Blade配置型接口
 */
public interface IConfig {
	
	/**   
	 * 插件注册
	 * @param plugins 插件集合
	*/
	void registerPlugins(IPluginFactroy plugins);	
	
	/**   
	 * 全局变量设置
	*/
	void globalConstants(Cst me);
	
	/**   
	 * 全局设置
	*/
	void globalSettings();
	
	/**   
	 * 程序启动之后执行
	*/
	void afterBladeStart();
	
}
