package com.tp.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;


public class Course extends Model<Course> {
	public static final Course dao = new Course();
	
	// create course
	public int createCourse(String name, String intro, String tea_id, String tea_name){
		//查询该门课程是否已经创建了
		Record courseRes = getCourseById(tea_id, name);
		if(courseRes == null) {
			Timestamp d = new Timestamp(System.currentTimeMillis()); 
//			long d = System.currentTimeMillis(); // 毫秒数
			System.out.println(d);
			Record course = new Record().set("course_name", name).set("introduction", intro).set("tea_id", tea_id).set("tea_name", tea_name).set("create_time", d);
			
			try {
				Db.save("course", course);
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
	public List<Record> getAllCourse(String courseName){
		String sql = "select * from course where course_name like '%' ? '%' order by create_time DESC";
		String sql1 = "select t.id as teaId, t.tea_id as accountId, t.username, t.email, c.course_id as course_id, c.course_name, c.introduction, c.create_time from course c, tea_info t where c.tea_id = t.id and c.course_name like '%' ? '%' order by c.create_time DESC";
		List<Record> classList = Db.find(sql, courseName);
		if(classList.size()>0){
			return classList;
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
