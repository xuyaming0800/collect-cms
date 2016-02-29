package com.autonavi.cc.api.service;

import com.autonavi.cc.api.entity.DetailConfigEntity;

/**
 * 增加，删除，查询和修改组件信息
 * 
 * @author wenpeng.jin
 *
 */
public interface DetailConfigService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月12日
	 * @description 根据客户ID,采集品类大类ID和采集品类小类ID查询组件
	 * @param ownerId
	 * @param collectClassIdp
	 * @param collectClassId
	 * @return
	 */
	public DetailConfigEntity queryDetailConfigs(String ownerId, String collectClassIdp, String collectClassId) throws Exception;
	
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 批量保存组件信息
	 * @param detailConfigEntity
	 * @return
	 * @throws Exception
	 */
	public String saveDetailConfigs(DetailConfigEntity detailConfigEntity)throws Exception;
	/**
	 * 获取版本号
	 * @return
	 * @throws Exception
	 */
	public Long geUniqueId() throws Exception;
	
}
