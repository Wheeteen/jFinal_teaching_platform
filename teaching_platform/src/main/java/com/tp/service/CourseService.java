package com.tp.service;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.expr.ast.Map;
import com.tp.clientModel.CourseInfo;
import com.tp.clientModel.UserRespModel;
import com.tp.model.ClassInfo;
import com.tp.model.Course;
import com.tp.model.StuCourse;
import com.tp.util.Result;

public class CourseService {
	Course course = new Course();
	ClassInfo classInfo = new ClassInfo();
	StuCourse stuCourse = new StuCourse();
	
	//create course
	public Result<UserRespModel> createCourse(String name, String intro, String tea_id, String tea_name, String class_name){
		Result<UserRespModel> result = null;
		int resCourse = course.createCourse(name, intro, tea_id, tea_name, class_name);
		result = new Result<UserRespModel>(false, "The course is existed");
		switch(resCourse){
			case 1:
				// search course id
				Integer course_id = course.getCourseId(tea_id, name);
				if(course_id != null) {
					HashMap<String, Object> res_cid = new HashMap<String, Object>();
					res_cid.put("course_id", course_id);
					result = new Result<UserRespModel>(res_cid); // return course_id
				} else {
					result = new Result<UserRespModel>(false, "Can't get course_id from db");
				}
				break;
			case -1:
				result = new Result<UserRespModel>(false, "The course is existed");
				break;
			default:
				result = new Result<UserRespModel>(false, "Something wrong with database!");
		}
		return result;
	}
	
	// update course
	// 暂时做的只能修改introduction,不能修改课程名字
	public Result<UserRespModel> updateCourse(String intro, int courseId){
		Result<UserRespModel> result = null;
		Boolean resCourse = course.updateCourse(intro, courseId);
		result = new Result<UserRespModel>(false, "The course is not existed");
		if(resCourse) {
			result = new Result<UserRespModel>(true);
		}
		return result;
	}
	
	/**
	 * 1. delete course
	 * 2. 同时删除 course, class_info, stu_course, task, submit_task, tp_advertise, course_file_info 里面的信息
	 */
	public Result<UserRespModel> deleteCourse(int course_id, String id) {
		Result<UserRespModel> result = null;
		int res = course.deleteCourse(course_id, id);
		switch (res) {
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		case -1:
			result = new Result<UserRespModel>(false, "The teacher has no right to delete the course because the course is not created by the teacher.");
			break;
		default:
			result = new Result<UserRespModel>(false, "course_id is not found");
			break;
		}
		return result;
	}
	
	// 查找一门课(根据关键字查找该门课程)
	// find all the identical course
	public Result<UserRespModel> getAllCourse(String courseName){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<CourseInfo> courseList = course.getAllCourse(courseName);
		result = new Result<UserRespModel>(false, "No course");
		if(courseList != null){
			int count = courseList.size();
			HashMap<String, Object> res_cid = new HashMap<String, Object>();
			res_cid.put("count", count);
			res_cid.put("list", courseList);
			result = new Result<UserRespModel>(res_cid);
		}
		return result;
	}
	
	// 查找该老师开设的所有课程
	public Result<UserRespModel> getAllCourseById(String teaId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> courseList = course.getAllCourseById(teaId);
		result = new Result<UserRespModel>(false, "The teacher didn't open any course");
		if(courseList != null){
			int count = courseList.size();
			userRespModel.setCount(count);
			userRespModel.setList(courseList);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
}
