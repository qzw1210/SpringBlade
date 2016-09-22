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
package com.smallchill.core.intercept;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.LogKit;
import com.smallchill.core.toolbox.kit.StrKit;

/**
 * @author James Zhan, Chill Zhuang
 */
public abstract class BladeValidator extends BladeInterceptor {
	protected boolean succeed = true;
	protected HttpServletRequest request;

	protected static final String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

	protected void addError(String errorMessage) {
		if (succeed) {
			this.succeed = false;
			result.addError(errorMessage);
		} else {
			throw new RuntimeException();
		}
	}

	final public Object intercept(Invocation inv) {
		BladeValidator validator = null;
		try {
			validator = getClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		validator.request = inv.getRequest();
		try {
			validator.doValidate(inv);
		} catch (Exception ex) {
			LogKit.logNothing(ex);
		}
		if (validator.succeed) {
			return invoke();
		}else{
			return validator.result;
		}
	}

	/**
	 * Use validateXxx method to validate the parameters of this action.
	 */
	protected abstract void doValidate(Invocation inv);
	
	/**
	 * Judge whether the two values are equal
	 */
	protected void validateTwoEqual(String field1, String field2, String errorMessage) {
		if (Func.isAllEmpty(field1, field2)) {// 字符串为 null 或者为 "" 时返回 true
			addError(errorMessage);
		}
		String value1 = request.getParameter(field1);
		String value2 = request.getParameter(field2);
		if (!value1.equals(value2)) {
			addError(errorMessage);
		}
	}
	
	/**
	 * Judge whether the two values are not equal
	 */
	protected void validateTwoNotEqual(String field1, String field2, String errorMessage) {
		if (Func.isAllEmpty(field1, field2)) {// 字符串为 null 或者为 "" 时返回 true
			addError(errorMessage);
		}
		String value1 = request.getParameter(field1);
		String value2 = request.getParameter(field2);
		if (value1.equals(value2)) {
			addError(errorMessage);
		}
	}

	/**
	 * Check sql
	 */
	protected void validateSql(String field, String errorMessage) {
		if (StrKit.isBlank(field)) {// 字符串为 null 或者为 "" 时返回 true
			addError(errorMessage);
		}
		String sql = request.getParameter(field);
		sql = sql.toLowerCase();
		if (sql.indexOf("delete") >= 0 || sql.indexOf("update") >= 0
				|| sql.indexOf("insert") >= 0 || sql.indexOf("drop") >= 0) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate the illegal characters
	 */
	protected void validateStringExt(String field, String errorMessage) {
		if (Func.isAllEmpty(field)) {// 字符串为 null 或者为 "" 时返回 true
			addError(errorMessage);
		}
		String val = request.getParameter(field);
		if (val.indexOf("<") >= 0) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate Required. Allow space characters.
	 */
	protected void validateRequired(String field, String errorMessage) {
		String value = request.getParameter(field);
		if (value == null || "".equals(value)) // 经测试,form表单域无输入时值为"",跳格键值为"\t",输入空格则为空格" "
			addError(errorMessage);
	}

	/**
	 * Validate required string.
	 */
	protected void validateRequiredString(String field, String errorMessage) {
		if (StrKit.isBlank(request.getParameter(field)))
			addError(errorMessage);
	}

	/**
	 * Validate integer.
	 */
	protected void validateInteger(String field, int min, int max,
			String errorMessage) {
		validateIntegerValue(request.getParameter(field), min, max,
				errorMessage);
	}

	private void validateIntegerValue(String value, int min, int max,
			String errorMessage) {
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		try {
			int temp = Integer.parseInt(value.trim());
			if (temp < min || temp > max)
				addError(errorMessage);
		} catch (Exception e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate integer.
	 */
	protected void validateInteger(String field, String errorMessage) {
		validateIntegerValue(request.getParameter(field), errorMessage);
	}

	private void validateIntegerValue(String value, String errorMessage) {
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		try {
			Integer.parseInt(value.trim());
		} catch (Exception e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate long.
	 */
	protected void validateLong(String field, long min, long max,
			String errorMessage) {
		validateLongValue(request.getParameter(field), min, max, errorMessage);
	}

	private void validateLongValue(String value, long min, long max,
			String errorMessage) {
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		try {
			long temp = Long.parseLong(value.trim());
			if (temp < min || temp > max)
				addError(errorMessage);
		} catch (Exception e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate long.
	 */
	protected void validateLong(String field, String errorMessage) {
		validateLongValue(request.getParameter(field), errorMessage);
	}

	private void validateLongValue(String value, String errorMessage) {
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		try {
			Long.parseLong(value.trim());
		} catch (Exception e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate double.
	 */
	protected void validateDouble(String field, double min, double max,
			String errorMessage) {
		String value = request.getParameter(field);
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		try {
			double temp = Double.parseDouble(value.trim());
			if (temp < min || temp > max)
				addError(errorMessage);
		} catch (Exception e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate double.
	 */
	protected void validateDouble(String field, String errorMessage) {
		String value = request.getParameter(field);
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		try {
			Double.parseDouble(value.trim());
		} catch (Exception e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate date. Date formate: yyyy-MM-dd
	 */
	protected void validateDate(String field, String errorMessage) {
		String value = request.getParameter(field);
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		if (!DateKit.isValidDate(Func.toStr(value))) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate date.
	 */
	protected void validateDate(String field, Date min, Date max,
			String errorMessage) {
		String value = request.getParameter(field);
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		try {
			Date temp = DateKit.parseTime(Func.toStr(value));
			if (temp.before(min) || temp.after(max))
				addError(errorMessage);
		} catch (Exception e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate date. Date formate: yyyy-MM-dd
	 */
	protected void validateDate(String field, String min, String max,
			String errorMessage) {
		// validateDate(field, Date.valueOf(min), Date.valueOf(max), errorKey,
		// errorMessage); 为了兼容 64位 JDK
		try {
			validateDate(field, DateKit.parseTime(Func.toStr(min)),
					DateKit.parseTime(Func.toStr(max)), errorMessage);
		} catch (Exception e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate equal field. Usually validate password and password again
	 */
	protected void validateEqualField(String field_1, String field_2,
			String errorMessage) {
		String value_1 = request.getParameter(field_1);
		String value_2 = request.getParameter(field_2);
		if (value_1 == null || value_2 == null || (!value_1.equals(value_2)))
			addError(errorMessage);
	}

	/**
	 * Validate equal string.
	 */
	protected void validateEqualString(String s1, String s2, String errorMessage) {
		if (s1 == null || s2 == null || (!s1.equals(s2)))
			addError(errorMessage);
	}

	/**
	 * Validate equal integer.
	 */
	protected void validateEqualInteger(Integer i1, Integer i2,
			String errorMessage) {
		if (i1 == null || i2 == null || (i1.intValue() != i2.intValue()))
			addError(errorMessage);
	}

	/**
	 * Validate email.
	 */
	protected void validateEmail(String field, String errorMessage) {
		validateRegex(field, emailAddressPattern, false, errorMessage);
	}

	/**
	 * Validate URL.
	 */
	protected void validateUrl(String field, String errorMessage) {
		String value = request.getParameter(field);
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		try {
			value = value.trim();
			if (value.startsWith("https://"))
				value = "http://" + value.substring(8); // URL doesn't
														// understand the https
														// protocol, hack it
			new URL(value);
		} catch (MalformedURLException e) {
			addError(errorMessage);
		}
	}

	/**
	 * Validate regular expression.
	 */
	protected void validateRegex(String field, String regExpression,
			boolean isCaseSensitive, String errorMessage) {
		String value = request.getParameter(field);
		if (value == null) {
			addError(errorMessage);
			return;
		}
		Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression)
				: Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches())
			addError(errorMessage);
	}

	/**
	 * Validate regular expression and case sensitive.
	 */
	protected void validateRegex(String field, String regExpression,
			String errorMessage) {
		validateRegex(field, regExpression, true, errorMessage);
	}

	/**
	 * Validate string.
	 */
	protected void validateString(String field, int minLen, int maxLen,
			String errorMessage) {
		validateStringValue(request.getParameter(field), minLen, maxLen,
				errorMessage);
	}

	private void validateStringValue(String value, int minLen, int maxLen,
			String errorMessage) {
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		if (value.length() < minLen || value.length() > maxLen)
			addError(errorMessage);
	}

	/**
	 * validate boolean.
	 */
	protected void validateBoolean(String field, String errorMessage) {
		validateBooleanValue(request.getParameter(field), errorMessage);
	}

	private void validateBooleanValue(String value, String errorMessage) {
		if (StrKit.isBlank(value)) {
			addError(errorMessage);
			return;
		}
		value = value.trim().toLowerCase();
		if ("1".equals(value) || "true".equals(value)) {
			return;
		} else if ("0".equals(value) || "false".equals(value)) {
			return;
		}
		addError(errorMessage);
	}

}
