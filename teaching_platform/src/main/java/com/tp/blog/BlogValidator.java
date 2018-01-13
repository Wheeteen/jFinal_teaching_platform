package com.tp.blog;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.tp.model.Blog;

public class BlogValidator extends Validator {

	@Override
	protected void handleError(Controller controller) {
		// TODO Auto-generated method stub
		controller.keepModel(Blog.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/blog/save"))
			controller.render("add.html");
		else if (actionKey.equals("/blog/update"))
			controller.render("edit.html");
	}

	@Override
	protected void validate(Controller arg0) {
		// TODO Auto-generated method stub
		validateRequiredString("blog.title", "titleMsg", "������Blog����!");
		validateRequiredString("blog.content", "contentMsg", "������Blog����!");
	}

}
