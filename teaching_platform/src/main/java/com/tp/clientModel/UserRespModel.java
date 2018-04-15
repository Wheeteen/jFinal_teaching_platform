package com.tp.clientModel;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class UserRespModel {

	private List<Record> list;
//	private String name;
//	private Integer id; //user actual id
	private Integer count; //数量多少
//	private Integer course_id; // 课程的Id
//	private Integer class_id; // 班级的id
//	private Integer task_id; // 作业的Id
//	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	public List<Record> getList() {
		return list;
	}

	public void setList(List<Record> list) {
		this.list = list;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
//
//	public Integer getCourse_id() {
//		return course_id;
//	}
//
//	public void setCourse_id(Integer course_id) {
//		this.course_id = course_id;
//	}
//
//	public Integer getClass_id() {
//		return class_id;
//	}
//
//	public void setClass_id(Integer class_id) {
//		this.class_id = class_id;
//	}
//
//	public Integer getTask_id() {
//		return task_id;
//	}
//
//	public void setTask_id(Integer task_id) {
//		this.task_id = task_id;
//	}
	
}
