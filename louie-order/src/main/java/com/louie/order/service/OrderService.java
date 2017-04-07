package com.louie.order.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.louie.core.CallParams;
import com.louie.core.CallResult;
import com.louie.core.CommonAdapter;
import com.louie.core.ServiceCall;
import com.louie.order.dto.OrderDto;
import com.louie.utils.JsonUtils;

/**
 * 
 * @author 
 *
 */
@Lazy
@Service("order.customer")
public class OrderService{

	/**
	 * ∂©µ•œÍ«È
	 * @param params
	 * @return
	 */
	public CallResult orderInfo(CallParams params){
		System.out.println("request params:"+params.getData());
		Map<String,Object> userParams = new HashMap<String,Object>();
		userParams.put("token", params.get("token"));
		ServiceCall call = new ServiceCall(new CommonAdapter("account.user.info",userParams));
		CallResult cr = call.invoke();
		if(!cr.getCode().equals(0)) return cr;
		String userId = (String) cr.get("userId");
		OrderDto od = getOrder(userId,(Integer)params.get("orderId"));
		CallResult result = new CallResult();
		Map data = new HashMap<String,Object>();
		data .putAll(JsonUtils.beanToMap(od));
		result.setData(data);
		
		return result;
	}
	
	private OrderDto getOrder(String uid,Integer orderId){
		OrderDto od = new OrderDto("computer",5200d);
		return od;
	}

}
