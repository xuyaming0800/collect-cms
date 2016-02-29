package com.autonavi.cc.core.componet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import autonavi.online.framework.util.json.JsonBinder;

import com.autonavi.cc.api.bean.CollectTaskClazz;
import com.autonavi.cc.api.bean.ProjectInfo;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.api.exception.BusinessExceptionEnum;
import com.autonavi.cc.core.dao.ProjectInfoDao;
import com.autonavi.cc.core.util.EnumConstant;
import com.autonavi.cc.core.util.SysProps;
@Component
public class ProjectInfoCacheComponent {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private RedisUtilComponent redisUtilComponent;
	@Autowired
	private ProjectInfoDao projectInfoDao;
	
	@SuppressWarnings({ "unchecked" })
	@PostConstruct
	private void init()throws Exception{
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		List<ProjectInfo> projectList=(List<ProjectInfo>)projectInfoDao.queryAllProjectInfos(SysProps.getCCSingleDataSourceKey());
		Map<String,List<ProjectInfo>> customMap=null;//项目按照客户ID分类
		Map<String,List<ProjectInfo>> customNormalMap=null;//项目按照客户ID分类
		List<ProjectInfo> allNormalProjectList = null;//存放所有正常的项目
		if(projectList!=null){
			customMap = new HashMap<String,List<ProjectInfo>>();
			customNormalMap = new HashMap<String,List<ProjectInfo>>();
			allNormalProjectList = new ArrayList<ProjectInfo>();
			for(ProjectInfo projectInfo:projectList){
				redisUtilComponent.setRedisJsonCache(EnumConstant.PROJECT_INFO_CACHE_PREFIX+projectInfo.getId()
						, projectInfo, jb, 0);
				if(customMap.get(projectInfo.getCustomId()) == null) {
					List<ProjectInfo> projects = new ArrayList<ProjectInfo>();
					customMap.put(projectInfo.getCustomId(), projects);
					
					List<ProjectInfo> normalProjects = new ArrayList<ProjectInfo>();
					customNormalMap.put(projectInfo.getCustomId(), normalProjects);
				}
				customMap.get(projectInfo.getCustomId()).add(projectInfo);
				if(projectInfo.getStatus() == 1) {//正常运行项目
					customNormalMap.get(projectInfo.getCustomId()).add(projectInfo);
					allNormalProjectList.add(projectInfo);
				}
			}
			logger.info("按客户ID分类项目信息,共"+customMap.size()+"位客户的项目信息");
			for(Entry<String,List<ProjectInfo>> entry : customMap.entrySet()) {
				redisUtilComponent.setRedisJsonCache(EnumConstant.CUSTOM_PROJECT_INFO_CACHE_PREFIX+entry.getKey(), entry.getValue(), jb,0);
			}
			logger.info("按客户ID分类正常项目信息,共"+customNormalMap.size()+"位客户的项目信息");
			for(Entry<String,List<ProjectInfo>> entry : customNormalMap.entrySet()) {
				redisUtilComponent.setRedisJsonCache(EnumConstant.CUSTOM_NORMAL_PROJECT_INFO_CACHE_PREFIX+entry.getKey(), entry.getValue(), jb,0);
			}
			logger.info("缓存所有正常的项目大集合,正常项目数量为:"+allNormalProjectList.size()+"条");
			redisUtilComponent.setRedisJsonCache(EnumConstant.ALL_NORMAL_PROJECT_INFO_CACHE_PREFIX, allNormalProjectList, jb,0);
			
			logger.info("缓存所有的项目大集合,项目数量为:"+projectList.size()+"条");
			redisUtilComponent.setRedisJsonCache(EnumConstant.ALL_PROJECT_INFO_CACHE_PREFIX, projectList, jb,0);
			logger.info("初始化缓存项目信息共"+projectList.size()+"条");
		}
	}
	public void refresh()throws Exception{
		this.init();
	}
	public ProjectInfo getProjectInfo(String id)throws Exception{
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		ProjectInfo projectInfo=redisUtilComponent.getRedisJsonCache(EnumConstant.PROJECT_INFO_CACHE_PREFIX+id, 
				ProjectInfo.class, jb);
		if(projectInfo==null){
			projectInfo=(ProjectInfo)projectInfoDao.queryProjectInfoById(String.valueOf(id), SysProps.getCCSingleDataSourceKey());
			if(projectInfo==null){
				logger.error("未找到项目，ownerId=["+id+"]");
				throw new BusinessException(BusinessExceptionEnum.PROJECT_IS_NOT_FOUND);
			}
			redisUtilComponent.setRedisJsonCache(EnumConstant.PROJECT_INFO_CACHE_PREFIX+projectInfo.getId()
					, projectInfo, jb, 0);
		}
		return projectInfo;
	}
	@SuppressWarnings("unchecked")
	public List<ProjectInfo> getProjectInfoList(Integer status,String customId)throws Exception{
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		String json=null;
		if(customId!=null&&!customId.equals("")){
			if(status==1){
				json=redisUtilComponent.getRedisStringCache(EnumConstant.CUSTOM_NORMAL_PROJECT_INFO_CACHE_PREFIX+customId);
			}else{
				json=redisUtilComponent.getRedisStringCache(EnumConstant.CUSTOM_PROJECT_INFO_CACHE_PREFIX+customId);
			}
		}else{
			if(status==1){
				json=redisUtilComponent.getRedisStringCache(EnumConstant.ALL_NORMAL_PROJECT_INFO_CACHE_PREFIX);
			}else{
				json=redisUtilComponent.getRedisStringCache(EnumConstant.ALL_PROJECT_INFO_CACHE_PREFIX);
			}
		}
		if(json!=null){
			return (List<ProjectInfo>)jb.fromJson(json, List.class, jb.getCollectionType(List.class, ProjectInfo.class));
		}else{
			return new ArrayList<ProjectInfo>();
		}
		
	}
	
//	public void refresh(String id)throws Exception{
//		logger.info("刷新项目ID="+id+"的缓存信息");
//		JsonBinder jb=JsonBinder.buildNormalBinder(false);
//		ProjectInfo projectInfo = (ProjectInfo)projectInfoDao.queryProjectInfoById(id, SysProps.getCCSingleDataSourceKey());
//		if(projectInfo==null){
//			logger.warn("projectId=["+id+"] db is null ");
//		}
//		redisUtilComponent.setRedisJsonCache(EnumConstant.PROJECT_INFO_CACHE_PREFIX+id
//				, projectInfo, jb, 0);
//		if(projectInfo != null) {
//			List<ProjectInfo> projectList = (List<ProjectInfo>)projectInfoDao.queryAllProjectInfos(projectInfo.getCustomId(), SysProps.getCCSingleDataSourceKey(), 0, 0);
//			redisUtilComponent.setRedisJsonCache(EnumConstant.CUSTOM_PROJECT_INFO_CACHE_PREFIX+projectInfo.getCustomId(), projectList, jb,0);
//		}
//		
//		refreshAllList();
//	}
//	
//	public void refresh(String customId,List<ProjectInfo> projectList )throws Exception{
//		logger.info("刷新客户ID="+customId+"的项目缓存信息");
//		logger.info(projectList);
//		JsonBinder jb=JsonBinder.buildNormalBinder(false);
//		if(projectList ==null){
//			projectList = (List<ProjectInfo>)projectInfoDao.queryAllProjectInfos(customId, SysProps.getCCSingleDataSourceKey(), 0, 0);
//		}
//		redisUtilComponent.setRedisJsonCache(EnumConstant.CUSTOM_PROJECT_INFO_CACHE_PREFIX+customId, projectList, jb,0);
//		refreshAllList();
//	}
//	
//	
//	public void refreshAllList() throws Exception{
//		JsonBinder jb=JsonBinder.buildNormalBinder(false);
//		List<ProjectInfo> allNormalProjectList = (List<ProjectInfo>)projectInfoDao.queryAllProjectInfos(null, SysProps.getCCSingleDataSourceKey(), 0, 0);//所有正常项目
//		logger.info("缓存所有正常的项目大集合,正常项目数量为:"+allNormalProjectList.size()+"条");
//		redisUtilComponent.setRedisJsonCache(EnumConstant.ALL_NORMAL_PROJECT_INFO_CACHE_PREFIX, allNormalProjectList, jb,0);
//		
//		List<ProjectInfo> projectList=(List<ProjectInfo>)projectInfoDao.queryAllProjectInfos(SysProps.getCCSingleDataSourceKey());//所有项目
//		logger.info("缓存所有的项目大集合,项目数量为:"+projectList.size()+"条");
//		redisUtilComponent.setRedisJsonCache(EnumConstant.ALL_PROJECT_INFO_CACHE_PREFIX, projectList, jb,0);
//	}

}
