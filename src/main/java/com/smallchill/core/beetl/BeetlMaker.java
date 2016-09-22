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

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import com.smallchill.core.toolbox.kit.FileKit;

/**
 * @title Beetl静态化生成
 * @author zhuangqian
 * @email smallchill@163.com
 * @date 2015-11-6下午5:17:55
 * @copyright 2015
 */
public abstract class BeetlMaker {

	/**
	 * 生成静态html
	 * 
	 * @param ftlPath 模板路径
	 * @param paras 参数
	 * @param htmlPath  html文件保存路径
	 */
	public static void makeHtml(String tlPath, Map<String, Object> paras, String htmlPath) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(htmlPath), "UTF-8"));
			BeetlTemplate.buildTo(FileKit.readString(tlPath, "UTF-8"), paras, pw);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

}
