package com.tp.common.config.route;

import com.jfinal.config.Routes;
import com.tp.main.controller.AboutClassController;
import com.tp.main.controller.CourseController;
import com.tp.main.controller.FileController;
import com.tp.main.controller.LoginController;
import com.tp.main.controller.NoticeController;
import com.tp.main.controller.TaskController;

public class BackendRoutes extends Routes {

	@Override
	public void config() {
		// TODO Auto-generated method stub
		/**
		 * login: url: localhost:81 or localhost:80/login
		 * register: url: localhost:81/register
		 */
		
		add("/", LoginController.class); //login
		add("course", CourseController.class); // course
		add("file", FileController.class); // file
		add("task", TaskController.class); // task
		add("aClass", AboutClassController.class); // class
		add("notice", NoticeController.class); // class
	}

}
