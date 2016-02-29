package com.autonavi.cc.core.componet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autonavi.cc.core.util.HttpRequestUtil;
@Component
public class CommonComponent {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private CommonCacheComponent commonCacheComponent;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取用户详细信息 
	 * @param id
	 * @param userMapAll
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Object getUser(String id,Map<String, Object> userMapAll,String url) throws Exception {
		Object  user = commonCacheComponent.getUser(id);
		if(user == null) {
			logger.warn("获取用户 id=["+id+"] cache is null");
			if(userMapAll == null) {
				userMapAll = HttpRequestUtil.getUsers(url, "");
			}
			if(userMapAll != null) {
				user = userMapAll.get(id);
			}
		}
		return user;
	}
	
	public List<String> getCustomIdList(String customName,String userType,String url)  throws Exception{
		List<String> idList = new ArrayList<String>();
		List<Object> customList = commonCacheComponent.getCustomList(userType);
		if(customList == null || customList.size() == 0) {
			Map<String,Object> customMap = HttpRequestUtil.getUsers(url,customName);
			if(customMap != null) {
				for(Entry<String,Object > entry : customMap.entrySet()) {
					Map user = (Map)entry.getValue();
					idList.add((String)user.get("id"));
				}
			}
		}else {
			for(Object obj : customList) {
				Map custom = (Map)obj;
				if(((String)custom.get("name")).contains(customName)) {
					idList.add((String)custom.get("id"));
				}
			}
		}
		return idList;
	}

}
