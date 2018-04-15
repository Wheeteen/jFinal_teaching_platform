package com.tp.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

public class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {
	public M setId(java.lang.Integer id) {
		set ("id", id);
		return (M)this;
	}
	public java.lang.Integer getId() {
		return getInt("id");
	}
	public M setStuId(java.lang.Integer stu_id) {
		set("stu_id", stu_id);
		return (M)this;
	}

	public java.lang.Integer getStuId() {
		return getInt("stu_id");
	}
	public M setTeaId(java.lang.Integer tea_id) {
		set("tea_id", tea_id);
		return (M)this;
	}

	public java.lang.Integer getTeaId() {
		return getInt("tea_id");
	}

	public M setUsername(java.lang.String username) {
		set("username", username);
		return (M)this;
	}

	public java.lang.String getUsername() {
		return getStr("username");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}

	public java.lang.String getPassword() {
		return getStr("password");
	}
	//type:1 student 2 teacher 3 administor
	
	//status 1 normal 2 exchange student 3 absence from school 4 go aboard
	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}
	
	public M setImgUrl(java.lang.String setImgUrl) {
		set("imgUrl", setImgUrl);
		return (M)this;
	}

	public java.lang.String getImgUrl() {
		return getStr("imgUrl");
	}

	public M setEmail(java.lang.String email) {
		set("email", email);
		return (M)this;
	}

	public java.lang.String getEmail() {
		return getStr("email");
	}
}
