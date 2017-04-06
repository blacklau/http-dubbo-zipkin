package com.louie.core;

import java.util.Map;
/**
 * 服务调用参数定义
 * @author liuyong
 *
 */
public class CommonAdapter implements ServiceCallAdapter{
	
	private String service;
	private Map data;
	
	public CommonAdapter(String service,Map data) {
		super();
		this.service = service;
		this.data = data;
	}
	public String getName(){
		return service;
	}
	public Map getData(){
		return data;
	}
}