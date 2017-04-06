package com.louie.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.louie.core.CallResult;
import com.louie.core.ServiceCall;
import com.louie.utils.JsonUtils;

@RestController
@EnableWebMvc
public class ApiController {

  @RequestMapping("/service")
  public String b( HttpServletRequest request) throws InterruptedException {
	   
	  ServiceCall call = new ServiceCall(new ServletAdapter(request));
	  CallResult cr = call.invoke();
	   
	  return JsonUtils.object2JSON(cr);
  }
  
}
