package com.louie.trace;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.louie.common.dubbo.DrpcClientInterceptor;

@Activate
public class WebApiDrpcClientInterceptor extends DrpcClientInterceptor implements Filter {
	
    public String getAppName() {
		return "webapi";
	}

}