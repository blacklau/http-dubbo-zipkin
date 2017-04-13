package com.louie.common.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 读取系统配置文件中值 (*.properties)
 * 
 * @author
 * 
 */
public class ZipkinConfig extends PropertyPlaceholderConfigurer {

	private static Map<String, String> ctxPropertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	/** 获取配置值 */
	private static String getValue(String name) {
		String value = ctxPropertiesMap.get(name);
		if (value == null)
			return null;
		return value;
	}

	/**
	 * 取出String类型的Property<br/>
	 *
	 * 如果没配置，默认返回空字符串
	 */
	public static String getProperty(String key) {
		return getProperty(key, "");
	}

	/**
	 * 取出String类型的Property，如果都為Null則返回Default值.
	 */
	public static String getProperty(String key, String defaultValue) {
		String value = getValue(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 取出Integer类型的Property<br/>
	 *
	 * 如果没配置，默认返回0
	 */
	public static Integer getInteger(String key) {
		return getInteger(key, 0);
	}

	/**
	 * 取出Integer类型的Property.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public static Integer getInteger(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Integer.valueOf(value) : defaultValue;
	}

	/**
	 * <br/>
	 *
	 * 如果没配置，默认返回0L
	 * 
	 * @param key
	 * @return
	 */
	public static final Long getLong(String key) {
		return getLong(key, 0L);
	}

	public static final Long getLong(String key, Long defaultValue) {
		String value = getValue(key);
		return value != null ? Long.valueOf(value) : defaultValue;
	}

	/**
	 * 取出Double类型的Property.如果内容错误则抛出异常. <br/>
	 *
	 * 如果没配置，默认返回0.0D
	 */
	public static Double getDouble(String key) {
		return getDouble(key, 0.0D);
	}

	/**
	 * 取出Double类型的Property.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public static Double getDouble(String key, Double defaultValue) {
		String value = getValue(key);
		return value != null ? Double.valueOf(value) : defaultValue;
	}

	/**
	 * 取出Boolean类型的Property.如果内容不是true/false则返回false.<br/>
	 *
	 * 如果没配置，默认返回false
	 */
	public static Boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	/**
	 * 取出Boolean类型的Property.如果都為Null則返回Default值,如果内容不为true/false则返回false.
	 */
	public static Boolean getBoolean(String key, boolean defaultValue) {
		String value = getValue(key);
		return value != null ? Boolean.valueOf(value) : defaultValue;
	}

	public static Map<String, String> getProperties() {
		return ctxPropertiesMap;
	}
}
