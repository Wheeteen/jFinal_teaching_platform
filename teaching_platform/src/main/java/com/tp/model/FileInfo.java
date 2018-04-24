package com.tp.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Const;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.tp.util.Constant;
import com.tp.util.getUUID;

public class FileInfo extends Model<FileInfo> {
	// 将文件名写入数据库
	public String upload(String fileUrl, String filename){

		Timestamp d = new Timestamp(System.currentTimeMillis()); 
		String id = getUUID.getUUID();
		Record upload = new Record().set("id", id).set("url", fileUrl).set("filename", filename).set("create_time", d);
		try {
			Db.save("img_file_store", upload);
			return id;
//			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}	
	}
	
	// 老师上传文件到某一门课程中(course_file_info)
	public int teaUploadFile(int courseId, String fileId, String title, String intro, String tea_id, String tea_name){
		// 先去table img_file_store中查找该fileId是否存在
		String sql = "select url, filename from img_file_store where id = ?";
		Record fileRes = Db.findFirst(sql, fileId);
		if(fileRes != null){
			// url
			String fileUrl = fileRes.getStr("url");
			// filename
			String filename = fileRes.getStr("filename");
			// 存在，所以写入table course_file_info中
			Timestamp d = new Timestamp(System.currentTimeMillis()); 
			Record upload = null;
			if(intro != null) {
				upload = new Record().set("course_id", courseId).set("title", title).set("introduction", intro).set("file_id", fileId).set("url", fileUrl).set("filename", filename).set("tea_id", tea_id).set("tea_name", tea_name).set("create_time", d);
			} else {
				upload = new Record().set("course_id", courseId).set("title", title).set("file_id", fileId).set("url", fileUrl).set("filename", filename).set("tea_id", tea_id).set("tea_name", tea_name).set("create_time", d);
			}
			try {
				Db.save("course_file_info", upload);
				return 1;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}	
		}
		// fileId不存在
		return -1;
	}
	// 取出该门课的所有文件(by teaId and course_name)
	public List<Record> getFilenameByTea(String teaId, String course_name){
		Record res = Db.findFirst("select course_id from course where tea_id = ? and course_name = ?",teaId, course_name);
		if(res != null) {
			int course_id = res.getInt("course_id");
			List<Record> fileList = getFileByCourseId(course_id);
			return fileList;
		}
		return null;
	}
	
	// 取出该门课的所有文件（by courseId)
	public List<Record> getFileByCourseId(int courseId) {
		String sql = "select * from course_file_info where course_id = ? and create_time > ? order by create_time DESC";
		long now = System.currentTimeMillis();
		long validTime = now - Constant.TWO_MONTH_TTL;
		Timestamp vt = new Timestamp(validTime);
		List<Record> fileList = Db.find(sql,courseId, vt);
		if(fileList.size()>0){
			return fileList;
		}
		return null;
	}
}
