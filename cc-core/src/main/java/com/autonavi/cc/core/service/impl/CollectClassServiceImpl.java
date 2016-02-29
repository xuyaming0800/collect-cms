package com.autonavi.cc.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autonavi.online.framework.sharding.dao.DaoHelper;

import com.autonavi.cc.api.bean.CollectClass;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.entity.ProjectInfoEntity;
import com.autonavi.cc.api.entity.TaskClazzQueryEntity;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.api.service.CollectClassService;
import com.autonavi.cc.core.componet.TaskClazzCacheComponent;
import com.autonavi.cc.core.dao.CollectClassDao;
import com.autonavi.cc.core.util.EnumConstant;
import com.autonavi.cc.core.util.SysProps;
/**
 * 项目管理增删改
 * @author wenpeng.jin
 *
 */
@Service("collectClassService")
public class CollectClassServiceImpl implements CollectClassService {
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private CollectClassDao collectClassDao;
	@Autowired
	public TaskClazzCacheComponent taskClazzCacheComponent;
	
//	@Autowired
//	private OtherTaskBusinessManager otherTaskBusinessManager;
//	
//	@Autowired
//	private TaskClazzCacheComponent taskClazzCacheComponent;
//
//	public String getCollectTaskInitiativeClazzTree(String uuid)throws Exception  {
//		TaskClazzQueryEntity entity=new TaskClazzQueryEntity();
//		if(uuid!=null)entity.setId(uuid);
//		entity.setPrefix(EnumConstant.TASK_CLAZZ_INITIATIVE_MENU_CACHE_PREFIX);
//		String json=otherTaskBusinessManager.getTaskClazzTreeQuery().execute(entity);
//		if(json==null){
//			taskClazzCacheComponent.refresh();
//			json=otherTaskBusinessManager.getTaskClazzTreeQuery().execute(entity);
//		}
//		return json;
//	}
//
//	public String getCollectTaskPassiveClazzTree(String uuid)throws Exception {
//		TaskClazzQueryEntity entity=new TaskClazzQueryEntity();
//		if(uuid!=null)entity.setId(uuid);
//		entity.setPrefix(EnumConstant.TASK_CLAZZ_PASSIVE_MENU_CACHE_PREFIX);
//		String json=otherTaskBusinessManager.getTaskClazzTreeQuery().execute(entity);
//		if(json==null){
//			taskClazzCacheComponent.refresh();
//			json=otherTaskBusinessManager.getTaskClazzTreeQuery().execute(entity);
//		}
//		return json;
//	}

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description  分页查询品类
	 * @param projectInfoEntity
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination queryCollectClass(ProjectInfoEntity  projectInfoEntity) throws Exception {
		logger.info("分页查询采集品类,查询条件ownerId："+projectInfoEntity.getCollectClass().getOwnerId()+"，父ID为："+projectInfoEntity.getCollectClass().getCollectClassParentId()+"--service-start");
		CollectClass collectClass = new CollectClass();
		collectClass.setOwnerId(projectInfoEntity.getCollectClass().getOwnerId());
		collectClass.setCollectClassParentId(projectInfoEntity.getCollectClass().getCollectClassParentId());
		int pageNo = projectInfoEntity.getPageNo();
		int limit = projectInfoEntity.getLimit();
		if(limit == 0) {
			limit = Integer.valueOf(SysProps.getMessage(SysProps.LIMIT));
		}
		Pagination page = new Pagination(pageNo, limit);
		List<CollectClass> collectClassList = (List<CollectClass>)collectClassDao.queryCollectClass(collectClass, projectInfoEntity.getCustomId(), page.getStart(), page.getLimit());
//		long totalCount = DaoHelper.getCount();
		logger.info("分页查询采集品类，结果集为："+collectClassList.toString());
		page.setObjectList(collectClassList);
		long totalCount = (Long)collectClassDao.queryCollectClassCount(collectClass, projectInfoEntity.getCustomId());
		logger.info("分页查询采集品类，总记录数："+totalCount);
		page.setTotalCount(totalCount);
		logger.info("分页查询采集品类--service-end");
		return page;
	}

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description 保存品类
	 * @param projectInfoEntity
	 * @throws Exception
	 */
	@Override
	public void saveCollectClass(ProjectInfoEntity projectInfoEntity) throws Exception {
		logger.info("保存采集品类--service");
		CollectClass collectClass = projectInfoEntity.getCollectClass();
//		String id = String.valueOf(UniqueIDHolder.getIdWorker().nextId(Miscellaneous.getMyid()));
//		collectClass.setId(id);
//		collectClass.setInitClassId(id);
		collectClass.setCreateTime(new Date().getTime());
		if(collectClass.getCollectClassType()==1){
			//小类需要根据父类获取一个主被动标记
			collectClass.setTaskType(taskClazzCacheComponent.
					getCollectTaskClazz(Long.valueOf(collectClass.getCollectClassParentId()), 
							Long.valueOf(collectClass.getOwnerId())).getTaskType().toString());
		}
		collectClassDao.saveCollectClass(collectClass, projectInfoEntity.getCustomId());
		collectClass.setId(String.valueOf(DaoHelper.getPrimaryKey()));
		taskClazzCacheComponent.refresh(collectClass.getId(),projectInfoEntity.getCustomId());
	}

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description 更新品类基本信息
	 * @param projectInfoEntity
	 * @throws Exception
	 */
	@Override
	public void updateCollectClass(ProjectInfoEntity projectInfoEntity) throws Exception {
		logger.info("更新采集品类--service");
		CollectClass collectClass = projectInfoEntity.getCollectClass();
		collectClass.setUpdateTime(new Date().getTime());
		collectClassDao.updateCollectClass(collectClass, projectInfoEntity.getCustomId());
		taskClazzCacheComponent.refresh(collectClass.getId(),projectInfoEntity.getCustomId());
	}

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description 更新品类状态
	 * @param projectInfoEntity
	 * @throws Exception
	 */
	@Override
	public void updateCollectClassStatus(ProjectInfoEntity projectInfoEntity)	throws Exception {
		logger.info("删除采集品类--service");
		CollectClass collectClass = projectInfoEntity.getCollectClass();
		collectClass.setUpdateTime(new Date().getTime());
		collectClassDao.updateCollectClassStatus(collectClass, projectInfoEntity.getCustomId());
		taskClazzCacheComponent.refresh(collectClass.getId(),projectInfoEntity.getCustomId());
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 更新品类入口状态
	 * @param projectInfoEntity
	 * @throws Exception
	 */
	@Override
	public void updateCollectClassEntranceStatus(ProjectInfoEntity projectInfoEntity)	throws Exception {
		logger.info("更新采集品类入口状态--service");
		CollectClass collectClass = projectInfoEntity.getCollectClass();
		collectClass.setUpdateTime(new Date().getTime());
		collectClassDao.updateAllCollectClassEntranceStatus(collectClass, projectInfoEntity.getCustomId());
		collectClassDao.updateCollectClassEntranceStatus(collectClass, projectInfoEntity.getCustomId());
	}

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
	@Override
	public List<CollectClass> queryCollectClassChild(String customId,
			String ownerId, String collectClassParentId) throws Exception {
		logger.info("根据ownerId和collectclassParentId查询所有子类,查询条件ownerId："+ownerId+"，父ID为："+collectClassParentId+"--service-start");
		CollectClass collectClass = new CollectClass();
		collectClass.setOwnerId(ownerId);
		collectClass.setCollectClassParentId(collectClassParentId);
		List<CollectClass> collectClassList = (List<CollectClass>)collectClassDao.queryCollectClassChild(collectClass,customId);
		logger.info("根据ownerId和collectclassParentId查询所有子类，结果集为："+collectClassList.toString());
		logger.info("根据ownerId和collectclassParentId查询所有子类--service-end");
		return collectClassList;
	}
	



}
