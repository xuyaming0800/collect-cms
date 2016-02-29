package com.autonavi.cc.api.service;
/**
 * 客户中心-API 
 * 所有服务的公共方法
 * @author yaming.xu
 *
 */
public interface CCApiToolsService {
	/**
	 * 根据错误码获取异常信息
	 * @param code
	 * @return
	 */
	public String getExceptionMessage(String code);
	
	/**
	 * 根据业务码获取业务信息
	 * @param code
	 * @return
	 */
	public String getMessage(String code);

}
