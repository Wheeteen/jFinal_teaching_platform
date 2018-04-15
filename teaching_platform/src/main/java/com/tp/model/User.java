package com.tp.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.tp.model.base.BaseUser;

public class User extends BaseUser<User> {
	public static final User dao = new User();
	
	//根据学生stu_id查找用户信息
	public User findByUserId(int userId){
		Record user = Db.findFirst("select * from stu_info where stu_id = ?",userId);
		return null;
	}
}
