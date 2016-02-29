package com.autonavi.cc.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autonavi.cc.api.bean.BlackWhiteList;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.bean.ProjectInfo;
import com.autonavi.cc.api.entity.ProjectInfoEntity;
import com.autonavi.cc.api.service.BlackWhiteListService;
import com.autonavi.cc.core.dao.BlackWhiteListDao;
import com.autonavi.cc.core.util.SysProps;
/**
 * 黑白名单管理增删改
 * @author wenpeng.jin
 *
 */
@Service("blackWhiteListService")
public class BlackWhiteListServiceImpl implements BlackWhiteListService {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private BlackWhiteListDao blackWhiteListDao;
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
	public Pagination queryBlackWhiteList(ProjectInfoEntity projectInfoEntity) throws Exception {
		logger.info("分页查询黑白名单信息，查询条件projectName"+projectInfoEntity.getProjectName()+"，blackWhiteListType："+projectInfoEntity.getBlackWhiteListType()+"--service--start");
		BlackWhiteList blackWhiteList = new BlackWhiteList();
		blackWhiteList.setProjectName(projectInfoEntity.getProjectName());
		if(projectInfoEntity.getBlackWhiteListType() == null || "".equals(projectInfoEntity.getBlackWhiteListType())) {
			blackWhiteList.setBlackWhiteListType(9);//不根据黑白名单状态查询
		}else {
			blackWhiteList.setBlackWhiteListType(Integer.valueOf(projectInfoEntity.getBlackWhiteListType()));
		}
		int pageNo = projectInfoEntity.getPageNo();
		int limit = projectInfoEntity.getLimit();
		if(limit == 0) {
			limit = Integer.valueOf(SysProps.getMessage(SysProps.LIMIT));
		}
		Pagination page = new Pagination(pageNo, limit);

		List<BlackWhiteList> blackWhiteLists = (List<BlackWhiteList>)blackWhiteListDao.queryBlackWhiteList(blackWhiteList,page.getStart(), page.getLimit());

		logger.info("分页查询黑白名单信息,结果集为："+blackWhiteLists.toString());
		page.setObjectList(blackWhiteLists);
		long totalCount = (Long)blackWhiteListDao.queryBlackWhiteListCount(blackWhiteList);
		logger.info("分页查询黑白名单信息,总记录数为："+totalCount);
		page.setTotalCount(totalCount);
		logger.info("分页查询黑白名单信息--service--end");
		return page;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 保存黑白名单信息
	 * @param blackWhiteList
	 * @throws Exception
	 */
	@Override
	public void saveBlackWhiteList(BlackWhiteList blackWhiteList) throws Exception {
		logger.info("保存黑白名单信息--service");
		blackWhiteList.setCreateTime(new Date().getTime());
		blackWhiteListDao.saveBlackWhiteList(blackWhiteList);
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新黑白名单基本信息
	 * @param blackWhiteList
	 * @throws Exception
	 */
	@Override
	public void updateBlackWhiteList(BlackWhiteList blackWhiteList) throws Exception {
		logger.info("更新黑白名单基本信息--service");
		blackWhiteList.setUpdateTime(new Date().getTime());
		blackWhiteListDao.updateBlackWhiteList(blackWhiteList);
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新黑白名单的状态
	 * @param blackWhiteList
	 * @throws Exception
	 */
	@Override
	public void updateBlackWhiteListStatus(BlackWhiteList blackWhiteList) throws Exception {
		logger.info("更新黑白名单状态信息--service");
		blackWhiteList.setUpdateTime(new Date().getTime());
		blackWhiteListDao.updateBlackWhiteListStatus(blackWhiteList);
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 验证是否存在项目
	 * @param blackWhiteList
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean checkUniqueProject(BlackWhiteList blackWhiteList)  throws Exception{
		boolean flag = true;
		List<BlackWhiteList> blackWhiteLists = (List<BlackWhiteList>)blackWhiteListDao.checkUniqueProject(blackWhiteList);
		if(blackWhiteLists.size() == 0) {
			flag = false;
		}
		return flag;
	}
	
	
}
