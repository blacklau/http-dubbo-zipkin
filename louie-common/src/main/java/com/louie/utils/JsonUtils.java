package com.louie.utils;

import java.util.Map;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类
 * @author 丁威 2015年9月25日 下午5:56:25
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class JsonUtils {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@SuppressWarnings("unchecked")
	public static final Map<String, Object> jsonToMap(String jsonStr) {
		return (Map<String, Object>)json2Map(jsonStr);
	}
	
	public static String mapToJson(Map<String, Object> map) {
		return object2JSON(map);
	}
	
	public static final Map json2Map(String jsonStr) {
		if(StringUtils.isBlank(jsonStr)) return null;
		
		try {
			return objectMapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			logger.error("Json转换异常", e);
			return null;
		} 
	}
	
	
	public static final SortedMap json2SortedMap(String jsonStr) {
		if(StringUtils.isBlank(jsonStr)) return null;
		
		try {
			return objectMapper.readValue(jsonStr, SortedMap.class);
		} catch (Exception e) {
			logger.error("Json转换异常", e);
			return null;
		} 
	}
	
	public static String object2JSON(Object obj) {
		if(obj == null){
			return "{}";
		}
		
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("转换异常", e);
			return "";
		}
		//return JSON.toJSONString(obj,SerializerFeature.WriteDateUseDateFormat);
	}
	
	public static String map2Json(Map map) {
		return object2JSON(map);
	}
	
	public static final <T> T json2Bean(String content, Class<T> valueType) {
		if (StringUtils.isBlank(content))
			return null;

		try {
			return objectMapper.readValue(content, valueType);
		} catch (Exception e) {
			logger.error("Json转换异常", e);
			return null;
		}
	}
	
	public static final Map beanToMap(Object obj) {
		try {
			Map map = objectMapper.convertValue(obj, Map.class);
			return map;
		} catch (Exception e) {
			logger.error("Json转换异常", e);
			return null;
		}
	}
}

