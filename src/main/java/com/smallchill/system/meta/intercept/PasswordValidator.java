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
package com.smallchill.system.meta.intercept;

import com.smallchill.common.vo.User;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.kit.StrKit;

public class PasswordValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateOldPwd("user.id", "user.oldPassword", "原密码错误!");
		validateRequired("user.newPassword", "新密码不能为空");
		validateRequired("user.newPasswordr", "确认密码不能为空");
		validateTwoNotEqual("user.oldPassword", "user.newPassword", "新密码不能与原密码相同!");
		validateTwoEqual("user.newPassword", "user.newPasswordr", "确认密码与新密码不相同!");
	}

	protected void validateOldPwd(String field1, String field2, String errorMessage) {
		String userId = request.getParameter(field1);
		String password = request.getParameter(field2);
		if (StrKit.isBlank(password)) {
			addError("请输入原密码!");
		}
		Blade blade = Blade.create(User.class);
		User user = blade.findById(userId);
		if(null == user){
			addError("未找到该用户!");
		}
		String pwd = user.getPassword();
		String salt = user.getSalt();
		boolean temp = (ShiroKit.md5(password, salt).equals(pwd));
		if (!temp) {
			addError(errorMessage);
		}
	}

}
