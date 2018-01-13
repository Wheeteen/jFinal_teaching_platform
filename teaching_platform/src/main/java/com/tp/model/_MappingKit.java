package com.tp.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class _MappingKit {
	public static void mapping (ActiveRecordPlugin arp) {
		arp.addMapping("user_info", "id", User.class);
	}
}
