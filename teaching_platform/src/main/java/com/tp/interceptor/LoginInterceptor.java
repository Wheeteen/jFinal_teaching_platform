package com.tp.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class LoginInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
//	    Integer userId =  inv.getController().getSessionAttr("loginUser"); 
//	    if(userId != null) {
//	    	inv.invoke();
//	    }
//	    else {
//    	    inv.getController().redirect("/file");
//    	}
	}

}
