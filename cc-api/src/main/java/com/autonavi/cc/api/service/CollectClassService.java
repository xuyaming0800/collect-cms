package com.autonavi.cc.api.service;

import java.util.List;

import com.autonavi.cc.api.bean.CollectClass;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.entity.ProjectInfoEntity;

/**
 * 项目管理 增加，删除，修改，查询
 * @author wenpeng.jin
 *
 */
public interface CollectClassService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description  分页查询品类
	 * @param projectInfoEntity
	 * @return
	 * @throws Exception
	 */
	public Pagination queryCollectClass(ProjectInfoEntity  projectInfoEntity)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description 保存品类
	 * @param projectInfoEntity
	 * @throws Exception
	 */
	public void saveCollectClass(ProjectInfoEntity projectInfoEntity)   throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description 更新品类基本信息
	 * @param projectInfoEntity
	 * @throws Exception
	 */
	public void updateCollectClass(ProjectInfoEntity projectInfoEntity)   throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description 更新品类状态
	 * @param projectInfoEntity
	 * @throws Exception
	 */
	public void updateCollectClassStatus(ProjectInfoEntity projectInfoEntity)   throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 更新品类入口状态
	 * @param projectInfoEntity
	 * @throws Exception
	 */
	public void updateCollectClassEntranceStatus(ProjectInfoEntity projectInfoEntity)   throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description 根据ownerId和collectclassParentId查询所有子类
	 * @param customId
	 * @param ownerId
	 * @param collectClassParentId
	 * @return
	 * @throws Exception
	 */
	public List<CollectClass> queryCollectClassChild(String customId,String ownerId,String collectClassParentId)  throws Exception;
	

}
