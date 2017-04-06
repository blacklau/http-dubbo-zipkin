package com.louie.core;

import java.io.Serializable;
import java.util.Map;
/**
 * 服务调用参数定义
 * @author liuyong
 *
 */
public class CallParams implements Serializable{
	
	private static final long serialVersionUID = 273828239238L;
	
	/**
	 * 调用参数
	 */
	Map data;

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
	
	public Object get(String key){
		Object value = data != null?data.get(key):null;
		return value;
	}
}