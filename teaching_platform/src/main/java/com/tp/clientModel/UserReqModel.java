package com.tp.clientModel;

public class UserReqModel {
	private int id;
	private String password;
	private int type;
	private String course_name;
	private String course_intro;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getCourse_intro() {
		return course_intro;
	}
	public void setCourse_intro(String course_intro) {
		this.course_intro = course_intro;
	}
	
}
