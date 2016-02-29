package com.autonavi.cc.core.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import autonavi.online.framework.property.PropertiesConfig;
import autonavi.online.framework.property.PropertiesConfigUtil;

import com.autonavi.cc.api.exception.BusinessRunException;

public class CCExceptionUtils {
	private static Logger logger = LogManager.getLogger(CCExceptionUtils.class);
	private static CCExceptionUtils cCExceptionUtils;
	private PropertiesConfig pc=null;
	
	private CCExceptionUtils()throws Exception{
		pc=PropertiesConfigUtil.getPropertiesConfigInstance();
	}
	
	public static CCExceptionUtils getInstance(){
		try {
			if(cCExceptionUtils==null){
				cCExceptionUtils=new CCExceptionUtils();
			}
			return cCExceptionUtils;
		} catch (Exception e) {
			logger.error("初始化异常工具失败", e);
			throw new BusinessRunException("初始化异常工具失败，请检查");
		}
	}
	
	public String getMessage(String errorCode){
		String message="";
		message=(String)pc.getProperty(errorCode);
		return message;
	}

}
