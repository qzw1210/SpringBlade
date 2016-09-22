package com.smallchill.test.example.intercept;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeInterceptor;
import com.smallchill.core.toolbox.kit.StrKit;

public class ExampleIntercept extends BladeInterceptor {

	@Override
	public Object intercept(Invocation inv) {
		
		//在controller中使用
		//@Before(ExampleIntercept.class)
		//可以在执行方法前先执行ExampleIntercept的intercept方法
		//这样达到细粒度拦截的目的
		//controller方法必须带有@ResponseBody注解
		
		HttpServletRequest request = inv.getRequest();
		String test = request.getParameter("test");
		if (StrKit.notBlank(test)) {
			return invoke();
		} else {
			return result.addError("操作失败");
		}
	}
}
