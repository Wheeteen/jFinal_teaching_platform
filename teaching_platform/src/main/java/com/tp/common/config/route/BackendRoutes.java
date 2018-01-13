package com.tp.common.config.route;

import com.jfinal.config.Routes;
import com.tp.main.controller.LoginController;

public class BackendRoutes extends Routes {

	@Override
	public void config() {
		// TODO Auto-generated method stub
		/**
		 * login: url: localhost:81 or localhost:80/login
		 * register: url: localhost:81/register
		 */
		add("/", LoginController.class); //Æð×÷ÓÃ
	}

}
