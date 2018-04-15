package com.tp.clientModel;

public class UserReqModel {
	private String id;  // 用户的id(学生or老师)
	private String username;
	private String password;
	private int type;
	private int status; // 1 表示 在校；2 表示  交换生 ； 3 表示 休学  ；  4 表示出国
	private int course_id; // course_id
	private String course_name;
	private String course_intro;
	private String class_name;
	private int class_id;
	private String content;
	private String file_id;
	private int task_id; // 作业的Id（task id)
	private int submit_tid; // 提交作业的Id
	private int grade;
	private String email;
	private String title; // 布置作业的title
	private int notice_id; // 公告的id
	private String end_time; // 提交作业的截止时间
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getCourse_intro() {
		return course_intro;
	}
	public void setCourse_intro(String course_intro) {
		this.course_intro = course_intro;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	
	public int getSubmit_tid() {
		return submit_tid;
	}
	public void setSubmit_tid(int submit_tid) {
		this.submit_tid = submit_tid;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
}
