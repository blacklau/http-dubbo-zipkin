package com.louie.common.dubbo;

import com.louie.core.CallResult;
import com.louie.core.Service;
import com.louie.core.ServiceDispacher;
import com.louie.core.ServiceDispacherImpl;

public class RpcManagerImpl implements RpcManager{
	
	
	public CallResult invoke(Service service) {
		// TODO Auto-generated method stub
		//System.out.println("client r:"+obj.toString());
		//return obj.toString();
		/*
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationConsumer.xml" });  
	    context.start();   
	    RpcManager demoServer = (RpcManager) context.getBean("demoService");  
	    Map<String,Object> params = new HashMap<String,Object>();
	    params.put("name", "account.user.login");
	    ServiceCall  shop = new ServiceCall();
	    shop.setName("account.user.info");
	    String result = demoServer.sayHello(shop);
	    System.out.println("eshop:"+result);  
	    */
		ServiceDispacher serviceDispacher = new ServiceDispacherImpl();
		return serviceDispacher.invoke(service);
		
	}

}
