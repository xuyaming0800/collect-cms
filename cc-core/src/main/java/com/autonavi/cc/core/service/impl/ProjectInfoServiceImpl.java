package com.autonavi.cc.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import autonavi.online.framework.sharding.dao.DaoHelper;

import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.bean.ProjectInfo;
import com.autonavi.cc.api.entity.ProjectInfoEntity;
import com.autonavi.cc.api.entity.ResultEntity;
import com.autonavi.cc.api.service.ProjectInfoService;
import com.autonavi.cc.api.util.HttpClientUtil;
import com.autonavi.cc.api.util.PropConstants;
import com.autonavi.cc.core.componet.CommonComponent;
import com.autonavi.cc.core.componet.ProjectInfoCacheComponent;
import com.autonavi.cc.core.dao.ProjectInfoDao;
import com.autonavi.cc.core.util.SysProps;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 项目管理增删改
 * @author wenpeng.jin
 *
 */
@Service("projectInfoService")
public class ProjectInfoServiceImpl implements ProjectInfoService {
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private ProjectInfoDao projectInfoDao;
	
	@Autowired
	private ProjectInfoCacheComponent projectInfoCacheComponent;
	
	@Autowired
	private CommonComponent commonComponent;
	
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
	public Pagination queryAllProjectInfos(int pageNo,int limit)  throws Exception{
		logger.info("进入queryAllProjectInfos方法查询所有项目信息-->service");
		Pagination page = null;
		List<ProjectInfo> projectInfoList = null;
		if(pageNo != 0 && limit != 0) {
			logger.info("分页查询项目信息，查询条件,当前页："+pageNo+",每页记录数："+limit+"--start");
			page = new Pagination(pageNo, limit);
			projectInfoList = (List<ProjectInfo>)projectInfoDao.queryAllProjectInfos("",SysProps.getCCSingleDataSourceKey(),page.getStart(), page.getLimit());
			logger.info("分页查询项目信息，查询条件,当前页："+pageNo+",每页记录数："+limit+"--end");
			logger.info("查询结果集为："+projectInfoList.toString());
		}else {
			logger.info("不分页查询项目信息--start");
			page = new Pagination();
			projectInfoList = (List<ProjectInfo>)projectInfoDao.queryAllProjectInfos("",SysProps.getCCSingleDataSourceKey(),0, 0);
			logger.info("不分页查询项目信息--end");
			logger.info("查询结果集为："+projectInfoList.toString());
		}
		page.setObjectList(projectInfoList);
		return page;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 条件分页查询项目
	 * @param projectInfoEntity
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination queryProjectInfos(ProjectInfoEntity  projectInfoEntity) throws Exception {
		logger.info("分页查询项目信息，查询条件customName："+projectInfoEntity.getCustomName()+"，projectName"+projectInfoEntity.getProjectName()+"--service--start");
		ProjectInfo projectInfo = new ProjectInfo();
		String customName = projectInfoEntity.getCustomName();
		projectInfo.setProjectName(projectInfoEntity.getProjectName()+"%");
		projectInfo.setCustomId("");
		projectInfo.setStatus(0);//默认值为1此处设置为0 默认查询所有项目（包括暂停项目）
		projectInfo.setDsKey(SysProps.getCCSingleDataSourceKey());
		int pageNo = projectInfoEntity.getPageNo();
		int limit = projectInfoEntity.getLimit();
		if(limit == 0) {
			limit = Integer.valueOf(SysProps.getMessage(SysProps.LIMIT));
		}
		Pagination page = new Pagination(pageNo, limit);
		List<String> idList = null;
		if(customName != null && !"".equals(customName)) {
			idList = commonComponent.getCustomIdList(customName, SysProps.getMessage("customUsertype"), SysProps.getMessage("get_custom_url"));
//			idList = new ArrayList<String>();
//			Map<String,Object> customMap = getUsers(get_custom_url,customName);
//			if(customMap != null) {
//				for(Entry<String,Object > entry : customMap.entrySet()) {
//					Map user = (Map)entry.getValue();
//					idList.add((String)user.get("id"));
//				}
//			}
		}
	
		List<ProjectInfo> projectInfoList = (List<ProjectInfo>)projectInfoDao.queryProjectInfos(projectInfo,idList,page.getStart(), page.getLimit());
//		long totalCount=DaoHelper.getCount();
//		Map<String,Object> customMapAll = getUsers(get_custom_url,"");
//		Map<String,Object> projLeaderMapAll = getUsers(get_project_leader_url,"");
//		for(ProjectInfo pi : projectInfoList) {
//			if(customMapAll != null) {
//				Map  user = (Map)customMapAll.get(pi.getCustomId());
//				if(user != null) {
//					pi.setCustomName((String)user.get("name"));
//				}else {
//					pi.setCustomName("");
//				}
//				
//			}else {
//				pi.setCustomName("");
//			}
//			if(projLeaderMapAll != null) {
//				Map  user = (Map)projLeaderMapAll.get(pi.getProjectLeaderId());
//				if(user != null) {
//					pi.setProjectLeaderName((String)user.get("name"));
//				}else {
//					pi.setProjectLeaderName("");
//				}
//			}else {
//				pi.setProjectLeaderName("");
//			}
//			
//			
//		}
		
		Map<String,Object> customMapAll = null;
		Map<String,Object> projectLeaderMapAll = null;
		Map user = null;
		for(ProjectInfo pi : projectInfoList) {
			Object customUser = commonComponent.getUser(pi.getCustomId(), customMapAll, SysProps.getMessage("get_custom_url"));
			if(customUser == null) {
				pi.setCustomName("");
			}else {
				user = (Map)customUser;
				pi.setCustomName((String)user.get("name"));
			}
			
			Object projectLeaderUser = commonComponent.getUser(pi.getProjectLeaderId(), projectLeaderMapAll, SysProps.getMessage("get_project_leader_url"));
			if(projectLeaderUser == null) {
				pi.setProjectLeaderName("");
			}else {
				user = (Map)projectLeaderUser;
				pi.setProjectLeaderName((String)user.get("name"));
			}
		}
		
		logger.info("分页查询项目信息,结果集为："+projectInfoList.toString());
		page.setObjectList(projectInfoList);
		long totalCount = (Long)projectInfoDao.queryProjectInfosCount(projectInfo,idList);
		logger.info("分页查询项目信息,总记录数为："+totalCount);
		page.setTotalCount(totalCount);
		logger.info("分页查询项目信息--service--end");
		return page;
	}

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 保存项目信息
	 * @param projectInfo
	 * @throws Exception
	 */
	@Override
	public void saveProjectInfo(ProjectInfo projectInfo) throws Exception {
		logger.info("保存项目信息--service");
//		projectInfo.setCreateTime(new Date().getTime());
		projectInfo.setDsKey(SysProps.getCCSingleDataSourceKey());
		projectInfoDao.saveProjectInfo(projectInfo);
		String id = String.valueOf(DaoHelper.getPrimaryKey());
		logger.info("刷新项目缓存,项目ID="+id);
		projectInfoCacheComponent.refresh();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新项目基本信息
	 * @param projectInfo
	 * @throws Exception
	 */
	@Override
	public void updateProjectInfo(ProjectInfo projectInfo) throws Exception {
		logger.info("更新项目信息--service");
//		projectInfo.setUpdateTime(new Date().getTime());
		projectInfo.setDsKey(SysProps.getCCSingleDataSourceKey());
		projectInfoDao.updateProjectInfo(projectInfo);
		logger.info("刷新项目缓存,项目ID="+projectInfo.getId());
		projectInfoCacheComponent.refresh();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新项目的状态
	 * @param projectInfo
	 * @throws Exception
	 */
	@Override
	public void updateProjectInfoStatus(ProjectInfo projectInfo) throws Exception {
		logger.info("更新项目状态--service");
		projectInfo.setDsKey(SysProps.getCCSingleDataSourceKey());
		projectInfoDao.updateProjectInfoStatus(projectInfo);
		logger.info("刷新项目缓存,项目ID="+projectInfo.getId());
		projectInfoCacheComponent.refresh();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月9日
	 * @description  获取所有客户信息或者获取项目负责人信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getUsers(String url,String userName)  throws Exception{
		logger.info("进入getUsers方法获取用户，url:"+url+"-----start");
		try {
			String json = HttpClientUtil.get(url+"&userName="+userName, null);
			logger.info("获取内容："+json);
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,ResultEntity.class);
			List<Object> userList = (List<Object>) resultEntity.getInfo();
			Map userMaps = null;
			if(userList != null && userList.size() > 0) {
				userMaps =new HashMap();
				for(Object obj : userList) {
					Map userMap = (Map)obj;
					userMaps.put(userMap.get("id"), userMap);
				}
			}
			logger.info("进入getUsers方法获取用户，url:"+url+"-----end");
			return userMaps;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
		

}
