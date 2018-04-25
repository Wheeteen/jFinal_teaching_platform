package com.tp.common.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.tp.common.config.route.BackendRoutes;
import com.tp.interceptor.LoginInterceptor;
import com.tp.model._MappingKit;

public class Config extends JFinalConfig {
	
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 81, "/", 5);
	}
	@Override
	public void configConstant(Constants arg) {
		// TODO Auto-generated method stub
		arg.setEncoding("utf-8");
		PropKit.use("config.txt");
		arg.setDevMode(PropKit.getBoolean("devMode", false));
		arg.setJsonFactory(new MixedJsonFactory());
		//Ĭ����"devMode",��û���������������false(default)
//		arg.setBaseUploadPath("uploadFile");
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub
//		arg0.addSharedFunction("/common/_layout.html");
//		arg0.addSharedFunction("/common/_paginate.html");
	}

	@Override
	public void configHandler(Handlers arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
//		me.add(new LoginInterceptor());
	}

	@Override
	public void configPlugin(Plugins arg) {
		// TODO Auto-generated method stub
		// ���� druid ���ݿ����ӳز��
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		arg.add(druidPlugin);
		
		// ����ActiveRecord���
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// ����ӳ���� MappingKit ���Զ����㶨
		_MappingKit.mapping(arp);
		arg.add(arp);
	}

	@Override
	public void configRoute(Routes route) {
		// TODO Auto-generated method stub
//		route.add("/", index.class, "/index");	// ����������Ϊ��Controller����ͼ���·��
//		route.add("/blog", BlogController.class); // ����������ʡ��ʱĬ�����һ������ֵ��ͬ���ڴ˼�Ϊ "/blog"
		route.add(new BackendRoutes());
	}

}
