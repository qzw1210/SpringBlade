package com.smallchill.core.interfaces;


/**
 * select aop
 */
public interface ISelect {
	
	IQuery userIntercept();
	
	IQuery deptIntercept();
	
	IQuery dictIntercept();
	
	IQuery roleIntercept();
	
}
