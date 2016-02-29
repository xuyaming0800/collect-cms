
package com.autonavi.cc.core.openapi.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autonavi.online.framework.sharding.dao.DaoHelper;

import com.autonavi.cc.api.bean.CollectClass;
import com.autonavi.cc.api.bean.CollectTaskClazz;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.bean.ProjectInfo;
import com.autonavi.cc.api.entity.ResultEntity;
import com.autonavi.cc.api.entity.TaskClazzMenuEntity;
import com.autonavi.cc.api.openapi.service.ProjectService;
import com.autonavi.cc.api.util.HttpClientUtil;
import com.autonavi.cc.core.componet.CommonComponent;
import com.autonavi.cc.core.componet.ProjectInfoCacheComponent;
import com.autonavi.cc.core.componet.TaskClazzCacheComponent;
import com.autonavi.cc.core.dao.CollectClassDao;
import com.autonavi.cc.core.dao.ProjectInfoDao;
import com.autonavi.cc.core.util.SysProps;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 对外接口查询项目相关信息
 * @author wenpeng.jin
 *
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private ProjectInfoDao projectInfoDao;
	@Autowired
	private ProjectInfoCacheComponent projectInfoCacheComponent;
	@Autowired
	private CollectClassDao collectClassDao;
	@Autowired
	private TaskClazzCacheComponent taskClazzCacheComponent;
	@Autowired
	private CommonComponent commonComponent;

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 条件查询项目
	 * @param ownerId 项目ID
	 * @param customName
	 * @param projectName
	 * @param projectName
	 * @param pageNo
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryProjectInfos(String ownerId,String customId,String customName, String projectName,int status,int pageNo, int limit) throws Exception {
		logger.info("查询项目信息，查询条件customName："+customName+",projectName"+projectName+",当前页数："+pageNo+",每页记录数："+limit+"--service--start");
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectName(projectName+"%");
		projectInfo.setCustomId(customId);
		projectInfo.setId(ownerId);
		projectInfo.setStatus(Integer.valueOf(status));//项目状态
		projectInfo.setDsKey(SysProps.getCCSingleDataSourceKey());
		
		List<String> idList = null;
		if(customName != null && !"".equals(customName)) {
			idList = commonComponent.getCustomIdList(customName, SysProps.getMessage("customUsertype"),SysProps.getMessage("get_custom_url"));
//			idList = new ArrayList<String>();
//			Map<String,Object> customMap = getUsers(get_custom_url,customName);
//			if(customMap != null) {
//				for(Entry<String,Object > entry : customMap.entrySet()) {
//					Map user = (Map)entry.getValue();
//					idList.add((String)user.get("id"));
//				}
//			}
		}
		Pagination page = null;
		List<ProjectInfo> projectInfoList = null;
		if(pageNo==0 && "".equals(projectName) && "".equals(ownerId)){
			//现查缓存
			projectInfoList=projectInfoCacheComponent.getProjectInfoList(projectInfo.getStatus(), projectInfo.getCustomId());
		}
		if(projectInfoList!=null&&projectInfoList.size()>0){
			logger.info("直接返回缓存中的数据!");
			page = new Pagination();
		}else{
			if(pageNo != 0) {
				if(limit == 0) {
					limit = Integer.valueOf(SysProps.getMessage(SysProps.LIMIT));
				}
				logger.info("分页查询项目信息，查询条件,当前页："+pageNo+",每页记录数："+limit+"--start");
				page = new Pagination(pageNo, limit);
				projectInfoList = (List<ProjectInfo>)projectInfoDao.queryProjectInfos(projectInfo,idList,page.getStart(), page.getLimit());
				logger.info("分页查询项目信息，查询条件,当前页："+pageNo+",每页记录数："+limit+"--end");
//				long totalCount = (Long)projectInfoDao.queryProjectInfosCount(projectInfo,idList);
				long totalCount = DaoHelper.getCount();
				logger.info("查询项目信息,总记录数为："+totalCount);
				page.setTotalCount(totalCount);
				logger.info("查询结果集为："+projectInfoList.toString());
			}else {
				logger.info("不分页查询项目信息--start");
				page = new Pagination();
				projectInfoList = (List<ProjectInfo>)projectInfoDao.queryProjectInfos(projectInfo,idList,0, 0);
				logger.info("不分页查询项目信息--end");
				logger.info("查询结果集为："+projectInfoList.toString());
				if(projectInfoList != null && projectInfoList.size()> 0) {
					logger.info("根据客户ID刷新客户的项目缓存信息");
					projectInfoCacheComponent.refresh();
				}
			}
		}
		
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
		
		logger.info("查询项目信息,结果集为："+projectInfoList.toString());
		page.setObjectList(projectInfoList);
		logger.info("查询项目信息--service--end");
		return page;
	}
	
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
	@Override
	public List<CollectClass> queryCollectClass(String ownerId,String taskType)
			throws Exception {
		logger.info("查询项目的采集品类父类,项目ID:"+ownerId);
		List<CollectClass> collectClasses=new ArrayList<CollectClass>();
//		List<CollectClass> collectClasses = (List<CollectClass>)collectClassDao.queryCollectClass(ownerId, projectInfoCacheComponent.getProjectInfo(ownerId).getCustomId(),Integer.valueOf(taskType));
		List<TaskClazzMenuEntity> list=taskClazzCacheComponent.getCollectClazzTree(Long.valueOf(ownerId), taskType.equals("1")?false:true);
		for(TaskClazzMenuEntity entity:list){
			CollectClass clazz=new CollectClass();
			clazz.setId(entity.getCollectClassId().toString());
			clazz.setCollectClassName(entity.getCollectClassName());
			collectClasses.add(clazz);
		}
		logger.info("结果集为:"+collectClasses);
		return collectClasses;
	}
	
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
	@Override
	public List<TaskClazzMenuEntity> getCollectClazzTree(String ownerId,
			String taskType) throws Exception {
		logger.info("查询项目的采集品类父类,项目ID:"+ownerId);
		List<TaskClazzMenuEntity> list=taskClazzCacheComponent.getCollectClazzTree(Long.valueOf(ownerId), taskType.equals("1")?false:true);
		logger.info("结果集为:"+list);
		return list;
	}

	
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
	@Override
	public void updateProjectStatus(String customId,String ownerId, String status)throws Exception {
		logger.info("启动or暂停项目信息--service,条件为:ownerId="+ownerId+",status="+status+"--start");
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setCustomId(customId);
		projectInfo.setId(ownerId);
		projectInfo.setStatus(Integer.valueOf(status));
		projectInfo.setDsKey(SysProps.getCCSingleDataSourceKey());
		projectInfoDao.updateProjectInfoStatus(projectInfo);
		//刷新缓存
		projectInfoCacheComponent.refresh();
		logger.info("启动or暂停项目信息--service--end");
	}
	
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月9日
	 * @description  获取所有客户信息或者获取项目负责人信息
	 * @return
	 * @throws Exception
	 */
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

	@Override
	public CollectTaskClazz getCollectClazz(String ownerId,
			String collectCLassId) throws Exception {
		// TODO Auto-generated method stub
		return taskClazzCacheComponent.getCollectTaskClazz(Long.valueOf(collectCLassId), Long.valueOf(ownerId));
	}



}
