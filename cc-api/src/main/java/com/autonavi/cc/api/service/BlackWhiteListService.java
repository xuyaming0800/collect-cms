package com.autonavi.cc.api.service;

import com.autonavi.cc.api.bean.BlackWhiteList;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.bean.ProjectInfo;
import com.autonavi.cc.api.entity.ProjectInfoEntity;

/**
 * 项目管理 增加，删除，修改，查询
 * @author wenpeng.jin
 *
 */
public interface BlackWhiteListService {
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 条件分页查询项目
	 * @param projectInfoEntity
	 * @return
	 * @throws Exception
	 */
	public Pagination queryBlackWhiteList(ProjectInfoEntity  projectInfoEntity)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 保存黑白名单信息
	 * @param blackWhiteList
	 * @throws Exception
	 */
	public void saveBlackWhiteList( BlackWhiteList  blackWhiteList) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新黑白名单基本信息
	 * @param blackWhiteList
	 * @throws Exception
	 */
	public void updateBlackWhiteList( BlackWhiteList  blackWhiteList) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新黑白名单的状态
	 * @param blackWhiteList
	 * @throws Exception
	 */
	public void updateBlackWhiteListStatus( BlackWhiteList  blackWhiteList) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 验证是否存在项目
	 * @param blackWhiteList
	 * @return
	 * @throws Exception
	 */
	public boolean checkUniqueProject(BlackWhiteList blackWhiteList)  throws Exception;

}
