package com.louie.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.louie.core.ServiceCallAdapter;
import com.louie.utils.JsonUtils;

public class ServletAdapter implements ServiceCallAdapter {

	private HttpServletRequest request;
	
	public ServletAdapter(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getName() {
		return request.getParameter("service");
	}

	@Override
	public Map getData() {
		String datastr = request.getParameter("data");
		return JsonUtils.json2Map(datastr);
	}

}
