package com.smallchill.core.toolbox;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import com.smallchill.core.toolbox.kit.BeanKit;
import com.smallchill.core.toolbox.support.Convert;

/**
 * 扩充了HashMap中的方法
 * 
 * @author loolly, chill
 * 
 */
@SuppressWarnings("serial")
public class Paras extends HashMap<String, Object> {

	/**
	 * 创建Paras
	 * @return Paras
	 */
	public static Paras create() {
		return new Paras();
	}

	private Paras(){
		
	}
	
	/**
	 * 创建HashMap
	 * 
	 * @return HashMap
	 */
	public static HashMap<String, Object> createHashMap() {
		return new HashMap<>();
	}
	
	/**
	 * 将PO对象转为Maps
	 * @param <T>
	 * @param bean Bean对象
	 * @return Vo
	 */
	public static <T> Paras parse(T bean) {
		return create().parseBean(bean);
	}

	/**
	 * 将map对象转为Maps
	 * 
	 * @param <T>
	 * @param vo
	 *            值对象
	 * @return Vo
	 */
	public static <T> Paras parse(Map<String, Object> map) {
		return create().parseMap(map);
	}

	
	/**
	 * 转换为Bean对象
	 * @param <T>
	 * @param bean Bean
	 * @return Bean
	 */
	public <T> T toBean(T bean) {
		BeanKit.fillBeanWithMap(this, bean);
		return bean;
	}
	
	/**
	 * 填充Value Object对象
	 * @param clazz Value Object（或者POJO）的类
	 * @return vo
	 */
	public <T> T toBean(Class<T> clazz) {
		return BeanKit.mapToBean(this, clazz);
	}
	
	/**
	 * 填充Value Object对象，忽略大小写
	 * @param clazz Value Object（或者POJO）的类
	 * @return vo
	 */
	public <T> T toBeanIgnoreCase(Class<T> clazz) {
		return BeanKit.mapToBeanIgnoreCase(this, clazz);
	}
	
	/**
	 * 将值对象转换为Maps<br>
	 * 类名会被当作表名，小写第一个字母
	 * @param <T>
	 * @param bean 值对象
	 * @return 自己
	 */
	public <T> Paras parseBean(T bean) {
		this.putAll(BeanKit.beanToMap(bean));
		return this;
	}
	
	/**
	 * 将值对象转换为Maps<br>
	 * 类名会被当作表名，小写第一个字母
	 * 
	 * @param <T>
	 * @param map
	 *            值对象
	 * @return 自己
	 */
	public <T> Paras parseMap(Map<String, Object> map) {
		this.putAll(map);
		return this;
	}

	/**
	 * 与给定实体对比并去除相同的部分<br>
	 * 此方法用于在更新操作时避免所有字段被更新，跳过不需要更新的字段
	 * version from 2.0.0
	 * @param Paras
	 * @param withoutNames 不需要去除的字段名
	 */
	public <T extends Paras> void removeEqual(T map, String... withoutNames) {
		HashSet<String> withoutSet = new HashSet<String>();
		for (String name : withoutNames) {
			withoutSet.add(name);
		}
		
		for(Entry<String, Object> entry : map.entrySet()) {
			if(withoutSet.contains(entry.getKey())) {
				continue;
			}
			
			final Object value = this.get(entry.getKey());
			if(null != value && value.equals(entry.getValue())) {
				this.remove(entry.getKey());
			}
		}
	}

	//-------------------------------------------------------------------- 特定类型值
	/**
	 * 设置列
	 * @param attr 属性
	 * @param value 值
	 * @return 本身
	 */
	public Paras set(String attr, Object value) {
		return this.put(attr, value);
	}
	
	/**
	 * 设置列
	 * @param attr 属性
	 * @param value 值
	 * @return 本身
	 */
	@Override
	public Paras put(String attr, Object value) {
		super.put(attr, value);
		return this;
	}
	
	
	/**
	 * 设置列，当键或值为null时忽略
	 * @param attr 属性
	 * @param value 值
	 * @return 本身
	 */
	public Paras setIgnoreNull(String attr, Object value) {
		if(null != attr && null != value) {
			set(attr, value);
		}
		return this;
	}
	
	/**
	 * 获得特定类型值
	 * @param attr 字段名
	 * @param defaultValue 默认值
	 * @return 字段值
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String attr, T defaultValue) {
		final Object result = get(attr);
		return (T)(result != null ? result : defaultValue);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public String getStr(String attr) {
		return Convert.toStr(get(attr), "");
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public Integer getInt(String attr) {
		return Convert.toInt(get(attr), 0);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public Long getLong(String attr) {
		return Convert.toLong(get(attr), null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public Float getFloat(String attr) {
		return Convert.toFloat(get(attr), null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public Boolean getBool(String attr) {
		return Convert.toBool(get(attr), null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public byte[] getBytes(String attr) {
		return get(attr, null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public Date getDate(String attr) {
		return get(attr, null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public Time getTime(String attr) {
		return get(attr, null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public Timestamp getTimestamp(String attr) {
		return get(attr, null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public Number getNumber(String attr) {
		return get(attr, null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public BigDecimal getBigDecimal(String attr) {
		return get(attr, null);
	}
	
	/**
	 * @param attr 字段名
	 * @return 字段值
	 */
	public BigInteger getBigInteger(String attr) {
		return get(attr, null);
	}
	
	//-------------------------------------------------------------------- 特定类型值
	
	@Override
	public Paras clone() {
		return (Paras) super.clone();
	}
	
	//-------------------------------------------------------------------- 特定类型值
}
