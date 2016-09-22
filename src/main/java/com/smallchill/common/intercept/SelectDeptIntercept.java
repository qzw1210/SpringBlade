package com.smallchill.common.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.intercept.QueryInterceptor;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.kit.StrKit;

public class SelectDeptIntercept extends QueryInterceptor {

	public void queryBefore(AopContext ac) {
		if (ShiroKit.lacksRole(ConstShiro.ADMINISTRATOR)) {
			String depts = ShiroKit.getUser().getDeptId() + "," + ShiroKit.getUser().getSubDepts();
			String condition = "where id in (" + StrKit.removeSuffix(depts, ",") + ")";
			ac.setCondition(condition);
		}
	}

}
