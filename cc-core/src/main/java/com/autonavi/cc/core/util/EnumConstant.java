package com.autonavi.cc.core.util;

public class EnumConstant {
	
	public enum COLLECT_CLASS_ENTRANCE_STATUS {
		// 0 省市区检索 1周边检索 2 省市数量查找
		NotEntrance(0), Entrance(1);

		private int code;

		public int getCode() {
			return code;
		}

		private COLLECT_CLASS_ENTRANCE_STATUS(int code) {
			this.code=code;
		}
	}
	
	public enum TASK_CLAZZ_TYPE {
		// 0 目录 1 分类
		MENU(0), ITEM(1);
		private int code;

		public int getCode() {
			return code;
		}

		private TASK_CLAZZ_TYPE(int code) {
			this.code = code;
		}
	}
	
	public enum TASK_TYPE {
		// 0 被动 1 主动
		PASSIVE(0), INITIATIVE(1);
		private int code;

		public int getCode() {
			return code;
		}

		private TASK_TYPE(int code) {
			this.code = code;
		}
	}
	
	public static final String TASK_CLAZZ_CACHE_PREFIX = "TCCP_";
	public static final String TASK_CLAZZ_INITIATIVE_MENU_CACHE_PREFIX = "TCIMCP_";
	public static final String TASK_CLAZZ_PASSIVE_MENU_CACHE_PREFIX = "TCPMCP_";
	
	public static final String PROJECT_INFO_CACHE_PREFIX = "PICP_";
	
	public static final String ALL_PROJECT_INFO_CACHE_PREFIX = "ALL_PROJECT_INFO_CACHE_PREFIX";
	public static final String ALL_NORMAL_PROJECT_INFO_CACHE_PREFIX = "ALL_NORMAL_PROJECT_INFO_CACHE_PREFIX";
	
	public static final String CUSTOM_PROJECT_INFO_CACHE_PREFIX = "CPICP_";
	
	public static final String CUSTOM_NORMAL_PROJECT_INFO_CACHE_PREFIX = "CNPICP_";
	
	public static final String USER_INFO_CACHE_PREFIX="BOSS_ONE_"; 
	
	public static final String USER_LIST_TYPE_CACHE_PREFIX="BOSS_TYPE_LIST_"; 
	
	public static final String USER_LIST_ROLE_BS_CACHE_PREFIX="BOSS_ROLE_BS_LIST_"; 

}
