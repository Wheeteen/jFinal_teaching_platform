package com.tp.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.UserRespModel;
import com.tp.util.Result;

public class Task extends Model<Task> {
	
	/**
	 * 每一门课创建新的作业(table task)
	 * important: 由title和class_id来唯一确定老师创建的每一次作业都是独一无二的
	 * @param classId
	 * @param title
	 * @param content
	 * @return
	 */
	public int createTask(int classId, String title, String content, String file_id, String end_time, String tea_id, String tea_name) {
		/**
		 * 1. 如果fileId不为null（是否已经成功上传作业）
		 * 2. 先去table img_file_store中查找该fileId是否存在
		 */
		String fileUrl = null; // file url
		String filename = null; // filename
		if(file_id != null) {
			String sql = "select url, filename from img_file_store where id = ?";
			Record fileRes = Db.findFirst(sql, file_id);	
			if(fileRes != null) {
				fileUrl = fileRes.getStr("url");
				filename = fileRes.getStr("filename");
			}
		}
		  
		// 查看该class_id下是否已经创建了同样title的作业
		Integer taskRes = getTaskId(classId, title);
		if(taskRes == null){
			Timestamp d = new Timestamp(System.currentTimeMillis()); // 当前时间
			//把string转化为date
			SimpleDateFormat fmt =new SimpleDateFormat("yyyy-MM-dd"); //24小时制
			  try {
				Timestamp endTime = new Timestamp (fmt.parse(end_time).getTime());
				Record task = new Record().set("class_id", classId).set("title", title).set("file_id", file_id).set("filename", filename).set("url", fileUrl).set("tea_id", tea_id).set("tea_name", tea_name).set("content", content).set("create_time", d).set("end_time", endTime);
				Db.save("task", task);
				return 1;
			} catch (ParseException e) {
				 // TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}
		// 该次作业已经创建(作业的title已经存在)
		return -1;
	}
	
	/**
	 * 更新作业的content
	 * @param task_id
	 * @param content
	 * @return
	 */
	public int updateTask(int task_id, String content, String file_id, String end_time){
		//查询该task_id是否存在
		int taskRes = isTaskFile(task_id, file_id);
		switch (taskRes) {
		case 0:
			// 判断 end_time 是否为null
			if(end_time!=null) {
				// 更新content和 end_time
				SimpleDateFormat fmt =new SimpleDateFormat("yyyy-MM-dd"); //24小时制
				Timestamp endTime;
				try {
					endTime = new Timestamp (fmt.parse(end_time).getTime());
					return Db.update("update task set content =?, end_time = ? where task_id =?", content, endTime, task_id)>0 ? 1: 0; // update成功1，否则为0
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 0;
				}
				
			} else {
				return Db.update("update task set content =? where task_id =?", content, task_id)>0 ? 1: 0; // update成功1，否则为0
			}
		case 1:
			String sql = "select url, filename from img_file_store where id = ?";
			Record fileRes = Db.findFirst(sql, file_id);	
			String fileUrl = null; // file url
			String filename = null; // filename
			if(fileRes != null) {
				fileUrl = fileRes.getStr("url");
				filename = fileRes.getStr("filename");
			}
			// 判断 end_time 是否为null
			if(end_time!=null) {
				// 更新content和 end_time
				SimpleDateFormat fmt =new SimpleDateFormat("yyyy-MM-dd"); //24小时制
				Timestamp endTime;
				try {
					endTime = new Timestamp (fmt.parse(end_time).getTime());
					return Db.update("update task set content =?, end_time = ?, file_id = ?, filename = ?, url = ? where task_id =?", content, endTime, file_id, filename, fileUrl, task_id)>0 ? 1: 0; // update成功1，否则为0
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 0;
				}
				
			} else {
				return Db.update("update task set content =?, file_id = ?, filename = ?, url = ? where task_id =?", content, file_id, filename, fileUrl, task_id)>0 ? 1: 0; // update成功1，否则为0
			}
		default:
			return -1; // task_id不存在
		}	
	}
	
	/**
	 * 判断是否提交了文件，该task_id是否存在
	 * @param task_id
	 * @param file_id
	 * @return
	 */
	public int isTaskFile(int task_id, String file_id) {
		String sql = "select task_id, file_id from task where task_id = ?";
		Record res = Db.findFirst(sql, task_id);
		if(res.getInt("task_id") != null) { // task_id存在, 该条记录存在
			if(file_id == null) {
				return 0; // 这时没有传文件，只是更新content即可
			} else {
				// 传了文件
				String ori_file_id = res.getStr("file_id");
				if(file_id.equals(ori_file_id)) {
					return 0; // 传了文件， 但是跟原来的一样，不用update file_id这些
				} else {
					return 1; // 传了文件， file_id与原来的不相等， 要update file_id  filename这些信息
				}
			}
		}
		return -1; // notice_id不存在
	}
	
	/**
	 * 查询某次作业是否已经创建了
	 * @param classId
	 * @param title
	 * @return
	 */
	public Integer getTaskId(int classId, String title){
		String sql = "select task_id from task where class_id = ? and title = ?";
		Record res = Db.findFirst(sql, classId, title);
		if(res != null){
			// 该次作业已经创建了（即作业的title一样）
			return res.getInt("task_id");
		}
		return null; // 该次作业还没有创建
	}
	
	/**
	 * 查找task_id是否存在
	 * @param classId
	 * @return
	 */
	public Boolean isTaskId(int taskId){
		String sql = "select task_id from task where task_id = ?";
		Record res = Db.findFirst(sql, taskId);
		if(res != null){
			// 该次作业已经创建了（即作业的title一样）
			return true;
		}
		return false; // 该次作业还没有创建
	}
	
	/**
	 * 1. 查找该班级中老师发布的所有作业
	 * 2. search all tasks in that class by the teacher
	 * @param classId
	 * @return
	 */
	public List<Record> getAllTaskByClassId(int classId){
		String sql = "select * from task where class_id =? order by create_time DESC";
		List<Record> taskList = Db.find(sql, classId);
		if(taskList.size() > 0) {
			return taskList;
		}
		return null;
	}

	/**
	 * 1. 学生提交作业： submit task
	 * 先在table submit_task中查询task_id与user_id(stu_id)这条记录是否存在,若存在，则是update,否则重新创建new one record
	 * @param stuId
	 * @param taskId
	 * @param fileId
	 * @return
	 */
	public int submitTask(String stuId, int taskId, String fileId, String stu_name){
		/**
		 * 由task_id去 table task 中查找该 task的end_time，查看是否已经过期了
		 */
		Timestamp endTime = getEndTime(taskId);
		Timestamp d = new Timestamp(System.currentTimeMillis());  // 当前时间
		if(d.getTime() > endTime.getTime()) {
			return -2; // 提交不了作业了，提交作业的时间过了
		} else {
			// 先查看fileId存不存在（是否已经成功上传作业）
			// 先去table img_file_store中查找该fileId是否存在
			String sql = "select url, filename from img_file_store where id = ?";
			Record fileRes = Db.findFirst(sql, fileId);
			String fileUrl = null; // file url
			String filename = null; // filename
			if(fileRes != null) {
				fileUrl = fileRes.getStr("url");
				filename = fileRes.getStr("filename");
			}
			
			// 由stuId 和  taskId来查询用户是否已经提交过作业了，若已提交，则update,否则create
			Integer taskRes = isTaskId(taskId, stuId);
			if(taskRes != null) {
				// 该条记录存在，此时update
				int upRes = Db.update("update submit_task set file_id = ?, filename = ?, url = ?, create_time = ? where submit_tid = ?", fileId, filename, fileUrl, d, taskRes);
				if(upRes > 0){
					return 1; // update success
				} else {
					return 0; // db problem
				}
			} else {
				// 该条记录不存在，此时create
				Integer class_id = getClassIdByTaskId(taskId);
				if(class_id != null) {
					Record createRes = new Record().set("task_id", taskId).set("class_id", class_id).set("stu_id", stuId).set("stu_name", stu_name).set("file_id", fileId).set("url", fileUrl).set("filename", filename).set("create_time", d);
					try {
						Db.save("submit_task", createRes);
						return 1; // create true
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						return 0;
					}
				}else {
					return -1; // class_id不存在
				}
					
			}
		}
		
	}
	
	// 由task_id查找class_id
	public Integer getClassIdByTaskId(int task_id) {
		String sql = "select class_id from task where task_id = ?";
		Record res = Db.findFirst(sql, task_id);
		if(res != null) {
			return res.getInt("class_id");
		}
		return null;
	}
	
	// 由task_id  get end_time
	public Timestamp getEndTime(int task_id) {
		String sql = "select end_time from task where task_id = ?";
		Record res = Db.findFirst(sql, task_id);
		if(res != null) {
			return res.getTimestamp("end_time");
		}
		return null;
	}
	/**
	 * 查询table submit_task 中的 task_id是否存在
	 * @param task_id
	 * @return
	 */
	public Integer isTaskId(int task_id, String stu_id){
		String sql = "select submit_tid from submit_task where task_id = ? and stu_id = ?";
		Record res = Db.findFirst(sql, task_id, stu_id);
		if(res != null) {
			return res.getInt("submit_tid");  // 该记录已存在
		}
		return null;
	}
	
	/**
	 * 1. 查询该门课 该学生提交的所有作业(search all the tasks the student had submitted)
	 * 2. table task 和  submit_task 两表关联
	 * @param classId
	 * @param studentId
	 * @return
	 */
	public List<Record> getAllStuTask(int classId, String studentId) {
		String sql1 = "select a.title, a.content, b.submit_tid, b.task_id, b.filename, b.url, b.grade from task a, submit_task b where b.class_id = ? and b.stu_id = ? and a.task_id = b.task_id";
		List<Record> taskList = Db.find(sql1, classId, studentId);
		if(taskList.size() > 0) {
			return taskList;
		}
		return null;
	}
	
	/**
	 * 1. 查看学生（当前用户）有哪次作业还没有提交呢？ （查找学生某一门课还没有提交的作业的content和title）
	 * 2. search all the tasks the student has submitted in one class
	 * 3. @param: classId、stu_id
	 * 
	 */
	public List<Record> stuNotSubTask(int class_id, String stu_id) {
		// https://blog.csdn.net/muxiaoshan/article/details/7617533
		/**
		 * 1. on条件是在生成临时表时使用的条件，它不管on中的条件是否为真，都会返回左边表中的记录。
		 * 2. on是两表关联的时候生成表，where是等表生成之后再对新生成的表进行过滤
		 */
		String sql = "select a.* from task a left join submit_task b on a.task_id = b.task_id and b.stu_id = ? and b.class_id = ? where b.task_id is null and a.class_id = ?";
		List<Record> taskList = Db.find(sql, stu_id, class_id, class_id);
		System.out.println(taskList);
		if(taskList.size() > 0) {
			return taskList;
		}
		return null;
	}
	
	// 一个班级中有多少学生提交了该门课的作业
	// 由class_id来查询
	public List<Record> getSubmitTask(int classId, int taskId) {
		String sql = "select * from submit_task where class_id = ? and task_id = ?";
		List<Record> taskList = Db.find(sql, classId, taskId);
		if(taskList.size() > 0) {
			return taskList;
		}
		return null;
	}
	
	/**
	 * 1. 老师查看某一次作业 有哪些作业已经评分了呢？查看已经评过分的作业
	 * @param classId
	 * @param taskId
	 * @return
	 */
	public List<Record> teaGetSetGrade(int classId, int taskId) {
		String sql = "select * from submit_task where class_id = ? and task_id = ? and grade is not null";
		List<Record> taskList = Db.find(sql, classId, taskId);
		if(taskList.size() > 0) {
			return taskList;
		}
		return null;
	}
	
	/**
	 * 1. 老师查看某一次作业 有哪些作业已经评分了呢？查看还评过分的作业
	 * @param classId
	 * @param taskId
	 * @return
	 */
	public List<Record> teaGetNotGrade(int classId, int taskId) {
		String sql = "select * from submit_task where class_id = ? and task_id = ? and grade is null";
		List<Record> taskList = Db.find(sql, classId, taskId);
		if(taskList.size() > 0) {
			return taskList;
		}
		return null;
	}
	
	/**
	 * 1. 老师查看一个班级还有多少学生没交该门课的作业（比如说第一次作业有多少学生没提交了，第二次作业有多少学生没提交了）
	 * 2. @param: class_id 和 task_id
	 * 3. @method: get
	 */
	public List<Record> teaGetNotSubTask(int classId, int taskId) {
		String sql1 = "select a.stu_id, a.stu_name, a.class_name from stu_course a left join submit_task b on a.stu_id = b.stu_id and b.class_id = ? and b.task_id = ? where a.class_id = ? and b.class_id is null";
		List<Record> taskList = Db.find(sql1, classId, taskId, classId);
		if(taskList.size() > 0) {
			return taskList;
		}
		return null;
	}
	// 老师对作业进行评分
	// 提交的是该submit_task中的id(primary key)
	// 和分数(update 当中的分数 grade)
	public Boolean setGrade(int id, int grade) {
		String realGrade = null;
		switch(grade) {
		case 1:
			realGrade = "D";
			break;
		case 2:
			realGrade = "C-";
			break;
		case 3:
			realGrade = "C+";
			break;
		case 4:
			realGrade = "B-";
			break;
		case 5:
			realGrade = "B+";
			break;
		case 6:
			realGrade = "A-";
			break;
		case 7:
			realGrade = "A+";
			break;
		default:
			realGrade = null;
		}
		return Db.update("update submit_task set grade = ? where submit_tid = ?", realGrade, id)>0;
	}
}
