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
import com.tp.index.index;
import com.tp.model._MappingKit;

public class Config extends JFinalConfig {
	
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 81, "/", 5);
	}
	@Override
	public void configConstant(Constants arg) {
		// TODO Auto-generated method stub
		PropKit.use("config.txt");
		arg.setDevMode(PropKit.getBoolean("devMode", false));
		arg.setJsonFactory(new MixedJsonFactory());
		//默认是"devMode",若没有这个参数，即是false(default)
		
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
	public void configInterceptor(Interceptors arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configPlugin(Plugins arg) {
		// TODO Auto-generated method stub
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		arg.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		arg.add(arp);
	}

	@Override
	public void configRoute(Routes route) {
		// TODO Auto-generated method stub
//		route.add("/", index.class, "/index");	// 第三个参数为该Controller的视图存放路径
//		route.add("/blog", BlogController.class); // 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		route.add(new BackendRoutes());
	}

}
