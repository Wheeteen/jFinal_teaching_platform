package com.tp.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.tp.clientModel.UserReqModel;
import com.tp.model.User;
import com.tp.service.LoginService;
import com.tp.util.Result;

public class LoginController extends Controller{
static LoginService service = new LoginService();
	
	@ActionKey("/")
	public void login() {
		String jsonString = HttpKit.readData(getRequest());
		UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
		int id = user.getId();
		String pwd = user.getPassword();
		int type = user.getType(); //1 student , 2 teacher , 3 administrator
		
		Result<User> result = null;
		int status = service.login(id, pwd,type);
		if(status == -1){
			result = new Result<User>(false, "User is not found");
		}
		if(status == 0){
			result = new Result<User>(false, "Password is wrong");
		}
		if(status == 1){
			result = new Result<User>(true);
		}
		renderJson(result);
	}
	
	public void register() {
		String jsonString = HttpKit.readData(getRequest());
		/**
		 * ʹ�� fastjson ��ת 
		 * Blog blog = Blog.me.findById(1);
		 * ʹ�� JsonKit : �� ת json 
		 * String blogJson = blog.toJson();
		 * ʹ�� fastjson : json ת �� 
		 * Blog parseObject = JSONObject.parseObject(blogJson, Blog.class);
		 */
		UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
		int id = user.getId();
		String pwd = user.getPassword();
		int type = user.getType(); 
		
		Result<User> result = new Result<User>(false,"The user is existed");
		int status = service.register(id, pwd,type);
		if(status == 1){
			result = new Result<User>(true);
		}
		renderJson(result);
	}
	
	public void test() {
		Result<User> result = new Result<User>(true);
		renderJson(result);
	}
}
