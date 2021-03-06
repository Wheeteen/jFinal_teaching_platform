package com.tp.service;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.GradeInfo;
import com.tp.clientModel.StuTaskInfo;
import com.tp.clientModel.UserRespModel;
import com.tp.model.Task;
import com.tp.util.Result;

public class TaskService {
	Task task = new Task();

	/**
	 * 每一门课创建新的作业
	 * @param classId
	 * @param title
	 * @param content
	 * @return
	 */
	public Result<UserRespModel> createTask(int classId, String title, String content, long end_time,String tea_id, String tea_name) {
		Result<UserRespModel> result = null;
		int taskRes = task.createTask(classId, title, content, end_time, tea_id, tea_name);
		switch (taskRes) {
		case 0:
			result = new Result<UserRespModel>(false, "something wrong about inserting data into db.");
			break;
		case 1:
			// search task_id
			Integer task_id = task.getTaskId(classId, title);
			if(task_id != null) {
				HashMap<String, Object> res_cid = new HashMap<String, Object>();
				res_cid.put("task_id", task_id);
				result = new Result<UserRespModel>(res_cid); // return course_id
			} else {
				result = new Result<UserRespModel>(false, "Can't get task_id from db");
			}
			break;
		default:
			result = new Result<UserRespModel>(false, "The task has been created, please create another task which title is different from that one.");
			break;
		}
		return result;
	}
	
	public Result<UserRespModel> updateTask(int task_id, String content, long end_time){
		Result<UserRespModel> result = null;
		int res = task.updateTask(task_id, content, end_time);
		switch (res) {
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		case 0:
			result = new Result<UserRespModel>(false, "DB wrong");
			break;
		default:
			result = new Result<UserRespModel>(false, "The task_id is not existed");
			break;
		}
		return result;
	}
	/**
	 * 1. 删除作业
	 * method: get
	 * param: task_id
	 */
	public Result<UserRespModel> deleteTask(int task_id, String id) {
		Result<UserRespModel> result = null;
		int res = task.deleteTask(task_id, id);
		switch (res) {
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		case 0:
			result = new Result<UserRespModel>(false, "DB wrong");
			break;
		case -1:
			result = new Result<UserRespModel>(false, "The teacher has no right to delete the task because the task was not created by the teacher.");
			break;
		default:
			result = new Result<UserRespModel>(false, "task_id is not found");
			break;
		}
		return result;
	}
	// 查找该班级下该老师发布的所有作业
	// search all the tasks in that class by the teacher
	public Result<UserRespModel> getTasksByClassId(int classId) {
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = task.getAllTaskByClassId(classId);

		result = new Result<UserRespModel>(false, "There is no task about this class");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setCount(count);
			userRespModel.setList(taskList);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	public Result<UserRespModel> submitTask(String stuId, int taskId, String fileId, String stu_name){
		Result<UserRespModel> result = null;
		int submitRes = task.submitTask(stuId, taskId, fileId, stu_name);
		switch(submitRes){
		case 0:
			result = new Result<UserRespModel>(false, "DB wrong！");
			break;
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		case -1:
			result = new Result<UserRespModel>(false, "The task_id is not found");
			break;
		default:
			result = new Result<UserRespModel>(false, "The task is expired!");
			break;
		}
		return result;
	}
	
	// search all the tasks the student had submitted in that class
	// 查找某一门课该学生提交的所有作业
	public Result<UserRespModel> getAllStuTask(int classId, String studentId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = task.getAllStuTask(classId, studentId);
		result = new Result<UserRespModel>(false, "The student hasn't submitted any tasks");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setCount(count);
			userRespModel.setList(taskList);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	/**
	 * 1. 查看学生（当前用户）有哪次作业还没有提交呢？ （查找学生某一门课还没有提交的作业的content和title）
	 * 2. search all the tasks the student has submitted in one class
	 * 3. @param: classId、studentId
	 * 
	 */
	public Result<UserRespModel> stuNotSubTask(int classId, String studentId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = task.stuNotSubTask(classId, studentId);
		result = new Result<UserRespModel>(false, "The student has submitted all the task.");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setCount(count);
			userRespModel.setList(taskList);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	/**
	 * 1. 一个班级中有多少学生提交了该门课的作业
	 * @param classId
	 * @param taskId
	 * @return
	 */
	public Result<UserRespModel> getSubmitTask(int classId, int taskId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = task.getSubmitTask(classId, taskId);
		result = new Result<UserRespModel>(false, "No student submit the task");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setList(taskList);
			userRespModel.setCount(count);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	/**
	 * 1. 老师查看某一次作业 有哪些作业已经评分了呢？查看已经评过分的作业
	 * @param classId
	 * @param taskId
	 * @return
	 */
	public Result<UserRespModel> teaGetSetGrade(int classId, int taskId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = task.teaGetSetGrade(classId, taskId);
		result = new Result<UserRespModel>(false, "All tasks have been graded.");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setList(taskList);
			userRespModel.setCount(count);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	/**
	 * 1. 老师查看某一次作业 有哪些作业已经评分了呢？查看还没有评过分的作业
	 * @param classId
	 * @param taskId
	 * @return
	 */
	public Result<UserRespModel> teaGetNotGrade(int classId, int taskId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = task.teaGetNotGrade(classId, taskId);
		result = new Result<UserRespModel>(false, "All tasks have been graded.");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setList(taskList);
			userRespModel.setCount(count);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	/**
	 * 1. 老师查看一个班级还有多少学生没交该门课的作业（比如说第一次作业有多少学生没提交了，第二次作业有多少学生没提交了）
	 * 2. @param: class_id 和 task_id
	 * 3. @method: get
	 */
	public Result<JSONObject> teaGetNotSubTask(int classId, int taskId){
		Result<JSONObject> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<StuTaskInfo> taskList = task.teaGetNotSubTask(classId, taskId);
		result = new Result<JSONObject>(false, "All students have submitted the task");
		if(taskList != null){
			int count = taskList.size();
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("list", taskList);
			jsonObject.put("count", count);
			result = new Result<JSONObject>(jsonObject);
		}
		return result;
	}
	
	/**
	 * 1. 老师对作业进行评分
	 * 2. 提交的是该submit_task中的id(primary key) 和 grade(int)
	 */
	public Result<UserRespModel> setGrade(int id, int grade, String remark) {
		Result<UserRespModel> result = null;
		Boolean res = task.setGrade(id, grade, remark);
		result = new Result<UserRespModel>(false, "submit_tid is not found");
		if(res){
			result = new Result<UserRespModel>(true);	
		}
		return result;
	}
	
	/**
	 * 查看该课程某次作业的成绩的分数（10，20，30）
	 */
	public Result<UserRespModel> getRealGrade(int class_id, int task_id) {
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = task.getRealGrade(class_id, task_id);
		result = new Result<UserRespModel>(false, "No task has been graded");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setList(taskList);
			userRespModel.setCount(count);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	/**
	 * 查看该课程所有作业的评分情况（10，20，30）
	 * 以task作为分界线
	 */
	public Result<JSONObject> getClassGrade(int class_id) {
		Result<JSONObject> result = null;
		List<GradeInfo> taskList = task.getClassGrade(class_id);
		result = new Result<JSONObject>(false, "There is no task assigned under this class.");
		if(taskList != null){
			int count = taskList.size();
			JSONObject json = new JSONObject();
			json.put("list", taskList);
			json.put("count", count);
			result = new Result<JSONObject>(json);
		}
		return result;
	}
}
