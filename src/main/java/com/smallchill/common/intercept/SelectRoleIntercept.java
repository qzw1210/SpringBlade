package com.smallchill.common.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.intercept.QueryInterceptor;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.kit.StrKit;

public class SelectRoleIntercept extends QueryInterceptor {

	public void queryBefore(AopContext ac) {
		if (ShiroKit.lacksRole(ConstShiro.ADMINISTRATOR)) {
			String roles = ShiroKit.getUser().getRoles() + "," + ShiroKit.getUser().getSubRoles();
			String condition = "where id in (" + StrKit.removeSuffix(roles, ",") + ")";
			ac.setCondition(condition);
		}
	}

}
