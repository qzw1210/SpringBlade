package com.smallchill.core.toolbox.kit;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.smallchill.core.exception.ToolBoxException;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.support.Convert;

/**
 * Bean工具类
 * @author Looly
 *
 */
public class BeanKit {
	
	/**
	 * 获得Bean类属性描述
	 * @param clazz Bean类 
	 * @return PropertyDescriptor数组
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws IntrospectionException{
		return Introspector.getBeanInfo(clazz).getPropertyDescriptors();
	}
	
	/**
	 * 获得Bean类属性描述
	 * @param clazz Bean类 
	 * @param fieldName 字段名
	 * @return PropertyDescriptor
	 * @throws IntrospectionException
	 */
	/*public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, final String fieldName) throws IntrospectionException{
		PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if(ObjectUtil.equals(fieldName, propertyDescriptor.getName())){
				return propertyDescriptor;
			}
		}
		return null;
	}*/
	
	/**
	 * Map转换为Bean对象
	 * 
	 * @param map Map
	 * @param beanClass Bean Class
	 * @return Bean
	 */
	public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass) {
		return fillBeanWithMap(map, ClassKit.newInstance(beanClass));
	}
	
	/**
	 * Map转换为Bean对象<br>
	 * 忽略大小写
	 * 
	 * @param map Map
	 * @param beanClass Bean Class
	 * @return Bean
	 */
	public static <T> T mapToBeanIgnoreCase(Map<?, ?> map, Class<T> beanClass) {
		return fillBeanWithMapIgnoreCase(map, ClassKit.newInstance(beanClass));
	}
	
	/**
	 * 使用Map填充Bean对象
	 * 
	 * @param map Map
	 * @param bean Bean
	 * @return Bean
	 */
	public static <T> T fillBeanWithMap(final Map<?, ?> map, T bean) {
		return fill(bean, new ValueProvider(){
			@Override
			public Object value(String name) {
				return map.get(name);
			}
		});
	}
	
	/**
	 * 使用Map填充Bean对象，忽略大小写
	 * 
	 * @param map Map
	 * @param bean Bean
	 * @return Bean
	 */
	public static <T> T fillBeanWithMapIgnoreCase(Map<?, ?> map, T bean) {
		final Map<Object, Object> map2 = new HashMap<Object, Object>();
		for (Entry<?, ?> entry : map.entrySet()) {
			final Object key = entry.getKey();
			if(key instanceof String) {
				final String keyStr = (String)key;
				map2.put(keyStr.toLowerCase(), entry.getValue());
			}else{
				map2.put(key, entry.getValue());
			}
		}
		
		return fill(bean, new ValueProvider(){
			@Override
			public Object value(String name) {
				return map2.get(name.toLowerCase());
			}
		});
	}
	
	/**
	 * ServletRequest 参数转Bean
	 * @param request ServletRequest
	 * @param beanClass Bean Class
	 * @return Bean
	 */
	public static <T> T requestParamToBean(javax.servlet.ServletRequest request, Class<T> beanClass){
		return fillBeanWithRequestParam(request, ClassKit.newInstance(beanClass));
	}
	
	/**
	 * ServletRequest 参数转Bean
	 * @param request ServletRequest
	 * @param bean Bean
	 * @return Bean
	 */
	public static <T> T fillBeanWithRequestParam(final javax.servlet.ServletRequest request, T bean){
		final String beanName = StrKit.lowerFirst(bean.getClass().getSimpleName());
		return fill(bean, new ValueProvider(){
			@Override
			public Object value(String name) {
				String value = request.getParameter(name);
				if (StrKit.isBlank(value)) {
					//使用类名前缀尝试查找值
					value = request.getParameter(beanName + StrKit.DOT + name);
					if(StrKit.isBlank(value)){
						//此处取得的值为空时跳过，包括null和""
						value = null;
					}
				}
				return value;
			}
		});
	}
	
	/**
	 * ServletRequest 参数转Bean
	 * @param <T>
	 * @param beanClass Bean Class
	 * @param valueProvider 值提供者
	 * @return Bean
	 */
	public static <T> T toBean(Class<T> beanClass, ValueProvider valueProvider){
		return fill(ClassKit.newInstance(beanClass), valueProvider);
	}
	
	/**
	 * 填充Bean
	 * @param <T>
	 * @param bean Bean
	 * @param valueProvider 值提供者
	 * @return Bean
	 */
	public static <T> T fill(T bean, ValueProvider valueProvider){
		if(null == valueProvider){
			return bean;
		}
		
		Class<?> beanClass = bean.getClass();
		try {
			PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(beanClass);
			String propertyName;
			Object value;
			for (PropertyDescriptor property : propertyDescriptors) {
				propertyName = property.getName();
				value = valueProvider.value(propertyName);
				if (null == value) {
					//此处取得的值为空时跳过，包括null和""
					continue;
				}
				
				try {
					property.getWriteMethod().invoke(bean, Convert.parse(property.getPropertyType(), value));
				} catch (Exception e) {
					throw new ToolBoxException(StrKit.format("Inject [{}] error!", property.getName()), e);
				}
			}
		} catch (Exception e) {
			throw new ToolBoxException(e);
		}
		return bean;
	}

	/**
	 * 对象转Map
	 * @param bean bean对象
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> beanToMap(T bean) {

		if (bean == null) {
			return null;
		}
		if(bean.getClass().equals(Paras.class)){
			return (Map<String, Object>) bean;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(bean.getClass());
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (false == key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(bean);
					if (null != value) {
						map.put(key, value);
					}
				}
			}
		} catch (Exception e) {
			throw new ToolBoxException(e);
		}
		return map;
	}

	/**   
	 * map互转(入库)
	 * @param oldMap	("xl","1")
	 * @param switchMap	("xl","f_it_xl")
	 * @return 			("f_it_xl","1")
	*/
	public static Map<String, Object> map2Map(Map<?, ?> oldMap,
			Map<String, Object> switchMap) {
		Map<String, Object> map = new HashMap<>();
		for (String key : switchMap.keySet()) {
			map.put(switchMap.get(key).toString().toLowerCase(),
					oldMap.get(key));
		}
		return map;
	}
	
	/**
	 * map互转(出库)
	 * @param oldMap	 ("f_it_xl","1")
	 * @param reverseMap ("xl","f_it_xl")
	 * @return			 ("xl","1")
	 */
	public static Map<String, Object> reverseMap2Map(Map<?, ?> oldMap, Map<String, Object> reverseMap) {
		Map<String, Object> map = new HashMap<>();
		for (String key : reverseMap.keySet()) {
			String reverseKey = ((String) reverseMap.get(key)).toUpperCase();
			map.put(key.toLowerCase(), oldMap.get(reverseKey));
		}
		return map;
	}
	
	/**   
	 * bean-map翻转
	 * @param reverseMap
	 * @param model
	 * @return Map<String,Object>
	*/
	public static Map<String, Object> reverse(Map<String, Object> reverseMap,
			Object model) {
		Map<String, Object> map = beanToMap(model);
		if (!Func.isEmpty(reverseMap)) {
			return map2Map(map, reverseMap);
		}
		return map;
	}
	
	/**
	 * 值提供者，用于提供Bean注入时参数对应值得抽象接口
	 * @author Looly
	 *
	 */
	public static interface ValueProvider{
		/**
		 * 获取值
		 * @param name Bean对象中参数名
		 * @return 对应参数名的值
		 */
		public Object value(String name);
	}
}
