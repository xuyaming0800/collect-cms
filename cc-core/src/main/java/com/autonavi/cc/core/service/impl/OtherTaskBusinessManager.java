package com.autonavi.cc.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.autonavi.cc.api.entity.TaskClazzQueryEntity;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.core.componet.RedisUtilComponent;
@Component
public class OtherTaskBusinessManager {
	@Autowired
	private RedisUtilComponent redisUtilComponent;
	
	
	@Autowired
	private TaskClazzTreeQuery taskClazzTreeQuery;
	
	public TaskClazzTreeQuery getTaskClazzTreeQuery() {
		return taskClazzTreeQuery;
	}
	
	@Component
	public class TaskClazzTreeQuery  {

		@Autowired
		public TaskClazzTreeQuery(OtherTaskBusinessManager CollectTaskSubmitEntity) {

		}
		public String execute(TaskClazzQueryEntity entity)
				throws Exception {
			Jedis jedis=null;
			try {
				jedis=redisUtilComponent.getRedisInstance();
				return redisUtilComponent.getRedisStringCache(entity.getPrefix()+entity.getId().toString(),jedis);
			} finally {
				redisUtilComponent.returnRedis(jedis);
			}
			
		}
		
	}

}
