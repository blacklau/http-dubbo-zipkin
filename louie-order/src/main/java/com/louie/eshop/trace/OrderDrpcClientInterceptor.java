package com.louie.eshop.trace;

import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.louie.common.dubbo.DrpcClientInterceptor;

@Configuration
@Activate
public class OrderDrpcClientInterceptor extends DrpcClientInterceptor implements Filter {
	
    public String getAppName() {
		return "order";
	}

}