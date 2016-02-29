package com.autonavi.cc.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.autonavi.cc.api.service.CommonService;
import com.autonavi.cc.api.util.PropConstants;
import com.autonavi.cc.core.componet.CommonCacheComponent;
import com.autonavi.cc.core.util.HttpRequestUtil;
import com.autonavi.cc.core.util.SysProps;
@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private CommonCacheComponent commonCacheComponent;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询客户信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getCustomList(String userName) throws Exception {
		logger.info("查询客户信息,条件:userName="+userName);
		List<Object> customList = new ArrayList<Object>();
		List<Object> objectList = commonCacheComponent.getCustomList(SysProps.getMessage("customUsertype"));
		if(objectList == null) {
			String param = "userName="+userName;
			objectList = HttpRequestUtil.getUserList(SysProps.getMessage("get_custom_url"), param);
			if(objectList  != null) {
				customList = objectList;
			}
		}else {
			for(Object obj : objectList) {
				Map custom = (Map)obj;
				if(((String)custom.get("name")).contains(userName)) {
					customList.add(obj);
				}
			}
		}
		return customList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询项目负责人信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getProjectLeaderList(String userName) throws Exception {
		logger.info("查询项目负责人信息,条件:userName="+userName);
		List<Object> projectLeaderList = new ArrayList<Object>();
		List<Object> objectList = commonCacheComponent.getUserList(SysProps.getMessage("projectLeaderRoleCode"), SysProps.getMessage("projectLeaderBsId"));
		if(objectList == null) {
			String param = "userName="+userName;
			objectList = HttpRequestUtil.getUserList(SysProps.getMessage("get_project_leader_url"), param);
			if(objectList != null) {
				projectLeaderList = objectList;
			}
		}else {
			for(Object obj : objectList) {
				Map projectLeader = (Map)obj;
				if(((String)projectLeader.get("name")).contains(userName)) {
					projectLeaderList.add(obj);
				}
			}
		}
		return projectLeaderList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月27日
	 * @description 查询品类价格
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @param customId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getCollectClassPrice(String ownerId,
			String collectClassParentId, String collectClassId, String customId)
			throws Exception {
		logger.info("查询采集品类价格,条件:ownerId="+ownerId+",collectClassParentId="+collectClassParentId+",collectClassId="+collectClassId+",customId="+customId);
		String param = "ownerId="+ownerId+"&collectClassParentId="+collectClassParentId+"&collectClassId="+collectClassId+"&customId="+customId;
		Object collectClassPrice = HttpRequestUtil.getCollectClassPrice(SysProps.getMessage("get_collectClassPrice_url"), param);
		return collectClassPrice;
	}
	
	
	
}
