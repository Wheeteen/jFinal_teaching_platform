package com.tp.model;

import java.sql.Timestamp;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.UserRespModel;
import com.tp.util.Constant;
import com.tp.util.Result;

public class Notice extends Model<Notice> {
	
	/**
	 * 发布公告
	 * @param classId
	 * @param title
	 * @param content
	 * @param file_id
	 * @param tea_id
	 * @param tea_name
	 * @return
	 */
	public int createNotice(int classId, String title, String content, String file_id, String tea_id, String tea_name) {

		/**
		 * 1. 如果fileId不为null（是否已经成功上传作业）
		 * 2. 先去table img_file_store中查找该fileId是否存在
		 */
		String fileUrl = null; // file url
		String filename = null; // filename
		if(file_id != null) {
			String sql = "select url, filename from img_file_store where id = ?";
			Record fileRes = Db.findFirst(sql, file_id);	
			if(fileRes != null) {
				fileUrl = fileRes.getStr("url");
				filename = fileRes.getStr("filename");
			}
		}
		
		// 通过class_id 和 title来查询该用户是否已经创建过同样title的公告了
		Integer notice_id = getNoticeId(classId, title);
		if(notice_id == null) {
			Timestamp d = new Timestamp(System.currentTimeMillis()); 
			Record rNotice = new Record().set("class_id", classId).set("title", title).set("tea_id", tea_id).set("tea_name", tea_name).set("content", content).set("file_id", file_id).set("filename", filename).set("url", fileUrl).set("create_time", d);		
			try {
				Db.save("tp_advertise", rNotice);
				return 1;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0; // 插入数据库出错
			}	
		} else {
			return -1;
		}
		
	}
	
	/**
	 * 获取notice_id, 通过class_id 和 title 唯一辨别该notice_id
	 * @param class_id
	 * @param title
	 * @return
	 */
	public Integer getNoticeId(int class_id, String title) {
		String sql = "select notice_id from tp_advertise where class_id = ? and title = ?";
		Record res = Db.findFirst(sql, class_id, title);
		if(res != null) {
			return res.getInt("notice_id");
		}
		return null;
	}
	
	public int isNoticeId(int notice_id, String file_id) {
		String sql = "select notice_id, file_id from tp_advertise where notice_id = ?";
		Record res = Db.findFirst(sql, notice_id);
		if(res.getInt("notice_id") != null) { // notice_id存在
			if(file_id == null) {
				return 0; // 这时没有传文件，只是更新content即可
			} else {
				// 传了文件
				String ori_file_id = res.getStr("file_id");
				if(file_id.equals(ori_file_id)) {
					return 0; // 传了文件， 但是跟原来的一样，不用update file_id这些
				} else {
					return 1; // 传了文件， file_id与原来的不相等， 要update file_id  filename这些信息
				}
			}
		}
		return -1; // notice_id不存在
	}
	/**
	 * 更新公告
	 * @param notice_id
	 * @param content
	 * @param file_id
	 * @return
	 */
	public int updateNotice(int notice_id, String content, String file_id){
		//查询该notice_id是否存在
		int resId = isNoticeId(notice_id, file_id);
		switch (resId) {
		case 0:
			return Db.update("update tp_advertise set content =? where notice_id =?", content, notice_id)>0 ? 1 : 0; // 1表示更新成功， 2表示更新失败
		case 1:
			String sql = "select url, filename from img_file_store where id = ?";
			Record fileRes = Db.findFirst(sql, file_id);	
			String fileUrl = null; // file url
			String filename = null; // filename
			if(fileRes != null) {
				fileUrl = fileRes.getStr("url");
				filename = fileRes.getStr("filename");
			}
			return Db.update("update tp_advertise set content =?, file_id = ?, filename = ?, url = ? where notice_id =?", content, file_id, filename, fileUrl, notice_id)>0 ? 1 : 0;
		default:
			return -1; // notice_id不存在
		}	
	}
	/**
	 * 1. 删除公告
	 * @param: notice_id
	 */
	public int deleteNotice(int notice_id) {
		String sql = "delete from tp_advertise where notice_id = ?";
		Boolean updateRes = Db.update(sql,notice_id)>0;
		if(updateRes) {
			return 1; // delete success
		}
		return 0;//delete false,something wrong with db
	}
	/**
	 * 查询该门课所有的公告(按时间先后顺序)
	 * 只取出最近两个月内发布的公告
	 * @param： class_id
	 */
	public List<Record> getNoticeByClassId(int classId){
		String sql = "select * from tp_advertise where class_id =? and create_time > ? order by create_time DESC";
		long now = System.currentTimeMillis();
		long validTime = now - Constant.TWO_MONTH_TTL;
		Timestamp vt = new Timestamp(validTime);
		List<Record> taskList = Db.find(sql, classId, vt);
		if(taskList.size() > 0) {
			return taskList;
		}
		return null;
	}
}
