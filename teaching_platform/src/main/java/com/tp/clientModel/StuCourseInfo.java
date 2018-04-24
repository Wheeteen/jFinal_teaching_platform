package com.tp.clientModel;

import java.sql.Timestamp;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class StuCourseInfo {
	private int class_id;
	private String class_name;
	private int course_id;
	private String course_name;
	private String tea_name;
	private String tea_id;
	private List<Record> newest;
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
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
	public String getTea_name() {
		return tea_name;
	}
	public void setTea_name(String tea_name) {
		this.tea_name = tea_name;
	}
	public String getTea_id() {
		return tea_id;
	}
	public void setTea_id(String tea_id) {
		this.tea_id = tea_id;
	}
	public List<Record> getNewest() {
		return newest;
	}
	public void setNewest(List<Record> newest) {
		this.newest = newest;
	}
	
	
}
