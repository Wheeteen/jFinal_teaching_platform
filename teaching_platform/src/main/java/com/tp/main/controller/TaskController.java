package com.tp.main.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.tp.clientModel.UserInfo;
import com.tp.clientModel.UserReqModel;
import com.tp.clientModel.UserRespModel;
import com.tp.service.TaskService;
import com.tp.util.GetHeader;
import com.tp.util.Result;

public class TaskController extends Controller {
	
	static TaskService taskService = new TaskService();
	
	/**
	 * 1. 每一门课创建新的作业：every class create new task
	 * 2. 由班级的id： class_id 来确定是哪一个班的作业
	 * 3. 内容包括： title和content 和  file_id(可以上传文件)
	 * 4. 还有一个截止时间！end_time
	 */
	public void createTask(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
			String id = userInfo.getId();
		    String tea_name = userInfo.getName();
		    int type = userInfo.getType();
			  
		    // 只有老师才能create task
		    if(type == 2){
			  String jsonString = HttpKit.readData(getRequest());
			  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			  int classId = user.getClass_id();   // class 的 id :  班级的id
			  String title = user.getTitle();     // 作业的标题
			  String content = user.getContent(); // 作业的内容
			  String end_time = user.getEnd_time(); // 截止时间
			  
			  Result<UserRespModel> result = taskService.createTask(classId, title, content, end_time, id, tea_name);
			  renderJson(result);
		    } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to create course");
			  renderJson(json);
		   }
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 修改作业内容：content
	 * @param: task_id 、content、 file_id(文件的id)、end_time(修改交作业的截止时间)
	 * important: task_id 和  content (一定要传) ， file_id 和 end_time（可传可不传）
	 * @method: post
	 */
	public void updateTask(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		    String jsonString = HttpKit.readData(getRequest());
			UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			int task_id = user.getTask_id();   // class 的 id :  班级的id
			String content = user.getContent(); // 作业的内容
			String end_time = user.getEnd_time(); // 截止时间
			
		    Result<UserRespModel> result = taskService.updateTask(task_id, content, end_time);
		    renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 1. 删除作业(只有创建该作业的老师才能删除该作业)
	 * method: get
	 * param: task_id
	 */
	public void deleteTask() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		    String id = userInfo.getId();
		    int type = userInfo.getType();
			  
		    // 只有老师才能create task
		    if(type == 2){
		    	int task_id = getParaToInt("task_id");
			    Result<UserRespModel> result = taskService.deleteTask(task_id, id);
			    renderJson(result);
		    } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to create course");
			  renderJson(json);
		   }
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
		
	}
	/**
	 * 查询该门课所有的作业(按时间先后顺序)
	 * @param： class_id
	 */
	public void getTasksByClassId(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  Result<UserRespModel> result = taskService.getTasksByClassId(classId);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * 以下是学生的操作
	 * table: submit_task
	 */

	/**
	 * 当前用户提交作业： submit_task
	 * important: 基于每个学生每份作业提交记录只能有一个！
	 * @param: task_id、file_id
	 * 多了一个截止时间
	 * @method： POST
	 */
	public void submitTask(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String stu_id = userInfo.getId();	
		  String stu_name = userInfo.getName();
		  int type = userInfo.getType();
		  
		  // 只有学生才能submit task
		  if(type == 1){
			  String jsonString = HttpKit.readData(getRequest());
			  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			  int taskId = user.getTask_id(); // 该作业（task)的id
		      String fileId = user.getFile_id(); // 提交的文件的id(file_id)
				
			  Result<UserRespModel> result = taskService.submitTask(stu_id, taskId, fileId, stu_name);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a teacher,he has no need to submit task");
			  renderJson(json);
		  }
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 1. 查看学生（当前用户）提交了多少次作业？ （查找某一门课该学生提交的所有作业）
	 * 2. search all the tasks the student has submitted in one class
	 * 3. @param: classId
	 * 
	 */
	public void getStuSubTasks() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String stu_id = userInfo.getId();
		  int classId = getParaToInt("class_id");
		  
		  Result<UserRespModel> result = taskService.getAllStuTask(classId, stu_id);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 1. 查看学生（当前用户）有哪次作业还没有提交呢？ （查找学生某一门课还没有提交的作业的content和title）
	 * 2. search all the tasks the student has submitted in one class
	 * 3. @param: classId
	 * 
	 */
	public void stuNotSubTask(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String stu_id = userInfo.getId();
		  int classId = getParaToInt("class_id");
		  
		  Result<UserRespModel> result = taskService.stuNotSubTask(classId, stu_id);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 1. 老师查看一个班级有多少学生提交了该门课的作业（比如说第一次作业有多少学生提交了，第二次作业有多少学生提交了）
	 * 2. @param: class_id 和 task_id
	 * 3. @method: get
	 */
	public void teaGetSubTask() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  int taskId = getParaToInt("task_id");
		  
		  Result<UserRespModel> result = taskService.getSubmitTask(classId, taskId);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 1. 老师查看一个班级还有多少学生没交该门课的作业（比如说第一次作业有多少学生没提交了，第二次作业有多少学生没提交了）
	 * 2. @param: class_id 和 task_id
	 * 3. @method: get
	 */
	public void teaGetNotSubTask(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  int taskId = getParaToInt("task_id");
		  
		  Result<JSONObject> result = taskService.teaGetNotSubTask(classId, taskId);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 1. 老师对作业进行评分
	 * 2. 提交的是该submit_task中的id(primary key) 和 grade(int)
	 */
	public void setGrade() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		    String jsonString = HttpKit.readData(getRequest());
			UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			int submit_tid = user.getSubmit_tid();   // submit_task中的id(primary key)
			int grade = user.getGrade(); // 作业的分数
			String remark = user.getRemark(); // 作业的评语（可填可不填）
			
		    Result<UserRespModel> result = taskService.setGrade(submit_tid, grade, remark);
		    renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 查看该课程某次作业的成绩的分数（10，20，30）
	 * 没用
	 */
	public void getTaskGrade() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  int taskId = getParaToInt("task_id");
		  
		  Result<UserRespModel> result = taskService.getRealGrade(classId, taskId);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 查看该课程所有作业的评分情况（10，20，30）
	 * 没用
	 * 以task作为分界线
	 */
	public void getClassGrade() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  
		  Result<JSONObject> result = taskService.getClassGrade(classId);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	/**
	 * 1. 老师查看某一次作业 有哪些作业已经评分了呢？查看已经评过分的作业
	 * 2. class_id 和 task_id
	 */
	public void teaGetSetGrade() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  int taskId = getParaToInt("task_id");
		  
		  Result<UserRespModel> result = taskService.teaGetSetGrade(classId, taskId);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
	
	/**
	 * 1. 老师查看某一次作业 有哪些作业已经评分了呢？查看还没有评过分的作业
	 * 2. class_id 和 task_id
	 */
	public void teaGetNotGrade() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  int taskId = getParaToInt("task_id");
		  
		  Result<UserRespModel> result = taskService.teaGetNotGrade(classId, taskId);
		  renderJson(result);
		}
		else {
			JSONObject jsonObject = new JSONObject();
			  jsonObject.put("token", false);
			  jsonObject.put("error", userInfo.getError());
			  renderJson(jsonObject);
		}
	}
}
