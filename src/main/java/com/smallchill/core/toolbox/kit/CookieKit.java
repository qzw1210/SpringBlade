/**
 * Copyright (c) 2011-2015, James Zhan 詹波 (jfinal@126.com).
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieKit {

	/**
	 * Get cookie value by cookie name.
	 */
	public static String getCookie(String name, HttpServletRequest request) {
		return getCookie(name, null, request);
	}

	/**
	 * Get cookie value by cookie name.
	 */
	public static String getCookie(String name, String defaultValue, HttpServletRequest request) {
		Cookie cookie = getCookieObject(name, request);
		return cookie != null ? cookie.getValue() : defaultValue;
	}
	
	/**
	 * Get cookie object by cookie name.
	 */
	public static Cookie getCookieObject(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals(name))
					return cookie;
		return null;
	}
	
	/**
	 * Remove Cookie.
	 */
	public static void removeCookie(String name, HttpServletResponse response) {
		doSetCookie(name, null, 0, null, null, null, response);
	}

	/**
	 * Remove Cookie.
	 */
	public static void removeCookie(String name, String path, HttpServletResponse response) {
		doSetCookie(name, null, 0, path, null, null, response);
	}

	/**
	 * Remove Cookie.
	 */
	public static void removeCookie(String name, String path, String domain, HttpServletResponse response) {
		doSetCookie(name, null, 0, path, domain, null, response);
	}

	private static void doSetCookie(String name, String value, int maxAgeInSeconds, String path, String domain, Boolean isHttpOnly, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAgeInSeconds);
		// set the default path value to "/"
		if (path == null) {
			path = "/";
		}
		cookie.setPath(path);

		if (domain != null) {
			cookie.setDomain(domain);
		}
		if (isHttpOnly != null) {
			cookie.setHttpOnly(isHttpOnly);
		}
		response.addCookie(cookie);
	}
	
}
