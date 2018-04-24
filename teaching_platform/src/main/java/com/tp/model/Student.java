package com.tp.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;


public class Student extends Model<Student>{
	public static final Student dao = new Student();
	//根据学生stu_id查找用户信息
	public Record findNameByStuId(String id){
		Record stu = Db.findFirst("select username from stu_info where id = ?",id);
		return stu;
	}
	//由学生的stu_id返回该用户的password
	public String findByStuIdToPwd(String id){
		Record stu = Db.findFirst("select password from stu_info where id = ?", id);
		if(stu != null){
			return stu.getStr("password");
		}
		return null;
	}
	// find id from stu_id
	public Integer findIdByStuId(String stuId){
		Record stu = Db.findFirst("select id from stu_info where stu_id = ?",stuId);
		if(stu != null){
			return stu.getInt("id");
		}
		return null;
	}
	//学生注册时将学生的基本信息插入表中
	public boolean addStuInfo(String stuId, String password, String username){
		Record stu = new Record().set("id", stuId).set("password", password).set("username", username);
		
		try {
			Db.save("stu_info", stu);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
    public boolean updatePassword(String stuId, String password) {
		//插入成功返回true
        return Db.update("update stu_info set password=? where id=?", password, stuId) > 0;
    }
    
    // get user info
    public Record getUserInfo(String id) {
    	Record stu = Db.findFirst("select * from stu_info where id = ?",id);
		return stu;
    }
    
    // modify user info: 修改用户信息
    public boolean modifyUserInfo(String id, String file_id, String email, String phone) {
    	// 先查看fileId存不存在（是否已经成功上传作业）
		// 先去table img_file_store中查找该fileId是否存在
    	if(file_id != null) {
    		String sql = "select url, filename from img_file_store where id = ?";
    		Record fileRes = Db.findFirst(sql, file_id);
    		String fileUrl = null; // file url
    		if(fileRes != null) {
    			fileUrl = fileRes.getStr("url");
    		}
    		if(phone != null) {
    			return Db.update("update stu_info set imgUrl=?, email = ?, phone = ? where id=?",fileUrl, email, phone, id) > 0;
    		} else {
    			return Db.update("update stu_info set imgUrl=?, email = ? where id=?",fileUrl, email, id) > 0;
    		}
    		 
    	}
    	else {
    		if(phone != null) {
    			return Db.update("update stu_info set email = ?, phone = ? where id=?", email, phone, id) > 0;
    		} else {
    			return Db.update("update stu_info set email = ? where id=?", email, id) > 0;
    		}
    	}
    }
}
