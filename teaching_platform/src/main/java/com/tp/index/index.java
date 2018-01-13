package com.tp.index;

import java.util.ArrayList;
import java.util.List;
import com.jfinal.core.Controller;
import com.tp.util.Result;


public class index extends Controller {
	public void index() {
		List<String> list = new ArrayList<String>();
		list.add("oh my god");
		list.add("this.is my mon");
		list.add("I hope to see the seasojhn");
		
		Result<List> result = new Result<List>(true, list);
		
		renderJson(result);
//		render("index.html");
	}
}
