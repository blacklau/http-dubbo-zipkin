package com.louie.core;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.louie.exception.LouieException;

@Component
public class ServiceDispacherImpl implements ServiceDispacher{
	/**
	 * 服务调用
	 */
	public CallResult invoke(Service service){
		String serviceName = service.getName();
		if(serviceName == null || serviceName == ""){
			throw new LouieException("服务未定义");
		}
		String[] names = serviceName.split("\\.");
		if(names.length < 3){
			throw new LouieException("服务定义错误");
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < names.length - 2;i++){
			sb.append(names[i]);
			sb.append(".");
		}
		sb.append(names[names.length - 2]);
		String serviceN = sb.toString();
		String methodstring = names[names.length -1];
		Object object = null;
		try{
			object = SpringApplicationContext.getBean(serviceN);
		}catch(Exception ex){
			
		}
		if(object == null){
			throw new LouieException("找不到该服务："+serviceN);
		}
		Method[] methods = object.getClass().getMethods();
		Method method = null;
		for(Method met:methods){
			if(met.getName().equals(methodstring)){
				method = met;
			}
		}
		if(method == null){
			throw new LouieException(serviceName+"方法未定义");
		}
		
		Class<?>[] clses = method.getParameterTypes();
		Object result = null;
		try{
			CallParams params = new CallParams();
			if(clses.length == 0){
				result = method.invoke(object, params);
			}else if(clses.length >= 1){
				Class cls = clses[0];
				if(cls.getName().equals(CallParams.class.getName())){
					params.setData(service.getData());
					result = method.invoke(object, params);
				}else{
					result = method.invoke(object, params);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new LouieException("调用异常");
		}
		
		CallResult sr = (CallResult) result;
		
		return sr;
	}
}
