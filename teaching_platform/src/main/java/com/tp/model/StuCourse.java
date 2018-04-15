package com.tp.model;

import java.sql.Timestamp;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class StuCourse extends Model<StuCourse> {
	Student student = new Student();
	ClassInfo classInfo = new ClassInfo();
	
	/**
	 * 注册班级（相对应该门课程）: register one class from the course
	 * @param stuId
	 * @param classId
	 * @return
	 */
	public Boolean registerClass(String stuId, int classId, String stu_name){
		int res = isChooseClass(stuId, classId);
		if(res == 0){
			// The student hasn't register the class
			// 由class_id get  --》  course_name
			Record info =  classInfo.getCourseClassName(classId);
			if(info != null){
				String class_name = info.getStr("class_name");
				Timestamp d = new Timestamp(System.currentTimeMillis()); 
				Record stu_course = new Record().set("stu_id", stuId).set("stu_name", stu_name).set("class_id", classId).set("class_name", class_name).set("create_time", d);
				
				try {
					Db.save("stu_course", stu_course);
					return true;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return false;
				}	
			}
			
		}
		return false;		
	}
	
	//判断学生是否已经选择了该门课程的 该班级
	// judge whether the student has chosen the class of the course
	public int isChooseClass(String stuId, int classId){
		String sql = "select id from stu_course where stu_id = ? and class_id = ?";
		Record info = Db.findFirst(sql, stuId, classId); // 主要有一个相同的就不行了
		if(info != null){
			return 1; // the student has chosen the class of the course
		}
		return 0; // 学生还没有注册该课程的对应班级，the student hasn't chosen the class
	}
	
	// 判断学生注册是哪门课程的哪个班级（注册了哪个班级）
	// select all the classes of the student
	/**
	 * 1. select all the classes that the student has choosen
	 * 2. 判断学生注册是哪门课程的哪个班级（注册了哪个班级）
	 * 3. 三表关联：table stu_course, class_info, course
	 * @param stuId
	 * @return
	 */
	public List<Record> getClassByStuId(String stuId){
		String sql2 = "select a.class_id, a.class_name, b.course_id, b.course_name, b.tea_id, b.tea_name from stu_course a, class_info b where a.stu_id = ? and a.class_id = b.class_id order by a.create_time DESC";
		List<Record> classList = Db.find(sql2, stuId);
		if(classList.size()>0){
			return classList;
		}
		return null;
	}
	
	// 查找一个class里面的学生
	public List<Record> getStudentsInClass(int classId) {
		String sql1 = "select class_name, stu_id, stu_name from stu_course where class_id = ?";
		List<Record> stuList = Db.find(sql1, classId);
		if(stuList.size() > 0) {
			return stuList;
		}
		return null;
	}

}
