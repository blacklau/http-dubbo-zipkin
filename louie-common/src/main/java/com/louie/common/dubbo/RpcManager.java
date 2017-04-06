package com.louie.common.dubbo;

import com.louie.core.CallResult;
import com.louie.core.Service;

public interface RpcManager {
	CallResult invoke(Service service);  
}
