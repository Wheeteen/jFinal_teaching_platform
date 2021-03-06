package com.tp.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.CourseInfo;
import com.tp.util.Constant;


public class Course extends Model<Course> {
	public static final Course dao = new Course();
	
	// create course
	public int createCourse(String name, String intro, String tea_id, String tea_name, String class_name){
		//查询该门课程是否已经创建了
		Record courseRes = getCourseById(tea_id, name);
		if(courseRes == null) {
			Timestamp d = new Timestamp(System.currentTimeMillis()); 
			Record course = new Record().set("course_name", name).set("introduction", intro).set("tea_id", tea_id).set("tea_name", tea_name).set("create_time", d);

			try {
				Db.save("course", course);
				Integer course_id = getCourseId(tea_id, name);
				if(course_id != null && class_name != null) {
					if(class_name.indexOf(",") != -1) {
						// 代表是数组
						String str[] = class_name.split(",");
						for(String info: str) {
							Record class_info = new Record().set("course_name", name).set("tea_id", tea_id).set("tea_name", tea_name).set("course_id", course_id).set("class_name", info).set("create_time", d);
							Db.save("class_info", class_info);
						}
					} else {
						Record class_info = new Record().set("course_name", name).set("tea_id", tea_id).set("tea_name", tea_name).set("course_id", course_id).set("class_name", class_name).set("create_time", d);
						Db.save("class_info", class_info);
					}
				}
				return 1;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0; // 写入数据库出错
			}	
		}
		return -1; // 这门课已经存在了	
	}
	
	// update course introduction
	// 暂时做的只能修改introduction,不能修改课程名字
	public Boolean updateCourse(String intro, int courseId){
		//查询该门课程是否已经创建了
		Boolean courseRes = getResCourseId(courseId);
		if(!courseRes) {
			// the course is not found, return false
			return false;
		}
		else {
			Timestamp d = new Timestamp(System.currentTimeMillis()); 
			int res = Db.update("update course set introduction =? , update_time =? where course_id =?", intro, d, courseId);
			if(res>0){
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * 1. delete course
	 * 2. 同时删除 course, class_info, stu_course, task, submit_task, tp_advertise, course_file_info 里面的信息
	 */
	public int deleteCourse(int course_id, String tea_id){
		String sql = "select tea_id from  course where course_id = ?";
		String sql5 = "select class_id from class_info where course_id = ?";
		String sql1 = "delete from class_info where class_id = ?";
		String sql2 = "delete from stu_course where class_id = ?";
		String sql3 = "delete from task where class_id = ?";
		String sql4 = "delete from submit_task where class_id = ?";
		String sql6 = "delete from tp_advertise where class_id = ?";
		
		String sql7 = "delete from course_file_info where course_id = ?";
		String sql8 = "delete from course where course_id = ?";
		
		Record id_res = Db.findFirst(sql, course_id);
		
		if(id_res != null){
			String ori_tea_id = id_res.getStr("tea_id");
			if(ori_tea_id.equals(tea_id)){
				List<Record> classList = Db.find(sql5, course_id);
				if(classList.size()>0){
					for(int i = 0; i < classList.size(); i++) {
						int class_id = classList.get(i).getInt("class_id");
						Db.update(sql1, class_id);
						Db.update(sql2, class_id);
						Db.update(sql3, class_id);
						Db.update(sql4, class_id);
						Db.update(sql6, class_id);
					}
				}
				Db.update(sql7, course_id);
				Db.update(sql8, course_id);
				
				return 1; // delete success
			}
			return -1; // 不是该老师创建的班级，该老师没有权利删除班级
		}
		return -2; //course_id不存在
	}
	//query whether this course has been created by the teacher: not found is true
	public Record getCourseById(String teaId, String courseName){
//		String sql = "select c.tea_id,c.course_name from course c, tea_info t where t.id = ?";
		// every teacher could only create one identical course
		Record course = Db.findFirst("select * from course where tea_id = ? and course_name = ?",teaId, courseName);
		return course;
		// course == null, the course is not found; course != null, the course has been existed
	}
	
	//get cour_id in table course
	public Integer getCourseId(String teaId, String courseName){
		Record courseRet = getCourseById(teaId, courseName);
		if(courseRet != null){
			return courseRet.getInt("course_id");	
		}
		return null;
	}
	
	// find whether course_id existed
	public boolean getResCourseId(int courseId) {
		Record course = Db.findFirst("select course_id from course where course_id = ?",courseId);
		if(course != null) {
			return true;
		}
		return false;
	}
	// 查找一门课
	// find all the identical course
	// 班级之取出一年内创建的班级
	public List<CourseInfo> getAllCourse(String courseName){
		String sql = "select * from course where course_name like '%' ? '%' order by create_time DESC";
		String sql1 = "select class_id, class_name from class_info where course_id = ? and create_time > ?";
		List<Record> courseList = Db.find(sql, courseName);
		List<CourseInfo> listData = new ArrayList<CourseInfo>();
		
		if(courseList.size()>0){
			long now = System.currentTimeMillis();
			long validTime = now - Constant.ONE_YEAR_TTL;
			Timestamp vt = new Timestamp(validTime);
			for(Record cinfo:courseList) {
				CourseInfo courseInfo = new CourseInfo();
				int course_id = cinfo.getInt("course_id"); // 只取出最近一年内创建的该课程对应的班级
				
				courseInfo.setCourse_id(course_id);
				courseInfo.setCourse_name(cinfo.getStr("course_name"));
				courseInfo.setCreate_time(cinfo.getTimestamp("create_time"));
				courseInfo.setUpdate_time(cinfo.getTimestamp("update_time"));
				courseInfo.setTea_id(cinfo.getStr("tea_id"));
				courseInfo.setTea_name(cinfo.getStr("tea_name"));
				courseInfo.setIntroduction(cinfo.getStr("introduction"));

				List<Record> classList = Db.find(sql1, course_id, vt);
				if(classList.size()>0) {
					courseInfo.setClass_list(classList);
				}
				
				listData.add(courseInfo);
			}
			
			return listData;
		}
		return null;
	}
	
	// get all courses by particular teacher
	public List<Record> getAllCourseById(String teaId){
		String sql = "select * from course where tea_id = ? order by create_time DESC";
		List<Record> classList = Db.find(sql, teaId);
		if(classList.size()>0){
			return classList;
		}
		return null;
	}
}
