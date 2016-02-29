package com.autonavi.cc.core.componet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import autonavi.online.framework.sharding.dao.DaoHelper;
import autonavi.online.framework.util.json.JsonBinder;

import com.autonavi.cc.api.bean.CollectTaskClazz;
import com.autonavi.cc.api.entity.TaskClazzMenuEntity;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.api.exception.BusinessExceptionEnum;
import com.autonavi.cc.core.dao.CollectClassDao;
import com.autonavi.cc.core.util.EnumConstant;

@Component
public class TaskClazzCacheComponent {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private CollectClassDao collectClassDao;
	//private CollectTaskCLazzDao collectTaskCLazzDao;
	@Autowired
	private RedisUtilComponent redisUtilComponent;
	@Autowired
	private ProjectInfoCacheComponent projectInfoCacheComponent;
	
	@PostConstruct
	private void init(){
		Set<Integer> dsSet=DaoHelper.getAllDSKey();
		for(Integer dsKey:dsSet){
			init(dsKey);
		}
	}
	@SuppressWarnings("unchecked")
	private void init(Integer dsKey){
		Jedis jedis=null;
		List<CollectTaskClazz> list=null;
		List<CollectTaskClazz> listAll=null;
		//总ownerIdSet 用来初始化数据 避免重复刷新缓存
		Set<Long> ownerSet=null;
		//主被动任务按照业主ID排列树
		Map<Long,List<TaskClazzMenuEntity>> activeOwnerMap=null;
		Map<Long,List<TaskClazzMenuEntity>> passiveOwnerMap=null;
		
		Map<Long,TaskClazzMenuEntity> activeEntitys=null;
		List<TaskClazzMenuEntity> tempActiveEntitysList=null;
		Map<Long,TaskClazzMenuEntity> passiveEntitys=null;
		List<TaskClazzMenuEntity> tempPassiveEntitysList=null;
		try {
			
			logger.info("开始初始化任务分类缓存");
			ownerSet=new HashSet<Long>();
			//获取全部任务缓存
			list=(List<CollectTaskClazz>)collectClassDao.selectAllVaildTaskClazz(dsKey);
			listAll=(List<CollectTaskClazz>)collectClassDao.selectAllTaskClazz(dsKey);
			if(list!=null){
				logger.info("共读取到使用中的分类信息"+list.size()+"条");
				logger.info("开始缓存使用中的每条分类信息");
				
				activeOwnerMap=new HashMap<Long,List<TaskClazzMenuEntity>>();
				passiveOwnerMap=new HashMap<Long,List<TaskClazzMenuEntity>>();
				
				activeEntitys=new HashMap<Long,TaskClazzMenuEntity>();
				tempActiveEntitysList=new ArrayList<TaskClazzMenuEntity>();
				passiveEntitys=new HashMap<Long,TaskClazzMenuEntity>();
				tempPassiveEntitysList=new ArrayList<TaskClazzMenuEntity>();
				for(CollectTaskClazz collectTaskClazz:list){
					//只要存在ownerId 就放入SET 后面用来补位
					ownerSet.add(collectTaskClazz.getOwnerId());
					if(collectTaskClazz.getTaskType()==null)collectTaskClazz.setTaskType(0);
					if(collectTaskClazz.getTaskType().equals(EnumConstant.TASK_TYPE.INITIATIVE.getCode())){
						if((collectTaskClazz.getParentId()==null || 0 == collectTaskClazz.getParentId())&&collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.MENU.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(false);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							activeEntitys.put(collectTaskClazz.getId(),entity);
							if(activeOwnerMap.get(collectTaskClazz.getOwnerId())==null){
								List<TaskClazzMenuEntity> activeEntitysList=new ArrayList<TaskClazzMenuEntity>();
								activeOwnerMap.put(collectTaskClazz.getOwnerId(), activeEntitysList);
							}
							activeOwnerMap.get(collectTaskClazz.getOwnerId()).add(entity);
						}
						else if((collectTaskClazz.getParentId()!=null && 0 != collectTaskClazz.getParentId())&&collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.MENU.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(false);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setPid(collectTaskClazz.getParentId());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							activeEntitys.put(collectTaskClazz.getId(),entity);
							TaskClazzMenuEntity _entity=activeEntitys.get(collectTaskClazz.getParentId());
							if(_entity!=null){
								_entity.getCollectClasses().add(entity);
							}else{
								//万一没有找到父级暂时缓存
								tempActiveEntitysList.add(entity);
							}
						}else if(collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.ITEM.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(true);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setCollectClassCount(collectTaskClazz.getClazzImgCount());
							entity.setCollectClassPay(collectTaskClazz.getClazzPay());
							entity.setCollectClassPayType(collectTaskClazz.getClazzPayType());
							entity.setCollectClassFarCount(collectTaskClazz.getClazzFarImgCount());
							entity.setCollectClassNearCount(collectTaskClazz.getClazzNearImgCount());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setPid(collectTaskClazz.getParentId());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							TaskClazzMenuEntity _entity=activeEntitys.get(collectTaskClazz.getParentId());
							if(_entity!=null){
								if(!_entity.getOwnerId().equals(entity.getOwnerId())){
									logger.warn("分类存在collectClassId["+entity.getCollectClassId()+"]问题,ownerId["+entity.getOwnerId()+"]和collectClassParentId["+collectTaskClazz.getParentId()+"]的ownerId["+_entity.getOwnerId()+"]不一致");
								}else{
									_entity.getCollectClasses().add(entity);
								}
							}else{
								//万一没有找到父级暂时缓存
								tempActiveEntitysList.add(entity);
							}
						}
					}
					else{
						if((collectTaskClazz.getParentId()==null || 0 == collectTaskClazz.getParentId())&&collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.MENU.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(false);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							passiveEntitys.put(collectTaskClazz.getId(),entity);
							if(passiveOwnerMap.get(collectTaskClazz.getOwnerId())==null){
								List<TaskClazzMenuEntity> passiveEntitysList=new ArrayList<TaskClazzMenuEntity>();
								passiveOwnerMap.put(collectTaskClazz.getOwnerId(), passiveEntitysList);
							}
							passiveOwnerMap.get(collectTaskClazz.getOwnerId()).add(entity);
						}
						else if(collectTaskClazz.getParentId()!=null&&0 != collectTaskClazz.getParentId() && collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.MENU.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(false);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setPid(collectTaskClazz.getParentId());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							passiveEntitys.put(collectTaskClazz.getId(),entity);
							TaskClazzMenuEntity _entity=passiveEntitys.get(collectTaskClazz.getParentId());
							if(_entity!=null){
								_entity.getCollectClasses().add(entity);
							}else{
								//万一没有找到父级暂时缓存
								tempPassiveEntitysList.add(entity);
							}
						}else if(collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.ITEM.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(true);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setCollectClassCount(collectTaskClazz.getClazzImgCount());
							entity.setCollectClassPay(collectTaskClazz.getClazzPay());
							entity.setCollectClassPayType(collectTaskClazz.getClazzPayType());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setPid(collectTaskClazz.getParentId());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							TaskClazzMenuEntity _entity=passiveEntitys.get(collectTaskClazz.getParentId());
							if(_entity!=null){
								if(!_entity.getOwnerId().equals(entity.getOwnerId())){
									logger.warn("分类存在collectClassId["+entity.getCollectClassId()+"]问题,ownerId["+entity.getOwnerId()+"]和collectClassParentId["+collectTaskClazz.getParentId()+"]的ownerId["+_entity.getOwnerId()+"]不一致");
								}else{
									_entity.getCollectClasses().add(entity);
								}
							}else{
								//万一没有找到父级暂时缓存
								tempPassiveEntitysList.add(entity);
							}
						}
					}
				}
				//整理暂时缓存的数据
				for(TaskClazzMenuEntity entity:tempActiveEntitysList){
					TaskClazzMenuEntity _entity=activeEntitys.get(entity.getPid());
					if(_entity!=null){
						if(!_entity.getOwnerId().equals(entity.getOwnerId())){
							logger.warn("分类存在collectClassId["+entity.getCollectClassId()+"]问题,ownerId["+entity.getOwnerId()+"]和collectClassParentId["+entity.getPid()+"]的ownerId["+_entity.getOwnerId()+"]不一致");
						}else{
							_entity.getCollectClasses().add(entity);
						}
					}else{
						if(entity.getPid()==null){
							activeOwnerMap.get(entity.getOwnerId()).add(entity);
						}
					}
				}
				for(TaskClazzMenuEntity entity:tempPassiveEntitysList){
					TaskClazzMenuEntity _entity=passiveEntitys.get(entity.getPid());
					if(_entity!=null){
						if(!_entity.getOwnerId().equals(entity.getOwnerId())){
							logger.warn("分类存在collectClassId["+entity.getCollectClassId()+"]问题,ownerId["+entity.getOwnerId()+"]和collectClassParentId["+entity.getPid()+"]的ownerId["+_entity.getOwnerId()+"]不一致");
						}else{
							_entity.getCollectClasses().add(entity);
						}
					}else{
						if(entity.getPid()==null){
							passiveOwnerMap.get(entity.getOwnerId()).add(entity);
						}
					}
				}
			}
			
			jedis=redisUtilComponent.getRedisInstance();
			logger.info("整合给客户端的主动任务分类信息");
			//平台化后缓存需要按照不同的业主分类
			for(Long ownerId:activeOwnerMap.keySet()){
				redisUtilComponent.setRedisJsonCache(jedis, EnumConstant.TASK_CLAZZ_INITIATIVE_MENU_CACHE_PREFIX+ownerId, activeOwnerMap.get(ownerId), JsonBinder.buildNonNullBinder(false),0);
			}
			//占位主动任务分类缓存
			for(Long ownerId:ownerSet){
				if(!activeOwnerMap.containsKey(ownerId)){
					//占位
					redisUtilComponent.setRedisJsonCache(jedis, EnumConstant.TASK_CLAZZ_INITIATIVE_MENU_CACHE_PREFIX+ownerId, new ArrayList<TaskClazzMenuEntity>() , JsonBinder.buildNonNullBinder(false),0);
				}
			}
			logger.info("整合给客户端的被动任务分类信息");
			//平台化后缓存需要按照不同的业主分类
			for(Long ownerId:passiveOwnerMap.keySet()){
				redisUtilComponent.setRedisJsonCache(jedis, EnumConstant.TASK_CLAZZ_PASSIVE_MENU_CACHE_PREFIX+ownerId, passiveOwnerMap.get(ownerId), JsonBinder.buildNonNullBinder(false),0);
			}
			//占位被动任务分类缓存
			for(Long ownerId:ownerSet){
				if(!passiveOwnerMap.containsKey(ownerId)){
					//占位
					redisUtilComponent.setRedisJsonCache(jedis, EnumConstant.TASK_CLAZZ_PASSIVE_MENU_CACHE_PREFIX+ownerId, new ArrayList<TaskClazzMenuEntity>() , JsonBinder.buildNonNullBinder(false),0);
				}
			}
			if(listAll!=null){
				logger.info("共读取到分类信息"+listAll.size()+"条");
				logger.info("开始缓存每条分类信息");
				for(CollectTaskClazz collectTaskClazz:listAll){
					redisUtilComponent.setRedisJsonCache(jedis, EnumConstant.TASK_CLAZZ_CACHE_PREFIX+collectTaskClazz.getId(), collectTaskClazz, JsonBinder.buildNormalBinder(false),0);
				}
			}
//			getCollectTaskClazz(643695172407787520L);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if (jedis != null) {
				redisUtilComponent.returnRedis(jedis);
			}
			if (list!=null){
				logger.info("清理缓存垃圾信息");
				list.clear();
				list=null;
			}
			if(activeEntitys!=null){
				activeEntitys.clear();
				activeEntitys=null;
			}
			if(activeOwnerMap!=null){
				activeOwnerMap.clear();
				activeOwnerMap=null;
			}
			if(tempActiveEntitysList!=null){
				tempActiveEntitysList.clear();
				tempActiveEntitysList=null;
			}
			if(passiveEntitys!=null){
				passiveEntitys.clear();
				passiveEntitys=null;
			}
			if(passiveOwnerMap!=null){
				passiveOwnerMap.clear();
				passiveOwnerMap=null;
			}
			if(tempPassiveEntitysList!=null){
				tempPassiveEntitysList.clear();
				tempPassiveEntitysList=null;
			}
		}
	}
	public void refresh(){
		this.init();
	}
	public void refresh(String clazzId,String customId)throws Exception{
		Jedis jedis=null;
		try{
			JsonBinder jb=JsonBinder.buildNormalBinder(false);
			CollectTaskClazz taskClazz=(CollectTaskClazz)collectClassDao.selectTaskClazzById(String.valueOf(clazzId),String.valueOf(customId));
			if(taskClazz==null){
				logger.warn("clazzId=["+clazzId+"] db is null ");
			}
			jedis=redisUtilComponent.getRedisInstance();
			Long ownerId=taskClazz.getOwnerId();
			//刷新树
			refreshOwnerIdTree(jedis,ownerId,customId,null);
			redisUtilComponent.setRedisJsonCache(jedis,EnumConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId
					, taskClazz, jb, 0);
		}finally{
			if (jedis != null) {
				redisUtilComponent.returnRedis(jedis);
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskClazzMenuEntity> refreshOwnerIdTree(Jedis jedis,Long ownerId,String customId,Boolean isPassive)throws Exception{
		List<CollectTaskClazz> list=null;
		Map<Long,TaskClazzMenuEntity> activeEntitys=null;
		List<TaskClazzMenuEntity> tempActiveEntitysList=null;
		List<TaskClazzMenuEntity> activeEntitysList=null;
		Map<Long,TaskClazzMenuEntity> passiveEntitys=null;
		List<TaskClazzMenuEntity> tempPassiveEntitysList=null;
		List<TaskClazzMenuEntity> passiveEntitysList=null;
		try{
			list=(List<CollectTaskClazz>)collectClassDao.selectVaildTaskClazzByOwnerId(String.valueOf(ownerId), customId);
			if(list!=null){
				activeEntitys=new HashMap<Long,TaskClazzMenuEntity>();
				tempActiveEntitysList=new ArrayList<TaskClazzMenuEntity>();
				passiveEntitys=new HashMap<Long,TaskClazzMenuEntity>();
				tempPassiveEntitysList=new ArrayList<TaskClazzMenuEntity>();
				logger.info("需要刷新ownerid["+ownerId+"]分类信息"+list.size()+"条");
				for(CollectTaskClazz collectTaskClazz:list){
					if(collectTaskClazz.getTaskType()==null)collectTaskClazz.setTaskType(0);
					if(collectTaskClazz.getTaskType().equals(EnumConstant.TASK_TYPE.INITIATIVE.getCode())){
						if((collectTaskClazz.getParentId()==null || 0 == collectTaskClazz.getParentId())&&collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.MENU.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(false);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							activeEntitys.put(collectTaskClazz.getId(),entity);
							if(activeEntitysList==null){
								activeEntitysList=new ArrayList<TaskClazzMenuEntity>();
							}
							activeEntitysList.add(entity);
						}else if((collectTaskClazz.getParentId()!=null && 0 != collectTaskClazz.getParentId())&&collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.MENU.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(false);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setPid(collectTaskClazz.getParentId());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							activeEntitys.put(collectTaskClazz.getId(),entity);
							TaskClazzMenuEntity _entity=activeEntitys.get(collectTaskClazz.getParentId());
							if(_entity!=null){
								_entity.getCollectClasses().add(entity);
							}else{
								//万一没有找到父级暂时缓存
								tempActiveEntitysList.add(entity);
							}
						}else if(collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.ITEM.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(true);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setCollectClassCount(collectTaskClazz.getClazzImgCount());
							entity.setCollectClassPay(collectTaskClazz.getClazzPay());
							entity.setCollectClassPayType(collectTaskClazz.getClazzPayType());
							entity.setCollectClassFarCount(collectTaskClazz.getClazzFarImgCount());
							entity.setCollectClassNearCount(collectTaskClazz.getClazzNearImgCount());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setPid(collectTaskClazz.getParentId());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							TaskClazzMenuEntity _entity=activeEntitys.get(collectTaskClazz.getParentId());
							if(_entity!=null){
								_entity.getCollectClasses().add(entity);
							}else{
								//万一没有找到父级暂时缓存
								tempActiveEntitysList.add(entity);
							}
						}
					}else{
						if((collectTaskClazz.getParentId()==null || 0 == collectTaskClazz.getParentId())&&collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.MENU.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(false);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							passiveEntitys.put(collectTaskClazz.getId(),entity);
							if(passiveEntitysList==null){
								passiveEntitysList=new ArrayList<TaskClazzMenuEntity>();
							}
							passiveEntitysList.add(entity);
						}else if((collectTaskClazz.getParentId()!=null && 0 != collectTaskClazz.getParentId())&&collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.MENU.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(false);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setPid(collectTaskClazz.getParentId());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							passiveEntitys.put(collectTaskClazz.getId(),entity);
							TaskClazzMenuEntity _entity=passiveEntitys.get(collectTaskClazz.getParentId());
							if(_entity!=null){
								_entity.getCollectClasses().add(entity);
							}else{
								//万一没有找到父级暂时缓存
								tempPassiveEntitysList.add(entity);
							}
						}else if(collectTaskClazz.getClazzType().equals(EnumConstant.TASK_CLAZZ_TYPE.ITEM.getCode())){
							TaskClazzMenuEntity entity=new TaskClazzMenuEntity();
							entity.setIsItem(true);
							entity.setCollectClassId(collectTaskClazz.getId());
							entity.setCollectClassName(collectTaskClazz.getClazzName());
							entity.setCollectClassCount(collectTaskClazz.getClazzImgCount());
							entity.setCollectClassPay(collectTaskClazz.getClazzPay());
							entity.setCollectClassPayType(collectTaskClazz.getClazzPayType());
							entity.setCollectClassFarCount(collectTaskClazz.getClazzFarImgCount());
							entity.setCollectClassNearCount(collectTaskClazz.getClazzNearImgCount());
							entity.setIndex(collectTaskClazz.getClazzIndex());
							entity.setPid(collectTaskClazz.getParentId());
							entity.setOwnerId(collectTaskClazz.getOwnerId());
							TaskClazzMenuEntity _entity=passiveEntitys.get(collectTaskClazz.getParentId());
							if(_entity!=null){
								_entity.getCollectClasses().add(entity);
							}else{
								//万一没有找到父级暂时缓存
								tempPassiveEntitysList.add(entity);
							}
						}
					}
					
				}
				//整理暂时缓存的数据
				for(TaskClazzMenuEntity entity:tempActiveEntitysList){
					TaskClazzMenuEntity _entity=activeEntitys.get(entity.getPid());
					if(_entity!=null){
						_entity.getCollectClasses().add(entity);
					}else{
						if(entity.getPid()==null){
							activeEntitysList.add(entity);
						}
					}
				}
				for(TaskClazzMenuEntity entity:tempPassiveEntitysList){
					TaskClazzMenuEntity _entity=passiveEntitys.get(entity.getPid());
					if(_entity!=null){
						_entity.getCollectClasses().add(entity);
					}else{
						if(entity.getPid()==null){
							passiveEntitysList.add(entity);
						}
					}
				}
				//组织抢座位
				if(activeEntitysList==null)activeEntitysList=new ArrayList<TaskClazzMenuEntity>();
				if(passiveEntitysList==null)passiveEntitysList=new ArrayList<TaskClazzMenuEntity>();
				redisUtilComponent.setRedisJsonCache(jedis, EnumConstant.TASK_CLAZZ_INITIATIVE_MENU_CACHE_PREFIX+ownerId, activeEntitysList, JsonBinder.buildNonNullBinder(false),0);
				redisUtilComponent.setRedisJsonCache(jedis, EnumConstant.TASK_CLAZZ_PASSIVE_MENU_CACHE_PREFIX+ownerId, passiveEntitysList, JsonBinder.buildNonNullBinder(false),0);
			}
			if(isPassive==null||!isPassive)
			    return activeEntitysList;
			else
				return passiveEntitysList;
		}catch(Exception e){
			throw e;
		}finally{
			if (list!=null){
				logger.info("清理缓存垃圾信息");
				list.clear();
				list=null;
			}
			if(tempActiveEntitysList!=null){
				tempActiveEntitysList.clear();
				tempActiveEntitysList=null;
			}
			if(tempPassiveEntitysList!=null){
				tempPassiveEntitysList.clear();
				tempPassiveEntitysList=null;
			}
		}
	}
	
	public void refresh(Long clazzId,Long ownerId)throws Exception{
		Jedis jedis=null;
		try{
			JsonBinder jb=JsonBinder.buildNormalBinder(false);
			String customId=projectInfoCacheComponent.getProjectInfo(String.valueOf(ownerId)).getCustomId();
			CollectTaskClazz taskClazz=(CollectTaskClazz)collectClassDao.selectTaskClazzById(String.valueOf(clazzId),customId);
			if(taskClazz==null){
				logger.warn("clazzId=["+clazzId+"] db is null ");
			}
			//刷新树
			refreshOwnerIdTree(jedis,ownerId,customId,null);
			jedis=redisUtilComponent.getRedisInstance();
			redisUtilComponent.setRedisJsonCache(EnumConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId
					, taskClazz, jb, 0);
		}finally{
			if (jedis != null) {
				redisUtilComponent.returnRedis(jedis);
			}
		}
		
	}
	
	public CollectTaskClazz getCollectTaskClazz(Long clazzId,Long ownerId)throws Exception{
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		CollectTaskClazz taskClazz=redisUtilComponent.getRedisJsonCache(EnumConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId, 
				CollectTaskClazz.class, jb);
		if(taskClazz==null){
			logger.warn("clazzId["+clazzId+"] not found from cache will get from db ");
			taskClazz=(CollectTaskClazz)collectClassDao.selectTaskClazzById(String.valueOf(clazzId),projectInfoCacheComponent.getProjectInfo(String.valueOf(ownerId)).getCustomId());
			if(taskClazz==null){
				logger.error("clazzId=["+clazzId+"] db is null ");
				throw new BusinessException(BusinessExceptionEnum.TASK_CLAZZ_NOT_FOUND);
			}
			redisUtilComponent.setRedisJsonCache(EnumConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId
					, taskClazz, jb, 0);
		}
		return taskClazz;
	}
	public String getCollectTaskClazzJson(Long clazzId,Long ownerId)throws Exception{
		String taskClazzJson=redisUtilComponent.getRedisStringCache(EnumConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId);
		if(taskClazzJson==null){
			JsonBinder jb=JsonBinder.buildNormalBinder(false);
			logger.warn("clazzId["+clazzId+"] not found from cache will get from db ");
			CollectTaskClazz taskClazz=(CollectTaskClazz)collectClassDao.selectTaskClazzById(String.valueOf(clazzId),projectInfoCacheComponent.getProjectInfo(String.valueOf(ownerId)).getCustomId());
			if(taskClazz==null){
				logger.error("clazzId=["+clazzId+"] db is null ");
				throw new BusinessException(BusinessExceptionEnum.TASK_CLAZZ_NOT_FOUND);
			}
			taskClazzJson=jb.toJson(taskClazz);
			redisUtilComponent.setRedisJsonCache(EnumConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId
					, taskClazz, jb, 0);
					
		}
		return taskClazzJson;
	}
	public String getCollectClazzTreeJson(Long ownerId,Boolean isPassive)throws Exception{
		Jedis jedis=null;
		try{
			String taskClazzJson="";
			if(isPassive==null||!isPassive){
				taskClazzJson=redisUtilComponent.getRedisStringCache(EnumConstant.TASK_CLAZZ_INITIATIVE_MENU_CACHE_PREFIX+ownerId);
			}else{
				taskClazzJson=redisUtilComponent.getRedisStringCache(EnumConstant.TASK_CLAZZ_PASSIVE_MENU_CACHE_PREFIX+ownerId);
			}
			 
			jedis=redisUtilComponent.getRedisInstance();
			if(taskClazzJson==null){
				JsonBinder jb=JsonBinder.buildNormalBinder(false);
				String customId=projectInfoCacheComponent.getProjectInfo(String.valueOf(ownerId)).getCustomId();
				List<TaskClazzMenuEntity> entityList=refreshOwnerIdTree(jedis,ownerId,customId,isPassive);
				if(entityList==null){
					return "";
				}
				return jb.toJson(entityList);
			}
			return taskClazzJson;
		}finally{
			if (jedis != null) {
				redisUtilComponent.returnRedis(jedis);
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<TaskClazzMenuEntity> getCollectClazzTree(Long ownerId,Boolean isPassive)throws Exception{
		String taskClazzJson=this.getCollectClazzTreeJson(ownerId, isPassive);
		if(taskClazzJson!=null&&!taskClazzJson.equals("")){
			JsonBinder jb=JsonBinder.buildNormalBinder(false);
			List<TaskClazzMenuEntity> list=jb.fromJson(taskClazzJson, List.class, jb.getCollectionType(List.class, TaskClazzMenuEntity.class));
			return list;
		}else{
			return new ArrayList<TaskClazzMenuEntity>();
		}
		
	}

}
