package com.tp.service;

import java.util.HashMap;
import java.util.List;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.UserRespModel;
import com.tp.model.Student;
import com.tp.model.Teacher;
import com.tp.util.Result;

public class LoginService {
	Student student = new Student();
	Teacher teacher = new Teacher();
	
	public int login(String id, String password,int type) {
		String pwd = null;
		if(type == 1){
			pwd = student.findByStuIdToPwd(id);
		}else{
			pwd = teacher.findByTeaIdToPwd(id);
		}
		if(pwd == null){
			//user is not found
			return -1;
		}
		if(pwd.equals(password)){
			//succeed
			return 1;
		}
		//password wrong
		return 0;
	}
	// get user's idNum
	public Record getUsername(String id,int type){
		Record res = null;
		if(type == 1){
			res = student.findNameByStuId(id);
		}else{
			res = teacher.findNameByTeaId(id);
		}
		return res;
	}
	public int registerStu(String id, String password, String username){
		String pwd = student.findByStuIdToPwd(id);
		if(pwd == null){
			Boolean addStu = student.addStuInfo(id, password, username);
			if(addStu){
				// succeed: Insert data into DB successfully
 				return 1;
			}else{
				// fail: Something wrong about inserting data into DB 
				return -1;
			}
		}
		return 0;
	}
	public int registerTea(String id, String password, String username) {
		String pwd = teacher.findByTeaIdToPwd(id);
		if(pwd == null){
			Boolean addTea = teacher.addTeaInfo(id, password, username);
			if(addTea){
				return 1;
			}else{
//				System.out.println("Insert teacher info wrong");
				return -1;
			}
		}
		return 0;
	}
	
	//change password
	public Result<UserRespModel> changePwd(String id, String password, int type){
		int userRes = login(id, password, type);
		Result<UserRespModel> result = null;
		Boolean updateRes = false;
		switch(userRes){
		  case -1:
			  result = new Result<UserRespModel>(false, "用户不存在");
			  break;
		  case 1:
			  result = new Result<UserRespModel>(false, "新密码与原始密码相同");
			  break;
		  case 0:
		      if(type == 1){
				updateRes = student.updatePassword(id, password);
			  }
			  if(type == 2){
				updateRes = teacher.updatePassword(id, password);
			  }
			  if(updateRes){
				result = new Result<UserRespModel>(true);
			  }else{
				result = new Result<UserRespModel>(false, "查询数据库出错");
			  }
		}		
		return result;
	}
	// 获取用户信息
	// get user info
	public Result<UserRespModel> getUserInfo(String id, int type) {
		Record info = null;
		Result<UserRespModel> result = null;
		
		if(type == 1) {
			info = student.getUserInfo(id);
		}
		if(type == 2) {
			info = teacher.getUserInfo(id);
		}
		result = new Result<UserRespModel>(false, "查询数据出错");
		HashMap<String, Object> mes = new HashMap<String, Object>();
		if(info != null) {
			String username = info.getStr("username");
			String imgUrl = info.getStr("imgUrl");
			String email = info.getStr("email");
			mes.put("id", id);
			mes.put("username", username);
			mes.put("imgUrl", imgUrl);
			mes.put("email", email);
			result = new Result<UserRespModel>(mes);
		}
		return result;
	}
	// 修改用户信息
	// modify user info
	public Result<UserRespModel> updateUserInfo(String id, int type, String file_id, String email){
		Boolean modifyRes = false;
		Result<UserRespModel> result = null;
		
		if(type == 1) {
			modifyRes = student.modifyUserInfo(id, file_id, email);
		}
		if(type == 2) {
			modifyRes = teacher.modifyUserInfo(id, file_id, email);
		}
		result = new Result<UserRespModel>(false, "数据库出错");
		if(modifyRes) {
			result = new Result<UserRespModel>(true);
		}
		return result;
	}
}
