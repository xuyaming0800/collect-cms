package com.autonavi.cc.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import autonavi.online.framework.property.PropertiesConfigUtil;

public class SysProps {
	private static Logger logger = LogManager.getLogger(SysProps.class);
	/**
	 * 获取客户中心的单一来源数据库编号
	 * @return
	 */
	public static Integer getCCSingleDataSourceKey() {
		Integer dsKey=1;
		try {
			dsKey=new Integer((String)PropertiesConfigUtil.getPropertiesConfigInstance().getProperty(CC_SINGLE_DATASOURCE_KEY));
		} catch (Exception e) {
			logger.error("获取单一数据源失败,请检查,将返回默认数据源编号1",e);
		}
		return dsKey;
	}
	
	public static String getMessage(String code){
		String message = "";
		try {
			message=(String)PropertiesConfigUtil.getPropertiesConfigInstance().getProperty(code);
		} catch (Exception e) {
			logger.error("获取单一数据源失败",e);
		}
		return message;
	}
	private static String CC_SINGLE_DATASOURCE_KEY="CC_SINGLE_DATASOURCE_KEY";
	
	public static String LIMIT="LIMIT";
	
}
