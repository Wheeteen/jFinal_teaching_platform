package com.tp.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.tp.clientModel.UserInfo;
import com.tp.clientModel.UserReqModel;
import com.tp.clientModel.UserRespModel;
import com.tp.interceptor.LoginInterceptor;
import com.tp.service.CourseService;
import com.tp.util.GetHeader;
import com.tp.util.Result;

//@Before(LoginInterceptor.class)
public class CourseController extends Controller {
	static CourseService service = new CourseService();
	
	/**
	 * 1. teacher create course,return --> cour_id(课程id)
	 * important: 老师开设课程
	 * 2. 可以使用 Map<String, String> map = new HashMap<>(); // key--value
	 */
	public void createCourse(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String id = userInfo.getId();
		  String tea_name = userInfo.getName();
		  int type = userInfo.getType();
		  
		  // 只有老师才能create course
		  if(type == 2){
			  String jsonString = HttpKit.readData(getRequest());
			  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			  String courseName = user.getCourse_name();
			  String courseIntro = user.getCourse_intro();
			  String class_name = user.getClass_name();
			  Result<UserRespModel> result = service.createCourse(courseName, courseIntro, id, tea_name, class_name);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to create course");
			  renderJson(json);
		  }
		}
		else {
		  renderJson(userInfo.getError());
		}

	}
	
	/**
	 * 1. teacher update course info: 通过course_id来查询数据库信息，Update该条记录
	 * 2. 暂时做的只能修改introduction,不能修改课程名字
	 */
	public void updateCourse(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int type = userInfo.getType();
		  
		  // 只有老师才能update course
		  if(type == 2){
			  String jsonString = HttpKit.readData(getRequest());
			  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			  int courseId = user.getCourse_id();
			  String courseIntro = user.getCourse_intro();
			  Result<UserRespModel> result = service.updateCourse(courseIntro, courseId);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to update course");
			  renderJson(json);
		  }
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	/**
	 * 1. delete course
	 * 2. 同时删除 course, class_info, stu_course, task, submit_task, tp_advertise, course_file_info 里面的信息
	 */
	public void deleteCourse(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		    String id = userInfo.getId();
		    int type = userInfo.getType();
			  
		    // 只有老师才能create task
		    if(type == 2){
		    	int course_id = getParaToInt("course_id");
		    	Result<UserRespModel> result = service.deleteCourse(course_id, id);
			    renderJson(result);
		    } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to delete course");
			  renderJson(json);
		   }
		}
		else {
		  renderJson(userInfo.getError());
		}
		
	}
	

	/**
	 *  通过搜索“course_name” --> 查询与之相关的课程
	 * 1. 模糊查询一门课： find all the identical course
	 * 2. 由 course_name 来查询
	 * 3. 返回该门课所有的信息： 包含 table course 和 table tea_info
	 * 4. 所有角色都可以查询
	 * 5. method: GET
	 * 
	 */
	public void searchAllCourse(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String courseName = getPara("course_name");
		  Result<UserRespModel> result = service.getAllCourse(courseName);
		  renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	
	/**
	 *  查找当前用户（老师）开设的所有课程(find all courses opening only by the target teacher)
	 * 1. @param：none(当前用户，什么都不用传）
	 */
	public void getCoursesByCurrentId(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String id = userInfo.getId();
		  int type = userInfo.getType();

		  // 查当前用户的，只能是老师
		  if(type == 2){
			  Result<UserRespModel> result = service.getAllCourseById(id);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to update course");
			  renderJson(json);
		  }
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	
	/**
	 *  查找“某一个老师” 开设的所有课程(find all courses opening only by the target teacher)
	 * 1. @param：id(传的是老师在数据表生成的id,primary key）
	 */
	public void getCoursesById(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String teaId = getPara("tea_id");
		  Result<UserRespModel> result = service.getAllCourseById(teaId);
		  renderJson(result);
		} else {
		  renderJson(userInfo.getError());
		}
	}
	
}
