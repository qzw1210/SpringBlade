package com.smallchill.core.aop;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.DoLog;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.log.LogManager;

/**
 * AOP 日志
 */
@Aspect
@Component
public class LogAop {
	private static Logger log = LoggerFactory.getLogger(LogAop.class);

	//@Pointcut("within(@org.springframework.stereotype.Controller *)")
	@Pointcut("execution(* com.smallchill.*..service.*.*(..))")
	public void cutService() {
	}

	@Around("cutService()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
		if(!LogManager.isDoLog()){
			return point.proceed();
		}
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		String methodName = ms.getName();
		DoLog doLog = method.getAnnotation(DoLog.class);
		if (!isWriteLog(methodName) && null == doLog) {
			return point.proceed();
		}
		ShiroUser user = ShiroKit.getUser();
		if(null == user){
			return point.proceed();
		}
		String className = point.getTarget().getClass().getName();
		Object[] params = point.getArgs();
		StringBuilder sb = new StringBuilder();
		Enumeration<String> paraNames = null;
		HttpServletRequest request = HttpKit.getRequest();
		if (params != null && params.length > 0) {
			paraNames = request.getParameterNames();
			String key;
			String value;
			while (paraNames.hasMoreElements()) {
				key = paraNames.nextElement();
				value = request.getParameter(key);
				Func.builder(sb, key, "=", value, "&");
			}
			if (StringUtils.isEmpty(sb)) {
				Func.builder(sb, request.getQueryString());
			}
		}
		try {
			String msg = Func.format("[类名]:{}  [方法]:{}  [参数]:{}", className, methodName, StrKit.removeSuffix(sb.toString(), "&"));
			LogManager.doLog(user, msg, getLogName(methodName), request, true);
			log.info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return point.proceed();
	}

	private boolean isWriteLog(String method) {
		String[] pattern = LogManager.logPatten();
		for (String s : pattern) {
			if (method.indexOf(s) > -1) {
				return true;
			}
		}
		return false;
	}
	
	private String getLogName(String method){
		String[] pattern = LogManager.logPatten();
		for (String s : pattern) {
			if (method.indexOf(s) > -1) {
				return LogManager.logMaps().getStr(s);
			}
		}
		return "";
	}
	
}
