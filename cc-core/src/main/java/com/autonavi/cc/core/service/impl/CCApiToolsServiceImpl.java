package com.autonavi.cc.core.service.impl;

import org.springframework.stereotype.Service;

import com.autonavi.cc.api.service.CCApiToolsService;
import com.autonavi.cc.core.exception.CCExceptionUtils;
/**
 * CC-API 客户中心
 * 工具服务
 * @author yaming.xu
 *
 */
@Service("cCApiToolsService")
public class CCApiToolsServiceImpl implements CCApiToolsService {
	
    /**
     * 获取错误信息
     */
	@Override
	public String getExceptionMessage(String code) {
		return CCExceptionUtils.getInstance().getMessage(code);
	}
	
	 /**
     * 获取业务信息
     */
	@Override
	public String getMessage(String code) {
		return CCExceptionUtils.getInstance().getMessage(code);
	}

}
