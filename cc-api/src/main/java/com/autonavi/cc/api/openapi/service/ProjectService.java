package com.autonavi.cc.api.openapi.service;

import java.util.List;

import com.autonavi.cc.api.bean.CollectClass;
import com.autonavi.cc.api.bean.CollectTaskClazz;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.entity.TaskClazzMenuEntity;

public interface ProjectService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 条件查询项目
	 * @param ownerId 项目ID
	 * @param customName
	 * @param projectName
	 * @param status
	 * @param pageNo
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Pagination queryProjectInfos(String ownerId,String customId,String customName,String projectName,int status,int pageNo, int limit)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据ownerId查询项目采集品类父类信息
	 * @param ownerId
	 * @param taskType 主动或者被动  0 被动任务 1 主动任务    
	 * @return
	 * @throws Exception
	 */
	public List<CollectClass> queryCollectClass(String ownerId,String taskType)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据ownerId查询项目采集品类父类以及子类信息
	 * @param ownerId
	 * @param taskType 主动或者被动  0 被动任务 1 主动任务    
	 * @return
	 * @throws Exception
	 */
	public List<TaskClazzMenuEntity> getCollectClazzTree(String ownerId,String taskType)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月13日
	 * @description 更新项目状态
	 * @param customId
	 * @param ownerId
	 * @param status
	 * @throws Exception
	 */
	public void updateProjectStatus(String customId,String ownerId,String status) throws Exception;
	/**
	 * 获取分类详细信息
	 * @param ownerId
	 * @param collectCLassId
	 * @return
	 * @throws Exception
	 */
	public CollectTaskClazz getCollectClazz(String ownerId,String collectCLassId)  throws Exception;
}
