package com.tp.clientModel;

import java.sql.Timestamp;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class GradeInfo {
	private int task_id;
	private String tea_id;
	private String tea_name;
	private int class_id;
	private String title;
	private String content;
	private Timestamp end_time;
	private List<Record> grade_list;
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
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
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public List<Record> getGrade_list() {
		return grade_list;
	}
	public void setGrade_list(List<Record> grade_list) {
		this.grade_list = grade_list;
	}
}
