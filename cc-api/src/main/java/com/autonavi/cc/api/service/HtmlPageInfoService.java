package com.autonavi.cc.api.service;

import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.entity.DetailConfigEntity;

/**
 * html 页面 增加，修改，查询
 * @author wenpeng.jin
 *
 */
public interface HtmlPageInfoService {
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 根据客户ID查询html页面信息
	 * @param detailConfigEntity
	 * @return
	 */
	public Pagination queryHtmlPageInfos(DetailConfigEntity detailConfigEntity) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 根据客户ID查询html总记录数
	 * @param detailConfigEntity
	 * @return
	 */
	public long queryHtmlPageInfosCount(DetailConfigEntity detailConfigEntity) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 查询html历史版本列表
	 * @param detailConfigEntity
	 * @return
	 * @throws Exception
	 */
	public Pagination queryHistoryHtmls(DetailConfigEntity detailConfigEntity) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 查询html历史版本总记录数
	 * @param detailConfigEntity
	 * @return
	 * @throws Exception
	 */
	public long queryHistoryHtmlsCount(DetailConfigEntity detailConfigEntity) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description  根据客户ID，采集品类大类ID和采集品类小类ID,版本号等信息查询html页面信息
	 * @param detailConfigEntity
	 * @return
	 * @throws Exception
	 */
	public DetailConfigEntity queryHtmlPageInfo(DetailConfigEntity detailConfigEntity) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 下载html
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @param entranceStatus
	 * @return
	 * @throws Exception
	 */
	public DetailConfigEntity downloadHtml(String ownerId, String collectClassParentId, String collectClassId,String entranceStatus,String versionNo) throws Exception;
	/**
	 * 获取最终的版本号
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @return
	 * @throws Exception
	 */
	public DetailConfigEntity getLastVersionNo(String ownerId, String collectClassParentId, String collectClassId,String entranceStatus) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 获取html webFLowText
	 * @param ownerId
	 * @param collectClassParentId
	 * @return
	 * @throws Exception
	 */
	public DetailConfigEntity getWebFlowText(String ownerId, String collectClassParentId) throws Exception;
	/**
	 * 
	 * @author yaming.xu
	 * @date 2015年10月30日
	 * @description 获取html webFLowText
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @param versionNo
	 * @return
	 * @throws Exception
	 */
	public DetailConfigEntity getWebFlowText(String ownerId, String collectClassParentId,String collectClassId,String versionNo) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月28日
	 * @description 验证最大版本号
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @param versionNo
	 * @param entranceStatus
	 * @return
	 * @throws Exception
	 */
	public boolean checkVersionNo(String ownerId, String collectClassParentId, String collectClassId,String versionNo,String entranceStatus) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 保存html页面信息
	 * @param detailConfigEntity
	 * @throws Exception
	 */
	public DetailConfigEntity saveHtmlPageInfo(DetailConfigEntity detailConfigEntity)throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 更新HTML页面的状态
	 * @param detailConfigEntity
	 * @throws Exception
	 */
	public void updateHtmlPageInfoStatus(DetailConfigEntity detailConfigEntity) throws Exception;
	/**
	 * 发布项目
	 * @param ownerId
	 * @throws Exception
	 */
	public void publishProject(String ownerId,String customId)throws Exception;

}
