package com.tp.model;

import java.io.File;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;


public class _MappingKit {
	public static void mapping (ActiveRecordPlugin arp) {
		arp.addMapping("stu_info", "id", Student.class);
		arp.addMapping("tea_info", "id", Teacher.class);
		arp.addMapping("course", "cour_id", Course.class);
		arp.addMapping("stu_course", "id", StuCourse.class);
		arp.addMapping("class_info", "id", ClassInfo.class);
		arp.addMapping("task", "task_id", Task.class);
		arp.addMapping("img_file_store", "id", FileInfo.class);
		arp.addMapping("tp_advertise", "id", Notice.class);
	}
}
