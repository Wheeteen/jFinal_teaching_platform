package com.tp.blog;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.tp.model.Blog;

@Before(BlogInterceptor.class)
public class BlogController extends Controller {
static BlogService service = new BlogService();
	
	public void index() {
		setAttr("blogPage", service.paginate(getParaToInt(0, 1), 10));
		render("blog.html");
	}
	
	public void add() {
	}
	
	/**
	 * save �� update ��ҵ���߼���ʵ��Ӧ����ҲӦ�÷��� serivce ֮�У�
	 * ��Ҫ�����ݽ���ȷ�Խ�����֤���ڴ˽�Ϊ��͵��
	 */
	@Before(BlogValidator.class)
	public void save() {
		String content = getPara("blog.content");
		String title = getPara("blog.title");
		System.out.println(content+","+title);
		getModel(Blog.class).save();
		redirect("/blog");
	}
	
	public void edit() {
		setAttr("blog", service.findById(getParaToInt()));
	}
	
	/**
	 * save �� update ��ҵ���߼���ʵ��Ӧ����ҲӦ�÷��� serivce ֮�У�
	 * ��Ҫ�����ݽ���ȷ�Խ�����֤���ڴ˽�Ϊ��͵��
	 */
	@Before(BlogValidator.class)
	public void update() {
		getModel(Blog.class).update();
		redirect("/blog");
	}
	
	public void delete() {
		service.deleteById(getParaToInt());
		redirect("/blog");
	}
}
