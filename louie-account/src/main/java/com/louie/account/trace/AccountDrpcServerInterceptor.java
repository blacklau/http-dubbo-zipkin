package com.louie.account.trace;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.louie.common.dubbo.DrpcServerInterceptor;
@Activate
public final class AccountDrpcServerInterceptor extends DrpcServerInterceptor implements Filter {
	
    public String getAppName() {
		return "account";
	}

}