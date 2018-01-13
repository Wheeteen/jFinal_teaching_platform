package com.tp.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

public class BaseCourse <M extends BaseCourse<M>> extends Model<M> implements IBean {
	public M setId(java.lang.Integer id) {
		set ("cour_id", id);
		return (M)this;
	}
	public java.lang.Integer getId() {
		return getInt("cour_id");
	}
	public M setCourseName(java.lang.String course_name) {
		set("course_name", course_name);
		return (M)this;
	}

	public java.lang.String getCourseName() {
		return getStr("course_name");
	}

	public M setIntroduction(java.lang.String introduction) {
		set("introduction", introduction);
		return (M)this;
	}

	public java.lang.String getIntroduction() {
		return getStr("introduction");
	}
}
