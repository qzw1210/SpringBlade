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
package com.smallchill.core.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

public class Invocation {
	private Class<?> clazz;
	private Method method;
	private Object[] args;
	private HttpServletRequest request;

	public Invocation(Class<?> clazz, Method method, Object[] args,
			HttpServletRequest request) {
		this(method, args, request);
		this.clazz = clazz;
	}

	public Invocation(Method method, Object[] args, HttpServletRequest request) {
		super();
		this.method = method;
		this.args = args;
		this.request = request;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
