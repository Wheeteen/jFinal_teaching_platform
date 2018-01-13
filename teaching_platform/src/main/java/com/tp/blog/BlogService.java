package com.tp.blog;

import com.jfinal.plugin.activerecord.Page;
import com.tp.model.Blog;

public class BlogService {
	/**
	 * 所有的 dao 对象也放在 Service 中
	 */
	private static final Blog dao = new Blog().dao();
	
	public Page<Blog> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from blog order by id asc");
	}
	
	public Blog findById(int id) {
		return dao.findById(id);
	}
	
	public void deleteById(int id) {
		dao.deleteById(id);
	}
}
