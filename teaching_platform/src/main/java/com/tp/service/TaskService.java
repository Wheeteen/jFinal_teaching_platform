package com.tp.service;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
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
	public Result<UserRespModel> createTask(int classId, String title, String content, String file_id, String end_time,String tea_id, String tea_name) {
		Result<UserRespModel> result = null;
		int taskRes = task.createTask(classId, title, content, file_id, end_time, tea_id, tea_name);
		switch (taskRes) {
		case 0:
			result = new Result<UserRespModel>(false, "插入数据出错！");
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
			result = new Result<UserRespModel>(false, "该作业已经创建过了！请重新创建一个作业title不一样的作业");
			break;
		}
		return result;
	}
	
	public Result<UserRespModel> updateTask(int task_id, String content, String file_id, String end_time){
		Result<UserRespModel> result = null;
		int res = task.updateTask(task_id, content, file_id, end_time);
		switch (res) {
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		case 0:
			result = new Result<UserRespModel>(false, "更新数据出错");
			break;
		default:
			result = new Result<UserRespModel>(false, "The task_id is not existed");
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

		result = new Result<UserRespModel>(false, "这门课暂时还没有任务");
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
			result = new Result<UserRespModel>(false, "插入数据出错！");
			break;
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		case -1:
			result = new Result<UserRespModel>(false, "The task_id is not found");
			break;
		default:
			result = new Result<UserRespModel>(false, "提交作业的时间过了！");
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
		result = new Result<UserRespModel>(false, "该学生还没有提交任何作业");
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
		result = new Result<UserRespModel>(false, "该学生已提交完所有作业");
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
		result = new Result<UserRespModel>(false, "还没有学生提交了作业");
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
		result = new Result<UserRespModel>(false, "已交作业均已评分");
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
		result = new Result<UserRespModel>(false, "已交作业都评分了！");
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
	public Result<UserRespModel> teaGetNotSubTask(int classId, int taskId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = task.teaGetNotSubTask(classId, taskId);
		result = new Result<UserRespModel>(false, "所有有学生都提交了作业");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setList(taskList);
			userRespModel.setCount(count);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	/**
	 * 1. 老师对作业进行评分
	 * 2. 提交的是该submit_task中的id(primary key) 和 grade(int)
	 */
	public Result<UserRespModel> setGrade(int id, int grade) {
		Result<UserRespModel> result = null;
		Boolean res = task.setGrade(id, grade);
		result = new Result<UserRespModel>(false, "该作业的id不存在");
		if(res){
			result = new Result<UserRespModel>(true);	
		}
		return result;
	}
}
