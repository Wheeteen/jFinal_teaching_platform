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
	public M setUserId(java.lang.Integer user_id) {
		set("user_id", user_id);
		return (M)this;
	}

	public java.lang.Integer getUserId() {
		return getInt("user_id");
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
	//type类型：1是学生，2是老师，3是管理员
	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.Integer getType() {
		return getInt("type");
	}
	//status类型： 1是正常，2是交换生，3是休学，4是出国
	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}
}
