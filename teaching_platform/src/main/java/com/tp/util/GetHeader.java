package com.tp.util;

import javax.servlet.http.HttpServletRequest;

import com.tp.clientModel.CheckResult;
import com.tp.clientModel.UserInfo;
import com.tp.clientModel.UserRespModel;

import io.jsonwebtoken.Claims;

public class GetHeader {
	public static UserInfo getHeader(HttpServletRequest request){
		String jwt = request.getHeader("Authorization"); // 接收jwt字符串
//		String jwt = request.getParameter("token");
		CheckResult checkResult = Jwt.validateJWT(jwt); // 校验jwt字符串
		UserInfo user = new UserInfo();
		if(checkResult.getSuccess() == true){
			Claims claims = checkResult.getClaims();
			// 取出jwt里面存储的信息
			String id =  (String) claims.get("id");
			int type = (int) claims.get("type");
			String name = (String) claims.get("username");
			user.setSuccess(true);
			user.setId(id);
			user.setType(type);
			user.setName(name);
		} else {
			String error = checkResult.getError();
			user.setSuccess(false);
			user.setError(error);	
		}
		return user;
	}
}