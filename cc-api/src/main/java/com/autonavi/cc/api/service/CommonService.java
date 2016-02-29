package com.autonavi.cc.api.service;

import java.util.List;

public interface CommonService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询客户信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCustomList(String userName) throws Exception;
	
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询项目负责人信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getProjectLeaderList(String userName) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月27日
	 * @description 查询品类价格
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @param customId
	 * @return
	 * @throws Exception
	 */
	public Object getCollectClassPrice(String ownerId, String collectClassParentId, String collectClassId, String customId) throws Exception;
	
	

}
