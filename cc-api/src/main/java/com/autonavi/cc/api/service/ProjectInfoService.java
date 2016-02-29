package com.autonavi.cc.api.service;

import java.util.Map;

import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.bean.ProjectInfo;
import com.autonavi.cc.api.entity.ProjectInfoEntity;

/**
 * 项目管理 增加，删除，修改，查询
 * @author wenpeng.jin
 *
 */
public interface ProjectInfoService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月8日
	 * @description  查询所有项目信息
	 * @param pageNo  当前页数
	 * @param limit 每页大小
	 * @return
	 * @throws Exception
	 */
	public Pagination queryAllProjectInfos(int pageNo,int limit)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 条件分页查询项目
	 * @param projectInfoEntity
	 * @return
	 * @throws Exception
	 */
	public Pagination queryProjectInfos(ProjectInfoEntity  projectInfoEntity)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 保存项目信息
	 * @param projectInfo
	 * @throws Exception
	 */
	public void saveProjectInfo( ProjectInfo  projectInfo) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新项目基本信息
	 * @param projectInfo
	 * @throws Exception
	 */
	public void updateProjectInfo( ProjectInfo  projectInfo) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新项目的状态
	 * @param projectInfo
	 * @throws Exception
	 */
	public void updateProjectInfoStatus( ProjectInfo  projectInfo) throws Exception;
	
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月9日
	 * @description  获取所有客户信息或者获取项目负责人信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUsers(String url,String userName)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月9日
	 * @description  获取所有客户信息
	 * @return
	 * @throws Exception
	 *//*
	public Map<String, Object> getCustoms()  throws Exception;
	*//**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月9日
	 * @description  获取所有的项目负责人信息
	 * @return
	 * @throws Exception
	 *//*
	public Map<String, Object> getProjectLeaders()  throws Exception;*/

}
