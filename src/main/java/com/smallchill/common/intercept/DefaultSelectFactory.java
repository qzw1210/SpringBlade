package com.smallchill.common.intercept;

import com.smallchill.core.intercept.SelectInterceptor;
import com.smallchill.core.interfaces.IQuery;

public class DefaultSelectFactory extends SelectInterceptor {
	
	public IQuery deptIntercept() {
		return new SelectDeptIntercept();
	}
	
	@Override
	public IQuery roleIntercept() {
		return new SelectRoleIntercept();
	}
	
}
