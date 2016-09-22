package com.smallchill.core.toolbox.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.smallchill.core.constant.Const;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.kit.BeanKit;
import com.smallchill.core.toolbox.kit.StrKit;

/**
 * javabean 、 paras映射
 *
 */
public class BeanInjector {

	public static final <T> T inject(Class<T> beanClass, HttpServletRequest request) {
		try {
			return BeanKit.mapToBeanIgnoreCase(request.getParameterMap(), beanClass);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static final <T> T inject(Class<T> beanClass, String paraPerfix, HttpServletRequest request) {
		try {
			Map<String, Object> map = injectPara(paraPerfix, request);
			return BeanKit.mapToBeanIgnoreCase(map, beanClass);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static final Paras injectMaps(HttpServletRequest request) {
		return Paras.parse(request.getParameterMap());
	}

	public static final Paras injectMaps(String paraPerfix, HttpServletRequest request) {
		Map<String, Object> map = injectPara(paraPerfix, request);
		return Paras.parse(map);
	}

	private static final Map<String, Object> injectPara(String paraPerfix, HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, Object> map = new HashMap<>();
		String start = paraPerfix.toLowerCase() + ".";
		String[] value = null;
		for (Entry<String, String[]> param : paramMap.entrySet()) {
			if (!param.getKey().toLowerCase().startsWith(start)) {
				continue;
			}
			value = param.getValue();
			if (ArrayUtils.isNotEmpty(value) && null != value[0]) {
				Object o = (value[0].toString().equals("")) ? " " : value[0];
				map.put(StringUtils.removeStart(param.getKey().toLowerCase(), start).toLowerCase(), o);

			} else {
				map.put(StringUtils.removeStart(param.getKey().toLowerCase(), start).toLowerCase(), null);
			}
		}
		String versionL = request.getParameter(Const.OPTIMISTIC_LOCK.toLowerCase());
		String versionU = request.getParameter(Const.OPTIMISTIC_LOCK);
	    if (StrKit.notBlank(versionL)){
			map.put(Const.OPTIMISTIC_LOCK.toLowerCase(), Func.toInt(versionL) + 1);
		} else if(StrKit.notBlank(versionU)){
			map.put(Const.OPTIMISTIC_LOCK.toLowerCase(), Func.toInt(versionU) + 1);
		}
		return map;
	}


}
