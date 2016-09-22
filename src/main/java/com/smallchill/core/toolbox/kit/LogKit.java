/**
 * Copyright (c) 2011-2016, James Zhan 詹波 (jfinal@126.com).
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

package com.smallchill.core.toolbox.kit;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smallchill.core.toolbox.Func;

/**
 * LogKit.
 */
public class LogKit {

	private static class Holder {
		private static Logger log = LoggerFactory.getLogger(LogKit.class);
	}

	/**
	 * 当通过 Constants.setLogFactory(...) 或者
	 * LogManager.me().setDefaultLogFacotyr(...) 指定默认日志工厂以后，重置一下内部 Log
	 * 对象，以便使内部日志实现与系统保持一致
	 */
	public static void synchronizeLog() {
		Holder.log = LoggerFactory.getLogger(LogKit.class);
	}

	/**
	 * Do nothing.
	 */
	public static void logNothing(Throwable t) {

	}

	public static void println(String message) {
		System.out.println(message);
	}
	
	public static void println(String message, Object ... values) {
		System.out.println(Func.format(message, values));
	}
	
	public static void println(String message, Map<?, ?> map) {
		System.out.println(Func.format(message, map));
	}

	public static void report(String message) {
		System.out.println("/nBlade report ------------- "+ DateKit.getTime() + " -----------------------------");
		System.out.println("msg	: " + message);
		System.out.println("---------------------------------------------------------------------------------");
	}

	public static void debug(String message) {
		Holder.log.debug(message);
	}

	public static void debug(String message, Throwable t) {
		Holder.log.debug(message, t);
	}

	public static void info(String message) {
		Holder.log.info(message);
	}

	public static void info(String message, Throwable t) {
		Holder.log.info(message, t);
	}

	public static void warn(String message) {
		Holder.log.warn(message);
	}

	public static void warn(String message, Throwable t) {
		Holder.log.warn(message, t);
	}

	public static void error(String message) {
		Holder.log.error(message);
	}

	public static void error(String message, Throwable t) {
		Holder.log.error(message, t);
	}

	/*public static void fatal(String message) {
		Holder.log.fatal(message);
	}

	public static void fatal(String message, Throwable t) {
		Holder.log.fatal(message, t);
	}*/

	public static boolean isDebugEnabled() {
		return Holder.log.isDebugEnabled();
	}

	public static boolean isInfoEnabled() {
		return Holder.log.isInfoEnabled();
	}

}
