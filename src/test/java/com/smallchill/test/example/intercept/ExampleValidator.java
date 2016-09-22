package com.smallchill.test.example.intercept;

import com.smallchill.common.vo.User;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.kit.StrKit;

public class ExampleValidator extends BladeValidator{

	@Override
	protected void doValidate(Invocation inv) {
		
		//在controller中使用
		//@Before(ExampleValidator.class)
		//可以在执行方法前先执行ExampleIntercept的doValidate方法
		//这样达到细粒度拦截的目的
		//controller方法必须带有@ResponseBody注解
		//例如请求uri为：/test/validate?abc.account=123&abc.email=123&abc.password=1
		//这样就会按照执行顺序进行后端校验,如果当中有一个出错则直接返回错误信息
		
		validateRequired("abc.account", "请输入账号");
		validateAccount("abc.account", "该账号已存在");
		validateRequired("abc.name", "请输入姓名");
		validateEmail("abc.email", "请输入正确的邮箱");
		validateString("abc.password", 5, 20, "请输入5到20位的密码");
	}
	
	/**
	 * 自定义校验方法
	 * 可以数据库交互,推荐dao工具为Blade或者Blade
	 * Blade针对于javabean,Blade针对于通用模型
	 */
	protected void validateAccount(String field, String errorMessage) {
		String account = request.getParameter(field);
		if (StrKit.isBlank(account)) {
			addError("请输入账号!");
		}
		if (Blade.create(User.class).isExist("SELECT * FROM tfw_user WHERE account = #{account} and status=1", Paras.create().set("account", account))) {
			addError(errorMessage);
		}
	}

}
