package com.tp.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.SystemException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.CourseInfo;
import com.tp.clientModel.NoticeInfo;
import com.tp.clientModel.UserRespModel;
import com.tp.util.Constant;
import com.tp.util.Result;

public class Notice extends Model<Notice> {
	StuCourse stuCourse = new StuCourse();
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
	public int createNotice(int classId, String title, String content, String tea_id, String tea_name) {	
		// 通过class_id 和 title来查询该用户是否已经创建过同样title的公告了
		Integer notice_id = getNoticeId(classId, title);
		if(notice_id == null) {
			Timestamp d = new Timestamp(System.currentTimeMillis()); 
			Record rNotice = new Record().set("class_id", classId).set("title", title).set("tea_id", tea_id).set("tea_name", tea_name).set("content", content).set("create_time", d);		
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
	
	public int isNoticeId(int notice_id) {
		String sql = "select notice_id from tp_advertise where notice_id = ?";
		Record res = Db.findFirst(sql, notice_id);
		if(res != null) { // notice_id存在
			return 1;
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
	public int updateNotice(int notice_id, String content){
		//查询该notice_id是否存在
		int resId = isNoticeId(notice_id);
		if(resId == 1) {
			return Db.update("update tp_advertise set content =? where notice_id =?", content, notice_id)>0 ? 1 : 0; // 1表示更新成功， 2表示更新失败
		}
		return -1;
		
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
	
	/**
	 * 学生首页，要显示最近五条公告，来自不同课程
	 */
	public List<NoticeInfo> getFiveStuNotice(String stu_id) {
		// 由stu_id来查询该学生选了什么课
		String sql2 = "select a.class_id, a.class_name, b.course_id, b.course_name, b.tea_id, b.tea_name from stu_course a, class_info b where a.stu_id = ? and a.class_id = b.class_id order by a.create_time DESC";
		List<Record> classList = Db.find(sql2, stu_id);
		if(classList != null) {
			List<NoticeInfo> listData = new ArrayList<NoticeInfo>();
			int count = classList.size();
			String sql = "select * from tp_advertise where class_id =? order by create_time DESC limit ?";
			for(Record info: classList) {
				NoticeInfo noticeInfo = new NoticeInfo();
				
				int class_id = info.getInt("class_id");
				
				int course_id = info.getInt("course_id");
				String class_name = info.getStr("class_name");
				String course_name = info.getStr("course_name");
				List<Record> noticeList = null;
				
				switch (count) {
				case 1:
					noticeList = Db.find(sql, class_id, 5);
					break;
				case 2:
					noticeList = Db.find(sql, class_id, 3);
					break;
				case 3:
					noticeList = Db.find(sql, class_id, 2);
					break;
				default:
					noticeList = Db.find(sql, class_id, 1);
					break;
				}
				if(noticeList.size()>0) {
					for(Record ninfo: noticeList){
						noticeInfo.setCourse_id(course_id);
						noticeInfo.setClass_name(class_name);
						noticeInfo.setCourse_name(course_name);
						noticeInfo.setClass_id(class_id);
						noticeInfo.setContent(ninfo.getStr("content"));
						noticeInfo.setNotice_id(ninfo.getInt("notice_id"));
						noticeInfo.setTea_id(ninfo.getStr("tea_id"));
						noticeInfo.setTitle(ninfo.getStr("title"));
						noticeInfo.setTea_name(ninfo.getStr("tea_name"));
						noticeInfo.setCreate_time(ninfo.getTimestamp("create_time"));
						
						listData.add(noticeInfo);
					}
				}
				
			}
			return listData;
		}
		return null;
	}
	/**
	 * 老师首页，显示老师发布的最新的10条公告
	 */
	public List<Record> getFiveTeaNotice(String tea_id) {
		String sql = "select * from tp_advertise where tea_id = ? order by create_time DESC limit 10";
		List<Record> listData = Db.find(sql, tea_id);
		if(listData.size()>0){
			return listData;
		}
		return null;
	}
}
