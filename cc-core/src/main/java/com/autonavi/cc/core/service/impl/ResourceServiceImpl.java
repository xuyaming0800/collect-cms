package com.autonavi.cc.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.autonavi.cc.api.entity.ResultEntity;
import com.autonavi.cc.api.service.ResourceService;
import com.autonavi.cc.api.util.HttpClientUtil;
import com.autonavi.cc.api.util.PropConstants;
import com.autonavi.cc.core.util.SysProps;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public Map<String, Object> queryInfoByUserName(String username)
			throws Exception {
		try {
			String json = HttpClientUtil.get(SysProps.getMessage("get_audit_userinfo_url") + "&username="
					+ username, null);
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,
					ResultEntity.class);
			Map<String, Object> userInfo = (Map<String, Object>) resultEntity
					.getInfo();
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 根据用户查询角色信息
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> queryRolesByUserName(String username) throws Exception {
		try {
			String json = HttpClientUtil.get(
					SysProps.getMessage("get_audit_role_url") + "&username=" + username, null);
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,
					ResultEntity.class);
			List info = (List) resultEntity.getInfo();
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 根据用户信息查询权限信息
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> queryPermissionsByUserName(String username)
			throws Exception {
		try {
			String json = HttpClientUtil.get(SysProps.getMessage("get_audit_permission_url") + "&username="
					+ username, null);
			ObjectMapper objectMapper = new ObjectMapper();

			ResultEntity resultEntity = objectMapper.readValue(json,
					ResultEntity.class);
			List info = (List) resultEntity.getInfo();
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
