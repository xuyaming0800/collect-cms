package com.autonavi.cc.core.componet;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import autonavi.online.framework.util.json.JsonBinder;

import com.autonavi.cc.core.util.EnumConstant;
@Component
public class CommonCacheComponent {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private RedisUtilComponent redisUtilComponent;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据角色code和系统Id查询用户
	 * @param roleCode
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Object> getUserList(String roleCode,String bsId) throws Exception {
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		List<Object> userList=redisUtilComponent.getRedisJsonCache(EnumConstant.USER_LIST_ROLE_BS_CACHE_PREFIX+roleCode+"_"+bsId, 
				List.class, jb);
		if(userList == null){
			logger.warn("roleCode=["+roleCode+"],bsId=["+bsId+"] cache is null ");
		}
		return userList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据系统类型获取用户 
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCustomList(String userType) throws Exception {
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		List<Object> userList=redisUtilComponent.getRedisJsonCache(EnumConstant.USER_LIST_TYPE_CACHE_PREFIX+userType, 
				List.class, jb);
		if(userList == null){
			logger.warn("userType=["+userType+"] cache is null ");
		}
		return userList;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据用户ID查询用户 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object getUser(String id)  throws Exception {
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		Object user =redisUtilComponent.getRedisJsonCache(EnumConstant.USER_INFO_CACHE_PREFIX+id, 
				Object.class, jb);
		if(user == null){
			logger.warn("id=["+id+"] cache is null ");
		}
		return user;
	}
	

}
