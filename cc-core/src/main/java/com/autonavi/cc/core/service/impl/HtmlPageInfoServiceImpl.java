package com.autonavi.cc.core.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autonavi.online.framework.sharding.dao.DaoHelper;
import autonavi.online.framework.util.json.JsonBinder;

import com.autonavi.cc.api.bean.CollectClass;
import com.autonavi.cc.api.bean.DetailConfig;
import com.autonavi.cc.api.bean.HtmlPageInfo;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.bean.WebFlowNode;
import com.autonavi.cc.api.entity.CollectClassEntity;
import com.autonavi.cc.api.entity.DetailConfigEntity;
import com.autonavi.cc.api.entity.TypeEntity;
import com.autonavi.cc.api.exception.BusinessCode;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.api.exception.BusinessExceptionEnum;
import com.autonavi.cc.api.service.CCApiToolsService;
import com.autonavi.cc.api.service.DetailConfigService;
import com.autonavi.cc.api.service.HtmlPageInfoService;
import com.autonavi.cc.core.componet.ProjectInfoCacheComponent;
import com.autonavi.cc.core.dao.CollectClassDao;
import com.autonavi.cc.core.dao.DetailConfigDao;
import com.autonavi.cc.core.dao.HtmlPageInfoDao;
import com.autonavi.cc.core.util.CommonUtil;
import com.autonavi.cc.core.util.EnumConstant;
import com.autonavi.cc.core.util.EnumConstant.COLLECT_CLASS_ENTRANCE_STATUS;
import com.autonavi.cc.core.util.SysProps;
/**
 * html 页面 增加，修改，查询
 * @author wenpeng.jin
 *
 */
@Service("htmlPageInfoService")
public class HtmlPageInfoServiceImpl implements HtmlPageInfoService {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private HtmlPageInfoDao htmlPageInfoDao;
	@Autowired
	private DetailConfigService detailConfigService;
	@Autowired
	private DetailConfigDao detailConfigDao;
	
	@Autowired
	private CollectClassDao collectClassDao;
	
	@Autowired
	private CCApiToolsService cCApiToolsService;
	@Autowired
	public ProjectInfoCacheComponent projectInfoCacheComponent;

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 根据客户ID查询html页面信息
	 * @param ownerId
	 * @param collectClassId
	 * @param collectClassId
	 * @return
	 */
	@Override
	public Pagination queryHtmlPageInfos(DetailConfigEntity detailConfigEntity) throws Exception {
		logger.info(detailConfigEntity.toString());
		logger.info("条件查询HTML页面信息s-service");
		HtmlPageInfo htmlPageInfo = new HtmlPageInfo();
		htmlPageInfo.setOwnerId(detailConfigEntity.getOwnerId());
		htmlPageInfo.setCollectClassParentId(detailConfigEntity.getCollectClassParentId());
		htmlPageInfo.setCollectClassId(detailConfigEntity.getCollectClassId());
		htmlPageInfo.setCustomId(detailConfigEntity.getCustomId());
		int pageNo = detailConfigEntity.getPageNo();
		int limit = detailConfigEntity.getLimit();
		if(limit == 0) {
			limit = Integer.valueOf(SysProps.getMessage(SysProps.LIMIT));
		}
		Pagination page = new Pagination(pageNo, limit);
		List<HtmlPageInfo> htmlList = (List<HtmlPageInfo>)htmlPageInfoDao.queryHtmlPageInfos(htmlPageInfo,page.getStart(), page.getLimit());
//		long totalCount=DaoHelper.getCount();
		logger.info(htmlList.toString());
		long totalCount = (Long)htmlPageInfoDao.queryHtmlPageInfosCount(htmlPageInfo);
		logger.info("总记录数："+totalCount);
		page.setTotalCount(totalCount);
		page.setObjectList(htmlList);
		logger.info(page.toString());
		return page;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 根据客户ID查询html总记录数
	 * @param detailConfigEntity
	 * @return
	 */
	public long queryHtmlPageInfosCount(DetailConfigEntity detailConfigEntity) throws Exception{
		logger.info(detailConfigEntity.toString());
		logger.info("条件查询HTML页面总记录数-service");
		HtmlPageInfo htmlPageInfo = new HtmlPageInfo();
		htmlPageInfo.setOwnerId(detailConfigEntity.getOwnerId());
		htmlPageInfo.setCustomId(detailConfigEntity.getCustomId());
		htmlPageInfo.setCollectClassParentId(detailConfigEntity.getCollectClassParentId());
		htmlPageInfo.setCollectClassId(detailConfigEntity.getCollectClassId());
		long totalCount = (Long)htmlPageInfoDao.queryHtmlPageInfosCount(htmlPageInfo);
		logger.info("条件查询HTML页面总记录数："+totalCount);
		return totalCount;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 查询html历史版本列表
	 * @param detailConfigEntity
	 * @return
	 * @throws Exception
	 */
	public Pagination queryHistoryHtmls(DetailConfigEntity detailConfigEntity) throws Exception{
		logger.info(detailConfigEntity.toString());
		logger.info("查询HTML历史版本-start-service");
		HtmlPageInfo htmlPageInfo = new HtmlPageInfo();
		htmlPageInfo.setOwnerId(detailConfigEntity.getOwnerId());
		htmlPageInfo.setCollectClassParentId(detailConfigEntity.getCollectClassParentId());
		htmlPageInfo.setCollectClassId(detailConfigEntity.getCollectClassId());
		htmlPageInfo.setCustomId(detailConfigEntity.getCustomId());
		int pageNo = detailConfigEntity.getPageNo();
		int limit = detailConfigEntity.getLimit();
		if(limit == 0) {
			limit = Integer.valueOf(SysProps.getMessage(SysProps.LIMIT));
		}
		Pagination page = new Pagination(pageNo, limit);
		List<HtmlPageInfo> htmlList =  (List<HtmlPageInfo>)htmlPageInfoDao.queryHistoryHtmls(htmlPageInfo,page.getStart(), page.getLimit());
//		long totalCount = DaoHelper.getCount();
		logger.info(htmlList.toString());
		long totalCount = (Long)htmlPageInfoDao.queryHistoryHtmlsCount(htmlPageInfo);
		logger.info("总记录数："+totalCount);
		page.setTotalCount(totalCount);
		page.setObjectList(htmlList);
		logger.info("查询HTML历史版本-end-service");
		logger.info(page.toString());
		return page;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 查询html历史版本总记录数
	 * @param detailConfigEntity
	 * @return
	 * @throws Exception
	 */
	public long queryHistoryHtmlsCount(DetailConfigEntity detailConfigEntity) throws Exception{
		logger.info(detailConfigEntity.toString());
		logger.info("查询HTML历史版本总记录数-service");
		HtmlPageInfo htmlPageInfo = new HtmlPageInfo();
		htmlPageInfo.setOwnerId(detailConfigEntity.getOwnerId());
		htmlPageInfo.setCollectClassParentId(detailConfigEntity.getCollectClassParentId());
		htmlPageInfo.setCollectClassId(detailConfigEntity.getCollectClassId());
		long totalCount = (Long)htmlPageInfoDao.queryHistoryHtmlsCount(htmlPageInfo);
		logger.info("查询HTML历史版本总记录数："+totalCount);
		return totalCount;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description  根据客户ID，采集品类大类ID和采集品类小类ID查询html页面信息
	 * @param ownerId
	 * @param collectClassId
	 * @param collectClassId
	 * @param versionNo
	 * @return
	 * @throws Exception
	 */
	public DetailConfigEntity queryHtmlPageInfo(DetailConfigEntity detailConfigEntity) throws Exception {
		logger.info("根据项目ID："+detailConfigEntity.getOwnerId()+"和采集品类大类ID："+
	      detailConfigEntity.getCollectClassParentId()+"和采集品类小类ID："+detailConfigEntity.getCollectClassId()+
	      "和版本号："+detailConfigEntity.getVersionNo()+"查询html页面信息start-service");
		DetailConfigEntity dce = new DetailConfigEntity();
		//组装查询条件
		HtmlPageInfo hpTmp = new HtmlPageInfo();
		hpTmp.setOwnerId(detailConfigEntity.getOwnerId());
		hpTmp.setCollectClassParentId(detailConfigEntity.getCollectClassParentId());
		hpTmp.setCollectClassId(detailConfigEntity.getCollectClassId());
		hpTmp.setVersionNo(detailConfigEntity.getVersionNo());
		hpTmp.setCustomId(detailConfigEntity.getCustomId());
		HtmlPageInfo htmlPageInfo = (HtmlPageInfo)htmlPageInfoDao.queryHtmlPageInfo(hpTmp);
		if(htmlPageInfo != null) {
			dce.setId(htmlPageInfo.getId());
			//dce.setHtmlText(htmlPageInfo.getHtmlText());
			dce.setJsText(htmlPageInfo.getJsText() == null ? "": htmlPageInfo.getJsText());
			dce.setCssText(htmlPageInfo.getCssText() == null ? "" : htmlPageInfo.getCssText());
			dce.setVersionNo(htmlPageInfo.getVersionNo());
			dce.setCustomId(hpTmp.getCustomId());
			logger.info("查询html页面组件详细信息start-service");
			queryDetailCofigs(dce, hpTmp);
			logger.info("查询html页面组件详细信息end-service");
		}else {
			dce.setDatas(new ArrayList<TypeEntity>());
		}
		logger.info("根据客户ID和采集品类ID查询html页面信息end-service");
		logger.info(dce.toString());
		return dce;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月25日
	 * @description 下载html
	 * @param detailConfigEntity
	 * @return
	 * @throws Exception
	 */
	public DetailConfigEntity downloadHtml(String ownerId, String collectClassParentId, String collectClassId,String entranceStatus,String versionNo) throws Exception{
		logger.info("进入downloadHtml方法，根据项目ID："+ownerId+"和采集品类大类ID："+
	            collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+
				entranceStatus+" 版本为:"+versionNo+" 查询HTML页面start-service");
		DetailConfigEntity dce = new DetailConfigEntity();
		dce.setCollectClassId(collectClassId);
		dce.setCollectClassParentId(collectClassParentId);
		dce.setOwnerId(ownerId);
		//组装查询条件
		HtmlPageInfo hpTmp = new HtmlPageInfo();
		hpTmp.setOwnerId(ownerId);
		hpTmp.setCollectClassParentId(collectClassParentId);
		hpTmp.setCollectClassId(collectClassId);
		hpTmp.setCustomId(projectInfoCacheComponent.getProjectInfo(ownerId).getCustomId());
		if(versionNo!=null&&!versionNo.equals("")){
			hpTmp.setVersionNo(versionNo);
		}
		HtmlPageInfo htmlPageInfo = null;
		if(EnumConstant.COLLECT_CLASS_ENTRANCE_STATUS.NotEntrance.getCode() == Integer.valueOf(entranceStatus)) {//下载非入口页
			 htmlPageInfo = (HtmlPageInfo)htmlPageInfoDao.queryHtmlPageInfo(hpTmp);
		}else if(EnumConstant.COLLECT_CLASS_ENTRANCE_STATUS.Entrance.getCode() == Integer.valueOf(entranceStatus)){//下载入口页
			CollectClass clazz=(CollectClass)collectClassDao.queryCollectEntranceClass(ownerId, hpTmp.getCustomId(), collectClassParentId, Integer.valueOf(entranceStatus));
			hpTmp.setCollectClassId(clazz.getId());
			htmlPageInfo = (HtmlPageInfo)htmlPageInfoDao.queryHtmlPageInfo(hpTmp);
//			htmlPageInfo =  (HtmlPageInfo)htmlPageInfoDao.queryHtmlPageInfoByEntranceStatus(hpTmp, Integer.valueOf(entranceStatus));
		}
		
		if(htmlPageInfo != null) {
			logger.info(htmlPageInfo.toString());
			dce.setId(htmlPageInfo.getId());
			dce.setHtmlText(replaceText(URLEncoder.encode(htmlPageInfo.getHtmlText(), "utf-8")));
			dce.setJsText(replaceText(URLEncoder.encode(htmlPageInfo.getJsText() == null ? "": htmlPageInfo.getJsText(), "utf-8")));
			dce.setCssText(replaceText(URLEncoder.encode(htmlPageInfo.getCssText() == null ? "" : htmlPageInfo.getCssText(), "utf-8")));
			dce.setVersionNo(htmlPageInfo.getVersionNo());
		}else {
			throw new BusinessException(BusinessExceptionEnum.QUERY_IS_NULL);
		}
		dce.setRestrainInfos(getRestrainInfos(ownerId, collectClassParentId));
		
		logger.info(dce.toString());
		logger.info("进入downloadHtml方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"查询HTML页面end-service");
		logger.info(dce.toString());
		return dce;
	}
	
	private List<CollectClassEntity> getRestrainInfos(String ownerId, String collectClassParentId) throws Exception{
		logger.info("获取采集品类约束条件start");
		CollectClass collectClass = new CollectClass();
		collectClass.setOwnerId(ownerId);
		collectClass.setCollectClassParentId(collectClassParentId);
		List<CollectClass> ccList = (List<CollectClass>)collectClassDao.queryCollectClass(collectClass, projectInfoCacheComponent.getProjectInfo(ownerId).getCustomId());
		List<CollectClassEntity> cceList = new ArrayList<CollectClassEntity>();
		for(CollectClass cc : ccList) {
			CollectClassEntity cce = new CollectClassEntity();
			cce.setId(cc.getId());
			cce.setCollectCount(cc.getCollectCount());
			cce.setIsRequired(cc.getIsRequired());
			cceList.add(cce);
		}
		logger.info("获取采集品类约束条件end");
		return cceList;
	}
	
	
	public DetailConfigEntity getWebFlowText(String ownerId, String collectClassParentId) throws Exception{
		logger.info("进入getWebFlowText方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"查询HTML页面跳转内容-webflowxml--start-service");
		DetailConfigEntity dce = new DetailConfigEntity();
		dce.setCollectClassParentId(collectClassParentId);
		dce.setOwnerId(ownerId);
		String customId=projectInfoCacheComponent.getProjectInfo(ownerId).getCustomId();
		CollectClass collectClass = new CollectClass();
		collectClass.setOwnerId(ownerId);
		collectClass.setCollectClassParentId(collectClassParentId);
		List<CollectClass> navCcList = (List<CollectClass>)collectClassDao.queryNavCollectClass(collectClass,customId );
		if(navCcList != null && navCcList.size() > 0){
			collectClass = navCcList.get(0);
		}else {
			throw new BusinessException((int)BusinessCode.NO_HTML5_NAV,cCApiToolsService.getExceptionMessage(String.valueOf(BusinessCode.NO_HTML5_NAV)));
		}
		
		//组装查询条件
		HtmlPageInfo hpTmp = new HtmlPageInfo();
		hpTmp.setOwnerId(ownerId);
		hpTmp.setCollectClassParentId(collectClassParentId);
		hpTmp.setCustomId(customId);
		List<HtmlPageInfo>  htmlPageInfos = (List<HtmlPageInfo>)htmlPageInfoDao.queryHtmlPageInfosByCCPID(hpTmp);
		String webFlowXml = makeWebFlowXml(htmlPageInfos,collectClass);
		dce.setWebFlowText(webFlowXml);
		logger.info("进入getWebFlowText方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"查询HTML页面跳转内容-webflowxml--end-service");
		logger.info(dce.toString());
		return dce;

	}
	
	public DetailConfigEntity getWebFlowText(String ownerId, String collectClassParentId,String collectClassId,String versionNo) throws Exception{
		logger.info("进入getWebFlowText-new方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"查询HTML页面跳转内容-webflowxml--start-service");
		DetailConfigEntity dce = new DetailConfigEntity();
		dce.setCollectClassParentId(collectClassParentId);
		dce.setOwnerId(ownerId);
		String customId=projectInfoCacheComponent.getProjectInfo(ownerId).getCustomId();
		CollectClass collectClass =(CollectClass)collectClassDao.queryCollectClassById(collectClassId, ownerId, customId, collectClassParentId);
		if(collectClass==null){
			throw new BusinessException((int)BusinessCode.NO_HTML5_NAV,cCApiToolsService.getExceptionMessage(String.valueOf(BusinessCode.NO_HTML5_NAV)));
		}
		//组装查询条件
		HtmlPageInfo hpTmp = new HtmlPageInfo();
		hpTmp.setOwnerId(ownerId);
		hpTmp.setCollectClassParentId(collectClassParentId);
		hpTmp.setCustomId(customId);
		if(collectClass.getEntranceStatus()==COLLECT_CLASS_ENTRANCE_STATUS.Entrance.getCode()){
			List<HtmlPageInfo>  htmlPageInfos = (List<HtmlPageInfo>)htmlPageInfoDao.queryHtmlPageInfosByCCPID(hpTmp);
			String webFlowXml = makeWebFlowXmlNew(htmlPageInfos,collectClass);
			dce.setWebFlowText(webFlowXml);
		}else{
			hpTmp.setCollectClassId(collectClassId);
			hpTmp.setVersionNo(versionNo);
			HtmlPageInfo htmlPageInfo=(HtmlPageInfo)htmlPageInfoDao.queryHtmlPageInfosById(hpTmp);
			String webFlowXml = makeWebFlowXmlNew(htmlPageInfo,collectClass);
			dce.setWebFlowText(webFlowXml);
		}
		
		logger.info("进入getWebFlowText方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"查询HTML页面跳转内容-webflowxml--end-service");
		logger.info(dce.toString());
		return dce;

	}
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
	public boolean checkVersionNo(String ownerId, String collectClassParentId, String collectClassId,String versionNo,String entranceStatus) throws Exception{
		//组装查询条件
		HtmlPageInfo htmlPageInfo = new HtmlPageInfo();
		htmlPageInfo.setOwnerId(ownerId);
		htmlPageInfo.setCollectClassParentId(collectClassParentId);
		htmlPageInfo.setCollectClassId(collectClassId);
		htmlPageInfo.setCustomId(projectInfoCacheComponent.getProjectInfo(ownerId).getCustomId());
		logger.info("进入checkVersionNo方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"查询HTML页面最大版本号start-service");

		logger.info("根据客户ID和采集品类ID查询html页面信息最大版本号start-service");
		String maxVersionNo = null;
		if(EnumConstant.COLLECT_CLASS_ENTRANCE_STATUS.NotEntrance.getCode() == Integer.valueOf(entranceStatus)) {//下载非入口页
			maxVersionNo = (String)htmlPageInfoDao.queryMaxVersionNo(htmlPageInfo);
		}else if(EnumConstant.COLLECT_CLASS_ENTRANCE_STATUS.Entrance.getCode() == Integer.valueOf(entranceStatus)){//下载入口页
			CollectClass clazz=(CollectClass)collectClassDao.queryCollectEntranceClass(ownerId, htmlPageInfo.getCustomId(), collectClassParentId, Integer.valueOf(entranceStatus));
			htmlPageInfo.setCollectClassId(clazz.getId());
			maxVersionNo = (String)htmlPageInfoDao.queryMaxVersionNo(htmlPageInfo);
		}
		logger.info("进入checkVersionNo方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"查询HTML页面最大版本号end-service");
		logger.info("根据客户ID和采集品类ID查询html页面信息最大版本号end,最大版本号为："+maxVersionNo+"，当前版本号为："+versionNo+"-service");
		if(maxVersionNo == null || "".equals(maxVersionNo) ) {
			return false;
		}
		if(versionNo == null || "null".equals(versionNo) || "".equals(versionNo)) {
			return true;
		}
		if(!maxVersionNo.equals(versionNo)) {
			logger.info("存在新版本号,最大版本号为："+maxVersionNo+"，当前版本号为："+versionNo);
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月18日
	 * @description 查询页面组件信息 
	 * @param dce
	 * @param ownerId
	 * @param versionNo
	 */
	public void queryDetailCofigs(DetailConfigEntity dce,HtmlPageInfo htmlPageInfo) {
		logger.info("根据客户ID："+htmlPageInfo.getOwnerId()+",版本号："+htmlPageInfo.getVersionNo()+" 查询组件信息start-service");
		DetailConfig detailConfig = new DetailConfig();
		detailConfig.setOwnerId(htmlPageInfo.getOwnerId());
		detailConfig.setVersionNo(htmlPageInfo.getVersionNo());
		detailConfig.setCustomId(dce.getCustomId());
		List<DetailConfig> detailConfigs = (List<DetailConfig>)detailConfigDao.queryDetailConfigsByVersionNo(detailConfig);
		logger.info("获取的组件信息："+detailConfigs.toString());
		List<TypeEntity> datas = new ArrayList<TypeEntity>();
		if(detailConfigs.size() > 0) {
			dce.setVersionNo(detailConfigs.get(0).getVersionNo());
			for(DetailConfig dc : detailConfigs) {
				TypeEntity typeEntity = new TypeEntity();
				JsonBinder binder= JsonBinder.buildNormalBinder(false);
				Object jsonText=binder.fromJson(dc.getJsonText(),Object.class);
				typeEntity.setJsonText(jsonText);
				typeEntity.setType(dc.getType());
				datas.add(typeEntity);
			}
			dce.setDatas(datas);
		}else {
			logger.info("根据客户ID："+htmlPageInfo.getOwnerId()+",版本号："+htmlPageInfo.getVersionNo()+" 查询组件信息为空-service");
		}
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 保存html页面信息
	 * @param detailConfigEntity
	 * @throws Exception
	 */
	@Override
	public DetailConfigEntity saveHtmlPageInfo(DetailConfigEntity detailConfigEntity)
			throws Exception {
		logger.info(detailConfigEntity.toString());
		logger.info("保存html页面信息start-service");
		String versionNo = detailConfigEntity.getVersionNo();
		//组件详细信息版本号存在，先删除历史数据，在保存新数据 重新生成版本号
		logger.info("保存html页面组件信息start-service");
		detailConfigService.saveDetailConfigs(detailConfigEntity);
		logger.info("保存html页面组件信息end-service");
		logger.info("获取生成的版本号为："+versionNo);
		HtmlPageInfo htmlPageInfo = new HtmlPageInfo();
		String id = String.valueOf(CommonUtil.getUniqueId());
		htmlPageInfo.setId(id);
		htmlPageInfo.setOwnerId(detailConfigEntity.getOwnerId());
		htmlPageInfo.setCollectClassParentId(detailConfigEntity.getCollectClassParentId());
		htmlPageInfo.setCollectClassId(detailConfigEntity.getCollectClassId());
		htmlPageInfo.setHtmlText(detailConfigEntity.getHtmlText());
		htmlPageInfo.setJsText(detailConfigEntity.getJsText());
		htmlPageInfo.setCssText(detailConfigEntity.getCssText());
		htmlPageInfo.setVersionNo(versionNo);
		htmlPageInfo.setCreateTime(new Date().getTime());
		htmlPageInfo.setCustomId(detailConfigEntity.getCustomId());
		//------------------暂时新增webflow页面跳转信息---------------start
		
		WebFlowNode indexNode= null;
		WebFlowNode backNode = new WebFlowNode();
		
		//判断是否为入口页
		CollectClass collectClass=(CollectClass)collectClassDao.queryCollectClassById(detailConfigEntity.getCollectClassId(), 
				detailConfigEntity.getOwnerId(), detailConfigEntity.getCustomId(),detailConfigEntity.getCollectClassParentId());
		if(collectClass.getEntranceStatus()==COLLECT_CLASS_ENTRANCE_STATUS.Entrance.getCode()){
			//入口页面 设置
			indexNode=new WebFlowNode();
			backNode.setOn("back");
			backNode.setTo("myTaskPage");
			indexNode.setOn("index");
			indexNode.setTo("makeTaskPage");
		}else{
			//小类页面 设置
			backNode.setOn("back");
			indexNode=new WebFlowNode();
			indexNode.setOn("index");
			indexNode.setTo("makeTaskPage");
		}
		List<WebFlowNode> nodes = new ArrayList<WebFlowNode>();
		nodes.add(backNode);
		if(indexNode!=null){
			nodes.add(indexNode);
		}
		String webFlowText = makeWebFlowTextNew(detailConfigEntity.getCollectClassId(), nodes);
		htmlPageInfo.setWebFlowText(webFlowText);
		//------------------暂时新增webflow页面跳转信息---------------end
		htmlPageInfoDao.saveHtmlPageInfo(htmlPageInfo);
		logger.info("保存html页面信息end-service");
		//Long id = DaoHelper.getPrimaryKey();
		
		detailConfigEntity.setVersionNo(versionNo);
		detailConfigEntity.setId(String.valueOf(id));
		detailConfigEntity.setHtmlText(null);
		detailConfigEntity.setDatas(null);
		return detailConfigEntity;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 更新HTML页面的状态
	 * @param detailConfigEntity
	 * @throws Exception
	 */
	@Override
	public void updateHtmlPageInfoStatus(DetailConfigEntity detailConfigEntity) throws Exception {
		logger.info(detailConfigEntity.toString());
		HtmlPageInfo htmlPageInfo = new HtmlPageInfo();
		htmlPageInfo.setOwnerId(detailConfigEntity.getOwnerId());
		htmlPageInfo.setId(detailConfigEntity.getId());
		htmlPageInfo.setStatus(detailConfigEntity.getStatus());
		htmlPageInfo.setCustomId(detailConfigEntity.getCustomId());
		logger.info("更新html页面状态信息start-service");
		htmlPageInfoDao.updateHtmlPageInfoStatus(htmlPageInfo);
		logger.info("更新html页面状态信息end-service");
	}
	
	//解决urlencoder加密时 空格变加号 
	private String replaceText(String str){
		str=str.replaceAll("\\+", "%20");//替换空格
		return str;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 组装页面跳转流字符
	 * @param collectClassId
	 * @param nodes
	 * @return
	 */
	public String makeWebFlowText(String collectClassId,List<WebFlowNode> nodes) {
		logger.info("进入makeWebFlowText方法，开始组装WebFlowText信息start");
		StringBuffer sb = new StringBuffer();
		if(nodes != null && nodes.size() > 0) {
			sb.append("<node id=\""+collectClassId+"\">");
			for(int i = 0 ; i < nodes.size(); i++) {
				WebFlowNode  node = nodes.get(i);
				sb.append("<forward on=\""+node.getOn()+"\" to=\""+node.getTo()+"\"/>");
			}
			sb.append("<forward on=\"exit\" to=\"exit\"/>");
			sb.append("</node>");
		}
		logger.info(sb.toString());
		logger.info("进入makeWebFlowText方法，开始组装WebFlowText信息end");
		
		return sb.toString();
	}
	public String makeWebFlowTextNew(String collectClassId,List<WebFlowNode> nodes) {
		logger.info("进入makeWebFlowText方法，开始组装WebFlowText信息start");
		StringBuffer sb = new StringBuffer();
		if(nodes != null && nodes.size() > 0) {
			for(int i = 0 ; i < nodes.size(); i++) {
				WebFlowNode  node = nodes.get(i);
				if(node.getTo()!=null&&!node.getTo().equals(""))
				    sb.append("<forward on=\""+node.getOn()+"\" to=\""+node.getTo()+"\"/>");
				else
					sb.append("<forward on=\""+node.getOn()+"\"/>");
			}
		}
		logger.info(sb.toString());
		logger.info("进入makeWebFlowText方法，开始组装WebFlowText信息end");
		
		return sb.toString();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 组装页面跳转流 xml信息 供客户端下载获取
	 * @param htmlPageInfos
	 * @return
	 */
	public String makeWebFlowXml(List<HtmlPageInfo>  htmlPageInfos,CollectClass collectClass ) { 
		logger.info("进入makeWebFlowXml方法，开始组装makeWebFlowXml信息start");
		StringBuffer sb = new StringBuffer();
		if(htmlPageInfos != null && htmlPageInfos.size() > 0) {
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<webflow>");
			sb.append("<node id=\""+collectClass.getId()+"\" index=\"true\">");
			StringBuffer nodeSb = new StringBuffer();
			for(int i = 0 ; i < htmlPageInfos.size(); i++) {
				HtmlPageInfo  htmlPageInfo = htmlPageInfos.get(i);
				if(!htmlPageInfo.getCollectClassId().equals(collectClass.getId())){
					sb.append(" <forward on=\""+htmlPageInfo.getCollectClassId()+"_click"+"\" to=\""+htmlPageInfo.getCollectClassId()+"\"/>");
					String text=htmlPageInfo.getWebFlowText() == null ? "":htmlPageInfo.getWebFlowText();
					if(text.indexOf("<forward on=\"save\" to=\"navigation\"/><forward on=\"back\" to=\"navigation\"/><forward on=\"exit\" to=\"exit\"/>")!=-1){
						//旧版本
						nodeSb.append(htmlPageInfo.getWebFlowText() == null ? "":htmlPageInfo.getWebFlowText());
					}else{
						//新版本使用旧接口的话采用旧模式
						nodeSb.append("<node id=\""+htmlPageInfo.getCollectClassId()+"\">");
						nodeSb.append("<forward on=\"save\" to=\"navigation\"/><forward on=\"back\" to=\"navigation\"/><forward on=\"exit\" to=\"exit\"/>");
						nodeSb.append("</node>");
					}
					
				}	
			}
			sb.append("<forward on=\"back\" to=\"exit\"/>");
			sb.append(" </node>");
			sb.append(nodeSb.toString());
			sb.append("</webflow>");
		}
		logger.info(sb.toString());
		logger.info(sb.toString().replaceAll("navigation", collectClass.getId()));
		logger.info("进入makeWebFlowText方法，开始组装WebFlowText信息end");
		return sb.toString().replaceAll("navigation", collectClass.getId());
	}
	public String makeWebFlowXmlNew(List<HtmlPageInfo>  htmlPageInfos,CollectClass collectClass ) { 
		logger.info("进入makeWebFlowXml方法，开始组装makeWebFlowXml信息start");
		StringBuffer sb = new StringBuffer();
		if(htmlPageInfos != null && htmlPageInfos.size() > 0) {
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<node id=\""+collectClass.getId()+"_$versionNo$\">");
			String version="";
			for(HtmlPageInfo  htmlPageInfo:htmlPageInfos) {
				if(!collectClass.getId().equals(htmlPageInfo.getCollectClassId())){
					sb.append(" <forward on=\""+htmlPageInfo.getCollectClassId()+"_click"+"\" to=\""+htmlPageInfo.getCollectClassId()+"_"+htmlPageInfo.getVersionNo()+"\"/>");		
				}else{
					String text=htmlPageInfo.getWebFlowText() == null ? "":htmlPageInfo.getWebFlowText();
					if(text.indexOf("<forward on=\"save\" to=\"navigation\"/><forward on=\"back\" to=\"navigation\"/><forward on=\"exit\" to=\"exit\"/>")!=-1){
						//旧模式 往新模式自动整合 为了兼容
						sb.append("<forward on=\"back\" to=\"myTaskPage\"/><forward on=\"index\" to=\"makeTaskPage\"/>");
					}else{
						sb.append(htmlPageInfo.getWebFlowText()==null?"":htmlPageInfo.getWebFlowText());	
					}
					version=htmlPageInfo.getVersionNo();
				}
			}
			sb.append(" </node>");
			logger.info(sb.toString());
			logger.info(sb.toString().replaceAll("\\$versionNo\\$", version));
			logger.info("进入makeWebFlowText方法，开始组装WebFlowText信息end");
			return sb.toString().replaceAll("\\$versionNo\\$", version);
		}
		
		return "";
	}
	public String makeWebFlowXmlNew(HtmlPageInfo htmlPageInfo,CollectClass collectClass){
		logger.info("进入makeWebFlowXml方法，开始组装makeWebFlowXml信息start");
		StringBuffer sb = new StringBuffer();
		if(htmlPageInfo!=null) {
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<node id=\""+collectClass.getId()+"_"+htmlPageInfo.getVersionNo()+"\">");
			String text=htmlPageInfo.getWebFlowText() == null ? "":htmlPageInfo.getWebFlowText();
			if(text.indexOf("<forward on=\"save\" to=\"navigation\"/><forward on=\"back\" to=\"navigation\"/><forward on=\"exit\" to=\"exit\"/>")!=-1){
				//旧模式 往新模式自动整合 为了兼容
				sb.append("<forward on=\"back\"/><forward on=\"index\" to=\"makeTaskPage\"/>");
			}else{
				sb.append(htmlPageInfo.getWebFlowText()==null?"":htmlPageInfo.getWebFlowText());	
			}
			sb.append(" </node>");
		}
		logger.info(sb.toString());
		logger.info("进入makeWebFlowText方法，开始组装WebFlowText信息end");
		return sb.toString();
	}
	@Override
	public DetailConfigEntity getLastVersionNo(String ownerId,
			String collectClassParentId, String collectClassId,String entranceStatus)
			throws Exception {
		DetailConfigEntity dce = new DetailConfigEntity();
		dce.setCollectClassId(collectClassId);
		dce.setCollectClassParentId(collectClassParentId);
		dce.setOwnerId(ownerId);
		//组装查询条件
		HtmlPageInfo hpTmp = new HtmlPageInfo();
		hpTmp.setOwnerId(ownerId);
		hpTmp.setCollectClassParentId(collectClassParentId);
		hpTmp.setCollectClassId(collectClassId);
		hpTmp.setCustomId(projectInfoCacheComponent.getProjectInfo(ownerId).getCustomId());
		HtmlPageInfo htmlPageInfo=null;
		if(EnumConstant.COLLECT_CLASS_ENTRANCE_STATUS.NotEntrance.getCode() == Integer.valueOf(entranceStatus)) {//下载非入口页
			htmlPageInfo= (HtmlPageInfo)htmlPageInfoDao.queryLastVersionNo(hpTmp);
		}else{
			CollectClass clazz=(CollectClass)collectClassDao.queryCollectEntranceClass(ownerId, hpTmp.getCustomId(), collectClassParentId, Integer.valueOf(entranceStatus));
			hpTmp.setCollectClassId(clazz.getId());
			htmlPageInfo= (HtmlPageInfo)htmlPageInfoDao.queryLastVersionNo(hpTmp);
//			htmlPageInfo = (HtmlPageInfo)htmlPageInfoDao.queryLastVersionNo(hpTmp,entranceStatus);
		}
		
		if(htmlPageInfo != null) {
			logger.info(htmlPageInfo.toString());
			dce.setVersionNo(htmlPageInfo.getVersionNo());
			dce.setCollectClassId(htmlPageInfo.getCollectClassId());
		}else {
			throw new BusinessException(BusinessExceptionEnum.QUERY_IS_NULL);
		}
		
		logger.info(dce.toString());
		logger.info("进入getLastVersionNo方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"查询HTML页面end-service");
		logger.info(dce.toString());
		return dce;
	}
	@Override
	public void publishProject(String ownerId,String customId) throws Exception {
		// TODO Auto-generated method stub
		logger.info("发布页面项目的H5页面");
		HtmlPageInfo htmlPageInfo = new HtmlPageInfo();
		htmlPageInfo.setOwnerId(ownerId.toString());
		htmlPageInfo.setCustomId(customId);
		htmlPageInfoDao.publishProject(htmlPageInfo);
	}

}
