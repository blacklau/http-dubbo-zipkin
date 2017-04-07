package com.louie.account.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.louie.core.CallParams;
import com.louie.core.CallResult;

/**
 * 
 * @author 
 *
 */
@Lazy
@Service("account.user")
public class UserService{
	/**
	 * 
	 * @param context
	 * @param user
	 * @return
	 */
	public CallResult info(CallParams params){

		CallResult cr = new CallResult();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("userId", "ujklskkkkliiekkikhhjkj");
		cr.setCode(0);
		cr.setData(data);
		   
		return cr;
	}

}
