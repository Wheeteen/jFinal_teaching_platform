package com.tp.service;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.UserRespModel;
import com.tp.model.ClassInfo;
import com.tp.model.StuCourse;
import com.tp.util.Result;

public class AboutClassService {
	ClassInfo classInfo = new ClassInfo();
	StuCourse stuCourse = new StuCourse();
	/**
	 * createClass( for the course )
	 * @param course_id
	 * @param class_name
	 * @return
	 */
	public Result<UserRespModel> createClass(int course_id, String class_name, String tea_id, String tea_name) {
		Result<UserRespModel> result = null;
		int resCls = classInfo.createClass(course_id, class_name, tea_id, tea_name);
		switch(resCls){
			case 1:
				// search course id
				Integer classId = classInfo.getClsName(class_name, course_id);
				if(classId != null) {
					HashMap<String, Object> res_cid = new HashMap<String, Object>();
					res_cid.put("class_id", classId);
					result = new Result<UserRespModel>(res_cid); // return course_id
				} else {
					result = new Result<UserRespModel>(false, "Can't get class_id from db");
				}
				break;
			case -1:
				result = new Result<UserRespModel>(false, "The class has existed, please create another class!");
				break;
			default:
				result = new Result<UserRespModel>(false, "Something wrong with database!");
		}
		return result;
	}
	

	/**
	 * 1. delete class_info 里面的 record
	 * 2. delete这个班的所有学生：stu_info里面的信息
	 * 3. task 和 submit_task里面的记录都要删除
	 */
	public Result<UserRespModel> deleteClass(int class_id, String id) {
		Result<UserRespModel> result = null;
		int res = classInfo.deleteClass(class_id, id);
		switch (res) {
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		case -1:
			result = new Result<UserRespModel>(false, "这个班级不是该老师创建的班级，该老师没有权利删除此班级！");
			break;
		default:
			result = new Result<UserRespModel>(false, "class_id不存在");
			break;
		}
		return result;
	}
	/**
	 * getAllClass (find all the class info from the course)
	 * 寻找该门课（course_id) 所对应的所有班级信息
	 * @param teaId
	 * @param courseName
	 * @return
	 */
	public Result<UserRespModel> getAllClass(int courseId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> classList = classInfo.getAllClass(courseId);
		result = new Result<UserRespModel>(false, "The course doesn't create any classes");
		if(classList != null){
			int count = classList.size();
			userRespModel.setCount(count);
			userRespModel.setList(classList);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	
	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * 下面的是学生选课
	 */
	
	/**
	 * 1. 学生注册班级（选课）->选择对应的班级
	 * 2. 由当前用户（学生）的stu_id 和  课程id(class_id)
	 * 3. table stu_course
	 */
	public Result<UserRespModel> registerClass(String stuId, int classId, String stu_name){
	  Result<UserRespModel> result = null;
	  Boolean resCourse = stuCourse.registerClass(stuId, classId, stu_name);
	  result = new Result<UserRespModel>(false, "The student has registered the class");
		if(resCourse) {
			result = new Result<UserRespModel>(true);
		}
		return result;
	}
	
	/**
	 * 1. 判断学生是否已经选择了该门课程的 相关班级
	 * 2. judge whether the student has chosen the class of the course
	 * @param stuId
	 * @param classId
	 * @return
	 */
	public Result<UserRespModel> isChooseClass(String stuId, int classId){
		Result<UserRespModel> result = null;
		int isChooseRet = stuCourse.isChooseClass(stuId, classId);
		result = new Result<UserRespModel>(false, "The student hasn't choosen the class"); // 该班级还没有选
		if(isChooseRet == 1) {
			result = new Result<UserRespModel>(true); // 选了该班级, have chosen the class
		}
		return result;
	}
	
	/**
	 * 1. 判断学生一共注册是哪几门课程(注册了哪个班级): 判断学生注册了那些班级（该学期上了那些班级的课）
	 * 2. select all the classes that the student has choosen
	 * 3. @param: none(stu_id)
	 * 4. @method: GET
	 */
	public Result<UserRespModel> getClassByStuId(String stuId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> classList = stuCourse.getClassByStuId(stuId);
		result = new Result<UserRespModel>(false, "The student hasn't registered any class"); // 该班级还没有选
		if(classList != null) {
			int count = classList.size();
			userRespModel.setCount(count);
			userRespModel.setList(classList);
			result = new Result<UserRespModel>(userRespModel); // 选了该班级, have chosen the class
		}
		return result;
	}
	
	/**
	 * 查找一个class里面的学生有哪些？都叫什么名字呀
	 * 1. 关联stu_course和stu_info
	 * @param: class_id
	 * @method: GET
	 */
	public Result<UserRespModel> getStudentsInClass(int classId) {
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> classList = stuCourse.getStudentsInClass(classId);
		result = new Result<UserRespModel>(false, "There are no students in this class"); // 该班级没有任何学生
		if(classList != null) {
			int count = classList.size();
			userRespModel.setCount(count);
			userRespModel.setList(classList);
			result = new Result<UserRespModel>(userRespModel); // 该班级的学生
		}
		return result;
	}
}
