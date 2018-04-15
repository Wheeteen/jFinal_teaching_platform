package com.tp.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.tp.clientModel.UserInfo;
import com.tp.clientModel.UserReqModel;
import com.tp.clientModel.UserRespModel;
import com.tp.service.AboutClassService;
import com.tp.util.GetHeader;
import com.tp.util.Result;

public class AboutClassController extends Controller {
	static AboutClassService service = new AboutClassService();
	
	/**
	 * 1. teacher create class: 由课程id来创建班级(cour_id)
	 * 2. 传 course_id + class_name(班级名字)过来
	 */
	public void createClass(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		   String id = userInfo.getId();
		   String tea_name = userInfo.getName();
		   int type = userInfo.getType();
		  
		  // 只有老师才能create class
		  if(type == 2){
			  String jsonString = HttpKit.readData(getRequest());
			  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			  int course_id = user.getCourse_id(); // course_id
			  String class_name = user.getClass_name(); // class_name
			  Result<UserRespModel> result = service.createClass(course_id, class_name, id, tea_name);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to create class");
			  renderJson(json);
		  }
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	/**
	 * updateClass: 更新班级信息：班级名字
	 * 理论上来说 班级名字 是无法更改的
	 */
	public void updateClass(){
		// 若以后需要再加上
	}
	
	/**
	 * 1. delete class_info 里面的 record
	 * 2. delete这个班的所有学生：stu_info里面的信息
	 * 3. task 和 submit_task里面的记录都要删除
	 */
	public void deleteClass(){
		
	}
	
	
	/**
	 * 1. 查询一门课 下 创建的所有班级信息 (find all the class info from the course)
	 * 2. 比方说： 由course_id来查询
	 * 3. class_info 和  course 两表关联 查询所有信息
	 * 4. 老师和学生都能查询
	 */
	public void getAllClass(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){  
		  int course_id = getParaToInt("course_id"); // course_id
		  
		  Result<UserRespModel> result = service.getAllClass(course_id);
		  renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	
	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * 下面的是学生选课
	 */
	
	/**
	 * 1. 学生注册班级（选课）->选择对应的班级
	 * 2. 由当前用户（学生）的stu_id 和  课程id(class_id)
	 */
	public void registerClass(){
		
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String stu_id = userInfo.getId();	
		  String stu_name = userInfo.getName();
		  int type = userInfo.getType();
		  
		  // 只有学生才能register class
		  if(type == 1){
			  String jsonString = HttpKit.readData(getRequest());
			  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			  int classId = user.getClass_id();   // classId

			  Result<UserRespModel> result = service.registerClass(stu_id, classId, stu_name);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a teacher,he has no access to register class");
			  renderJson(json);
		  }
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	/**
	 * 1. 判断当前用户（学生）是否已经选择了该门课程的 下的相关班级
	 * 2. judge whether the student has chosen the class of the course
	 * 3. @param: class_id
	 * 4. @method: GET
	 */
	public void isChooseClass(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String stu_id = userInfo.getId();	
		  int type = userInfo.getType();
		  
		  // 只有学生再查询是否已经注册了 该 class，否则不浪费功夫
		  if(type == 1){
			  int classId = getParaToInt("class_id");
			  Result<UserRespModel> result = service.isChooseClass(stu_id, classId);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a teacher,he has no access to register class");
			  renderJson(json);
		  }
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	/**
	 * 1. 判断学生一共注册是哪几门课程(注册了哪个班级): 判断学生注册了那些班级（该学期上了那些班级的课）
	 * 2. select all the classes that the student has choosen
	 * 3. @param: none(stu_id)
	 * 4. @method: GET
	 */
	public void getClassByStuId(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String stu_id = userInfo.getId();	
		  int type = userInfo.getType();
		  
		  // 只有学生再查询是否已经注册了 该 class，否则不浪费功夫
		  if(type == 1){
			  Result<UserRespModel> result = service.getClassByStuId(stu_id);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a teacher,he has no access to register class");
			  renderJson(json);
		  }
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	/**
	 * 查找一个class里面的学生有哪些？都叫什么名字呀
	 * 1. 关联stu_course和stu_info
	 * @param: class_id
	 * @method: GET
	 */
	public void getStudentsInClass() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  Result<UserRespModel> result = service.getStudentsInClass(classId);
		  renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
}
