package com.tp.model;

import java.sql.Timestamp;
import java.util.List;

import org.omg.CORBA.SystemException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;


public class ClassInfo extends Model<ClassInfo> {
	public static final ClassInfo dao = new ClassInfo();
	Course course = new Course();
	
	/**
	 * create class
	 * @param course_id
	 * @param class_name
	 * @return
	 */
	public int createClass(int course_id, String class_name, String tea_id, String tea_name){
		/**
		 * 1. 查询class_name是否已经存在，不能重复
		 */
		Integer isClass = getClsName(class_name, course_id);
		if(isClass == null){
			// the class is not found, so create the class
			
			// get course_name
			String course_name = getCourseName(course_id);
			if(course_name != null) {
				Timestamp d = new Timestamp(System.currentTimeMillis()); 
				Record newCls = new Record().set("course_id", course_id).set("course_name", course_name).set("class_name", class_name).set("tea_id", tea_id).set("tea_name", tea_name).set("create_time", d);
				
				try {
					Db.save("class_info", newCls);
					return 1; // success
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return 0; // insert data is wrong
				}
			}			
			
		}
		return -1; // the class has existed
	}
	
	// delete class
	// 删除该班级的时候，要连同删除注册该班级的学生，可以之后再处理
	public int deleteClass(String clsName, int teaId, String courseName){
		// search the course info from table course
//		Record courseRet = course.getCourseById(teaId, courseName);
//		if(courseRet != null){
//			int course_id = courseRet.getInt("cour_id");
//			// judge whether the class has existed, if existed, delete; else return false
//			Boolean isClass = getClsName(clsName, course_id);
//			if(!isClass){
//				String sql = "delete from class_info where course_id = ?";
//				Boolean updateRes = Db.update(sql,course_id)>0;
//				if(updateRes) {
//					return 1; // delete success
//				}
//				return 0;//delete false,something wrong with db
//			}
//			return -1; // the class is not found
//		}
		return -2; //the course is not found
	}
	
	// judge whether the class has existed
	public Integer getClsName(String clsName, int cour_id){
		Record cls = Db.findFirst("select class_id from class_info where course_id = ? and class_name = ?",cour_id, clsName);
		if(cls == null){
			// the class not found
			return null;
		}
		// existed
		return cls.getInt("class_id");
	}
	
	// 该门课所对应的所有班级信息
	// find all the class info from the course
	public List<Record> getAllClass(int courseId){
		String sql = "select class_id, course_id, class_name, tea_id, tea_name, create_time from class_info where course_id = ? order by create_time DESC";
		List<Record> classList = Db.find(sql,courseId);
		if(classList.size()>0){
			return classList;
		}
		return null;
	}
	
	// get course_Name and class_name by id(the primary key of table class_info)
	public Record getCourseClassName(int classId){
		
		String sql = "select class_name from class_info where class_id = ?";
		Record res = Db.findFirst(sql,classId);
		if(res != null){
			return res;
		}
		return null;
	}
	
	/**
	 * 1. get course name
	 * 2. @param course_id
	 */
	public String getCourseName(int course_id){
		String sql = "select course_name from course where course_id = ?";
		Record res = Db.findFirst(sql,course_id);
		if(res != null){
			return res.getStr("course_name");
		}
		return null;
	}
}
