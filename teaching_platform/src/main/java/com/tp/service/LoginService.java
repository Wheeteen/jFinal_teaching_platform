package com.tp.service;

import java.util.List;

import com.tp.model.User;

public class LoginService {
	private static final User dao = new User().dao();
	
	public int login(int id, String password, int type) {
		User user =  findById(id,type);
		System.out.print(user);
		if(user == null){
			//user is not found
			return -1;
		}
		String pwd = user.getStr("password");
		System.out.print(pwd);
		if(pwd.equals(password)){
			System.out.println(1);
			//succeed
			return 1;
		}
		//password wrong
		return 0;
	}
	
	public int register(int id, String password,int type){
		User user =  findById(id,type);
		if(user == null){
			new User().set("user_id", id).set("password", password).set("type", type).set("status", 1).save();
			return 1;
		}
		return 0;
	}
	//ͨfind user info by user_id and type
	public User findById(int id, int type){
		List<User> user =  dao.find("select * from user_info where user_id = "+id+" and type = "+type);
		if(user.size() == 1){
			User user2 = user.get(0);
			return user2;
		}
		return null;
	}
}
