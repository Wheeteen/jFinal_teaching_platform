package com.tp.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.UserInfo;
import com.tp.clientModel.UserReqModel;
import com.tp.clientModel.UserRespModel;
import com.tp.model.User;
import com.tp.service.LoginService;
import com.tp.util.GetHeader;
import com.tp.util.Jwt;
import com.tp.util.MD5;
import com.tp.util.Result;

public class LoginController extends Controller{
	static LoginService service = new LoginService();
	
	@ActionKey("/")
	public void login() {
		String jsonString = HttpKit.readData(getRequest());
		UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
		String id = user.getId();  // 学号、工号
		String pwd = MD5.MD5(user.getPassword());
		int type = user.getType(); //1表示 student, 2表示teacher 
		
		JSONObject json = new JSONObject();
		UserReqModel userReqModel = new UserReqModel();
		
		int status = service.login(id, pwd,type);
		
		if(status == -1){
			json.put("success", false);
			json.put("error", "用户不存在");
		}
		if(status == 0){
			json.put("success", false);
			json.put("error", "密码错误");
		}
		if(status == 1){
			// 将用户的 id和 username 一并存入jwt中
			Record userRes = service.getUsername(id, type);
			json.put("success", false);
			json.put("error", "查询数据库出错");
			
			if(userRes != null){
				String username = userRes.getStr("username"); //用户名

				// 通过jwt来记录用户信息
				userReqModel.setId(id); //用户的id(学号or工号  唯一)
				userReqModel.setType(type);
				userReqModel.setUsername(username);  // 用户的名字
				
				String jwt = Jwt.create(userReqModel);  // 生成用户的jwt
				
				System.out.println(jwt);
				if(jwt != null){
					json.put("success", true);
					json.put("id", id);
					json.put("type", type);
					json.put("username", username);
					json.put("error", null);
					json.put("jwt", jwt);
				}
				
			}
		}
		renderJson(json.toJSONString());
	}
	

	public void register() {
		String jsonString = HttpKit.readData(getRequest());
		UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
		String id = user.getId(); // 用户的学号or工号
		String pwd = MD5.MD5(user.getPassword());
		String username = user.getUsername();
		int type = user.getType(); 
		
		Result<UserRespModel> result = new Result<UserRespModel>(false,"用户已存在");
		int status = 0;
		if(type == 1){
			status = service.registerStu(id, pwd, username);
		}
		if(type == 2) {
			status = service.registerTea(id, pwd, username);
		}
		switch(status) {
		case 1:
		  result = new Result<UserRespModel>(true);
		  break;
		case 0:
		  result = new Result<UserRespModel>(false,"用户已存在");
		  break;
	    default:
	    	result = new Result<UserRespModel>(false, "插入数据出错");
		}
		renderJson(result);
	}
	// forget password?
	public void forgetPwd(){
		String jsonString = HttpKit.readData(getRequest());
		UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
		String id = user.getId(); // 传参：用户的personId(学号or工号) 、 用户的密码、用户的类型
		String pwd = MD5.MD5(user.getPassword());
		int type = user.getType(); //1 student , 2 teacher , 3 administrator
		
		Result<UserRespModel> result = service.changePwd(id, pwd, type);
		renderJson(result);
	}
	
	// 获取用户信息
	public void getUserInfo() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String id = userInfo.getId();
		  int type = userInfo.getType();
		
		  Result<UserRespModel> result = service.getUserInfo(id, type);
		  renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	/**
	 * 1. 修改用户信息(modify user info)、完善用户信息
	 * 2. 只能修改用户的头像（上传头像）、用户的email（可为空）
	 * 3. 注意： 用户的id 和 username不能修改！！！密码的修改调用接口forgetPwd
	 * 4. 用户状态的需求先不做
	 */
	public void updateUserInfo() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String id = userInfo.getId();
		  int type = userInfo.getType();
		  
		  String jsonString = HttpKit.readData(getRequest());
		  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
		  String file_id = user.getFile_id();
		  String email = user.getEmail();
		
		  Result<UserRespModel> result = service.updateUserInfo(id, type, file_id, email);
		  renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
}
