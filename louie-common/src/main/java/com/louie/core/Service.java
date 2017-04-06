package com.louie.core;

import java.io.Serializable;
import java.util.Map;

import com.louie.common.dubbo.RpcManager;
import com.louie.exception.LouieException;
/**
 * 服务定义
 * @author liuyong
 *
 */
public class Service implements Serializable{
	private static final long serialVersionUID = 287382239238L;
	/**
	 * 服务名，规范: 系统名+业务名+方法名,用"."隔开，如  "account.user.info"
	 */
	private String name;
	/**
	 * 调用参数
	 */
	private	Map data;
	
	public Service() {
	}

	public Service(String name, Map data) {
		super();
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
	
}