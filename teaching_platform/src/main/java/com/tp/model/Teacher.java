package com.tp.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;


public class Teacher extends Model<Teacher> {
	public static final Teacher dao = new Teacher();
	
	// find id from stu_id
	public Integer findIdByTeaId(String teaId){
		Record tea = Db.findFirst("select id from tea_info where tea_id = ?",teaId);
		if(tea != null){
			return tea.getInt("id");
		}
		return null;
	}
	//根据学生tea_id查找用户信息
	public Record findNameByTeaId(String id){
		Record tea = Db.findFirst("select username from tea_info where id = ?", id);
		return tea;
	}
	//由学生的stu_id返回该用户的password
	public String findByTeaIdToPwd(String teaId){
		Record tea = Db.findFirst("select password from tea_info where id = ?",teaId);
		if(tea != null){
			return tea.getStr("password");
		}
		return null;
	}
	//学生注册时将学生的基本信息插入表中
	public boolean addTeaInfo(String teaId, String password, String username){
		Record tea = new Record().set("id", teaId).set("password", password).set("username", username);
		
		try {
			Db.save("tea_info", tea);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
    public boolean updatePassword(String teaId, String password) {
		//插入成功返回true
        return Db.update("update tea_info set password=? where id=?", password, teaId) > 0;
    }
    
    // get user info
    public Record getUserInfo(String id) {
    	Record tea = Db.findFirst("select * from tea_info where id = ?",id);
		return tea;
    }
    
    // modify user info: 修改用户信息
    public boolean modifyUserInfo(String id, String file_id, String email) {
    	// 先查看fileId存不存在（是否已经成功上传作业）
		// 先去table img_file_store中查找该fileId是否存在
    	if(file_id != null) {
    		String sql = "select url, filename from img_file_store where id = ?";
    		Record fileRes = Db.findFirst(sql, file_id);
    		String fileUrl = null; // file url
    		if(fileRes != null) {
    			fileUrl = fileRes.getStr("url");
    		}
    		 return Db.update("update tea_info set imgUrl=?, email = ? where id=?",fileUrl, email, id) > 0;
    	}
    	else {
    		return Db.update("update tea_info set email = ? where id=?", email, id) > 0;
    	}
    }
}
