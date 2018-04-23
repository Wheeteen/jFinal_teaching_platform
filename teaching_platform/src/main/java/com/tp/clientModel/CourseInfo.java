package com.tp.clientModel;

import java.sql.Timestamp;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class CourseInfo {
	private int course_id;
	private String course_name;
	private Timestamp update_time;
	private Timestamp create_time;
	private String tea_id;
	private String tea_name;
	private String introduction;
	private List<Record> class_list;
	
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getTea_id() {
		return tea_id;
	}
	public void setTea_id(String tea_id) {
		this.tea_id = tea_id;
	}
	public String getTea_name() {
		return tea_name;
	}
	public void setTea_name(String tea_name) {
		this.tea_name = tea_name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public List<Record> getClass_list() {
		return class_list;
	}
	public void setClass_list(List<Record> class_list) {
		this.class_list = class_list;
	}
}
