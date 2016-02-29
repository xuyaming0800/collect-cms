package com.autonavi.cc.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.entity.DetailConfigEntity;
import com.autonavi.cc.api.entity.ResultEntity;
import com.autonavi.cc.api.exception.BusinessCode;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.api.exception.ExceptionCode;
import com.autonavi.cc.api.service.CCApiToolsService;
import com.autonavi.cc.api.service.DetailConfigService;
import com.autonavi.cc.api.service.HtmlPageInfoService;

@Controller
@RequestMapping("/htmlPage")
public class HtmlPageInfoController {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private HtmlPageInfoService htmlPageInfoService;
	
	@Autowired
	private DetailConfigService detailConfigService;
	
	@Autowired
	private CCApiToolsService cCApiToolsService;
	
	/**
	 * html5配置主页
	 * @author wenpeng.jin
	 * @date 2015年8月18日
	 * @description 
	 * @return
	 */
	@RequestMapping("/main")
	public String mainPage() {
		return "html/main";
	}
	
	@RequestMapping("/htmlbulider")
	public String htmlbuliderPage() {
		return "html/htmlbulider";
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 查询html列表
	 * @param detailConfigEntity
	 * @return
	 */
	@RequestMapping("/queryHtmlPageInfos")
	public @ResponseBody ResultEntity queryHtmlPageInfos(@RequestBody DetailConfigEntity detailConfigEntity) {
		logger.info(detailConfigEntity.toString());
		logger.info("进入queryHtmlPageInfos方法，查询HTML页面s-start");
		ResultEntity result = new ResultEntity();
		try{
//			String check=checkNull(new String[]{detailConfigEntity.getOwnerId()},new String[]{"ownerId"});
//			if(!check.equals("")){
//				logger.error("校验失败："+check);
//				result.setSuccess(false);
//				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
//				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
//				return result;
//			}
			Pagination page = htmlPageInfoService.queryHtmlPageInfos(detailConfigEntity);
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入queryHtmlPageInfos方法，查询HTML页面s-end");
		}catch(BusinessException e){
				result.setSuccess(false);  
				result.setCode(e.getErrorCode()+"");
				result.setDesc(e.getErrorMessage());
				logger.error("进入queryHtmlPageInfos方法，系统内容异常",e);
				return result;
			}catch(Exception e){
				result.setSuccess(false);
				result.setCode(ExceptionCode.SYSTEM_ERROR+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				logger.error("进入queryHtmlPageInfos方法条件查询HTML页面s异常",e);
			}
		logger.info("完成queryHtmlPageInfos方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 查询html历史版本列表
	 * @param detailConfigEntity
	 * @return
	 */
	@RequestMapping("/queryHistoryHtmls")
	public @ResponseBody ResultEntity queryHistoryHtmls(@RequestBody DetailConfigEntity detailConfigEntity) {
		logger.info(detailConfigEntity.toString());
		logger.info("进入queryHistoryHtmls方法，根据项目ID："+detailConfigEntity.getOwnerId()+"和采集品类大类ID："+detailConfigEntity.getCollectClassParentId()+"和采集品类小类ID："+detailConfigEntity.getCollectClassId()+" 查询HTML历史版本记录-start");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{detailConfigEntity.getOwnerId(),detailConfigEntity.getCollectClassParentId(),detailConfigEntity.getCollectClassId(),detailConfigEntity.getCustomId()},new String[]{"ownerId","collectClassParentId","collectClassId","customId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			Pagination page  = htmlPageInfoService.queryHistoryHtmls(detailConfigEntity);
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入queryHistoryHtmls方法 ，根据项目ID："+detailConfigEntity.getOwnerId()+"和采集品类大类ID："+detailConfigEntity.getCollectClassParentId()+"和采集品类小类ID："+detailConfigEntity.getCollectClassId()+"查询HTML历史版本记录-end");
		}catch(BusinessException e){
				result.setSuccess(false);  
				result.setCode(e.getErrorCode()+"");
				result.setDesc(e.getErrorMessage());
				logger.error("进入queryHistoryHtmls方法，系统内容异常",e);
				return result;
			}catch(Exception e){
				result.setSuccess(false);
				result.setCode(ExceptionCode.SYSTEM_ERROR+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				logger.error("进入queryHistoryHtmls方法 ，根据项目ID："+detailConfigEntity.getOwnerId()+"和采集品类大类ID："+detailConfigEntity.getCollectClassParentId()+"和采集品类小类ID："+detailConfigEntity.getCollectClassId()+"查询HTML历史版本记录异常",e);
			}
		logger.info("完成queryHistoryHtmls方法操作，"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 查询html内容
	 * @param detailConfigEntity
	 * @return
	 */
	@RequestMapping("/queryHtmlPageInfo")
	public @ResponseBody ResultEntity queryHtmlPageInfo(@RequestBody DetailConfigEntity detailConfigEntity) {
		logger.info(detailConfigEntity.toString());
		logger.info("进入queryHtmlPageInfo方法，根据项目ID："+detailConfigEntity.getOwnerId()+"和采集品类大类ID："+detailConfigEntity.getCollectClassParentId()+"和采集品类小类ID："+detailConfigEntity.getCollectClassId()+"和版本号："+detailConfigEntity.getVersionNo()+"查询HTML页面start");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{detailConfigEntity.getOwnerId(),detailConfigEntity.getCollectClassParentId(),detailConfigEntity.getCollectClassId()},new String[]{"ownerId","collectClassParentId","collectClassId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			DetailConfigEntity dce  = htmlPageInfoService.queryHtmlPageInfo(detailConfigEntity);
			result.setSuccess(true);
			result.setInfo(dce);
			logger.info("进入queryHtmlPageInfo方法，根据项目ID："+detailConfigEntity.getOwnerId()+"和采集品类大类ID："+detailConfigEntity.getCollectClassParentId()+"和采集品类小类ID："+detailConfigEntity.getCollectClassId()+"和版本号："+detailConfigEntity.getVersionNo()+"查询HTML页面end");
		}catch(BusinessException e){
				result.setSuccess(false);  
				result.setCode(e.getErrorCode()+"");
				result.setDesc(e.getErrorMessage());
				logger.error("进入queryHtmlPageInfo方法，系统内容异常",e);
				return result;
			}catch(Exception e){
				result.setSuccess(false);
				result.setCode(ExceptionCode.SYSTEM_ERROR+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				logger.error("进入queryHtmlPageInfo方法，根据项目ID："+detailConfigEntity.getOwnerId()+"和采集品类大类ID："+detailConfigEntity.getCollectClassParentId()+"和采集品类小类ID："+detailConfigEntity.getCollectClassId()+"和版本号："+detailConfigEntity.getVersionNo()+"查询HTML页面s异常",e);
			}
		logger.info("完成queryHtmlPageInfo方法操作，"+result.toString());
		return result;
	}
	@RequestMapping("/getVersionNo")
	public @ResponseBody ResultEntity getVersionNo(@RequestParam("ownerId") String ownerId, 
			@RequestParam("collectClassParentId") String collectClassParentId, 
			@RequestParam(value="collectClassId",required=false,defaultValue="0") String collectClassId,
			@RequestParam(value="entranceStatus", required=false, defaultValue="0") String entranceStatus){
		logger.info("进入getVersionNo方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"查询版本号页面start");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{ownerId,collectClassParentId,collectClassId},new String[]{"ownerId","collectClassParentId","collectClassId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			DetailConfigEntity dce  = htmlPageInfoService.getLastVersionNo(ownerId,collectClassParentId,collectClassId,entranceStatus);
			result.setSuccess(true);
			result.setInfo(dce);
			logger.info("进入getVersionNo方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"查询HTML页面end");
		}catch(BusinessException e){
				result.setSuccess(false);  
				result.setCode(e.getErrorCode()+"");
				result.setDesc(e.getErrorMessage());
				logger.error("进入getVersionNo方法，系统内容异常",e);
				return result;
			}catch(Exception e){
				result.setSuccess(false);  
				result.setCode(ExceptionCode.SYSTEM_ERROR+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				logger.error("进入downloadHtml方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"查询HTML页面异常",e);
			}
		logger.info("完成getVersionNo方法操作，"+result.toString());
		return result;
	}
	
	
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月25日
	 * @description 下载HTML
	 * @param detailConfigEntity
	 * @return
	 */
	@RequestMapping("/downloadHtml")
	public @ResponseBody ResultEntity downloadHtml(
			@RequestParam("ownerId") String ownerId, 
			@RequestParam("collectClassParentId") String collectClassParentId, 
			@RequestParam(value="collectClassId",required=false,defaultValue="0") String collectClassId,
			@RequestParam(value="entranceStatus", required=false, defaultValue="0") String entranceStatus,
			@RequestParam(value="versionNo", required=false) String versionNo) {
		logger.info("进入downloadHtml方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"查询HTML页面start");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{ownerId,collectClassParentId,collectClassId,entranceStatus},new String[]{"ownerId","collectClassParentId","collectClassId","entranceStatus"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			DetailConfigEntity dce  = htmlPageInfoService.downloadHtml(ownerId,collectClassParentId,collectClassId,entranceStatus,versionNo);
			result.setSuccess(true);
			result.setInfo(dce);
			logger.info("进入downloadHtml方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"查询HTML页面end");
		}catch(BusinessException e){
				result.setSuccess(false);  
				result.setCode(e.getErrorCode()+"");
				result.setDesc(e.getErrorMessage());
				logger.error("进入downloadHtml方法，系统内容异常",e);
				return result;
			}catch(Exception e){
				result.setSuccess(false);  
				result.setCode(ExceptionCode.SYSTEM_ERROR+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				logger.error("进入downloadHtml方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"查询HTML页面异常",e);
			}
		logger.info("完成downloadHtml方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 获取项目(大品类下)的页面跳转内容
	 * @param ownerId
	 * @param collectClassParentId
	 * @return
	 */
	@RequestMapping("/getWebFlowText")
	public @ResponseBody ResultEntity getWebFlowText(@RequestParam("ownerId") String ownerId, 
			@RequestParam("collectClassParentId") String collectClassParentId) {
		logger.info("进入getWebFlowText方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"查询HTML页面-webflowxml-start");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{ownerId,collectClassParentId},new String[]{"ownerId","collectClassParentId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			DetailConfigEntity dce  = htmlPageInfoService.getWebFlowText(ownerId,collectClassParentId);
			result.setSuccess(true);
			result.setInfo(dce);
			logger.info("进入getWebFlowText方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"查询HTML页面跳转内容-webflowxml-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入getWebFlowText方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"查询HTML页面-webflowxml-异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);  
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入getWebFlowText方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"查询HTML页面-webflowxml-异常",e);
		}
		logger.info("完成getWebFlowText方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author yaming.xu
	 * @date 2015年9月14日
	 * @description 获取项目(大品类下)的页面跳转内容
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @param versionNo
	 * @return
	 */
	@RequestMapping("/getWebFlowTextNew")
	public @ResponseBody ResultEntity getWebFlowText(@RequestParam("ownerId") String ownerId, 
			@RequestParam("collectClassParentId") String collectClassParentId,
			@RequestParam("collectClassId") String collectClassId,
			@RequestParam("versionNo") String versionNo) {
		logger.info("进入getWebFlowText-new方法，根据项目ID："+ownerId+"和采集品类小类ID："+collectClassId+"版本："+versionNo+"查询HTML页面-webflowxml-start");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{ownerId,collectClassParentId,collectClassId,versionNo},new String[]{"ownerId","collectClassParentId","collectClassId","versionNo"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			DetailConfigEntity dce  = htmlPageInfoService.getWebFlowText(ownerId,collectClassParentId,collectClassId,versionNo);
			result.setSuccess(true);
			result.setInfo(dce);
			logger.info("进入getWebFlowText-new方法，根据项目ID："+ownerId+"和采集品类小类ID："+collectClassId+"版本："+versionNo+"查询HTML页面-webflowxml-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入getWebFlowText-new方法，根据项目ID："+ownerId+"和采集品类小类ID："+collectClassId+"版本："+versionNo+"查询HTML页面-webflowxml-error",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);  
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入getWebFlowText-new方法，根据项目ID："+ownerId+"和采集品类小类ID："+collectClassId+"版本："+versionNo+"查询HTML页面-webflowxml-error",e);
		}
		logger.info("完成getWebFlowText-new方法操作，"+result.toString());
		return result;
	}
	
	@RequestMapping("/checkVersionNo")
	public @ResponseBody ResultEntity checkVersionNo(
			@RequestParam("ownerId") String ownerId, 
			@RequestParam("collectClassParentId") String collectClassParentId, 
			@RequestParam("collectClassId") String collectClassId,
			@RequestParam(value="versionNo", defaultValue="0") String versionNo,
			@RequestParam(value="entranceStatus", required=false, defaultValue="0") String entranceStatus) {
		logger.info("进入checkVersionNo方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"和版本号："+versionNo+"校验最大版本号start");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{ownerId,collectClassParentId,collectClassId},new String[]{"ownerId","collectClassParentId","collectClassId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			boolean flag  = htmlPageInfoService.checkVersionNo(ownerId, collectClassParentId, collectClassId, versionNo,entranceStatus);
			result.setSuccess(true);
			if(flag) {//为true有新版本
				result.setCode(BusinessCode.EXIST_MAX_VERSION_NO+"");
				
			}else {
				result.setCode(BusinessCode.NO_EXIST_MAX_VERSION_NO+"");
			}
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入checkVersionNo方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"和版本号："+versionNo+"校验最大版本号end");
		}catch(BusinessException e){
				result.setSuccess(false);  
				result.setCode(e.getErrorCode()+"");
				result.setDesc(e.getErrorMessage());
				logger.error("进入checkVersionNo方法，系统内容异常",e);
				return result;
			}catch(Exception e){
				result.setSuccess(false);  
				result.setCode(ExceptionCode.SYSTEM_ERROR+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				logger.error("进入checkVersionNo方法，根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"和采集品类入口状态："+entranceStatus+"和版本号："+versionNo+"校验最大版本号异常",e);
			}
		logger.info("完成checkVersionNo方法操作，"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 保存html内容
	 * @param detailConfigEntity
	 * @return
	 */
	@RequestMapping("/saveHtmlPageInfo")
	public @ResponseBody ResultEntity saveHtmlPageInfo(@RequestBody DetailConfigEntity detailConfigEntity){
		logger.info(detailConfigEntity.toString());
		logger.info("进入saveHtmlPageInfo方法,保存start");
		ResultEntity result = new ResultEntity();
		try {
			String check=checkNull(new String[]{detailConfigEntity.getOwnerId(),detailConfigEntity.getCollectClassParentId(),detailConfigEntity.getCollectClassId(),detailConfigEntity.getCustomId()},new String[]{"ownerId","collectClassParentId","collectClassId","customId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			//优先获取版本
			detailConfigEntity.setVersionNo(detailConfigService.geUniqueId().toString());
			//处理额外信息 如果再添加隐藏域 可在此修改
			String html=detailConfigEntity.getHtmlText();
			//增加版本信息隐藏域
			html=html.replace("$extraInfo$", "<input type=\"hidden\" id=\"versionNo\" name=\"versionNo\" value=\""+detailConfigEntity.getVersionNo()+"\" />");
			detailConfigEntity.setHtmlText(html);
			DetailConfigEntity dce = htmlPageInfoService.saveHtmlPageInfo(detailConfigEntity);
			result.setSuccess(true);
			result.setCode(BusinessCode.SAVE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			result.setInfo(dce);
			logger.info("进入saveHtmlPageInfo方法,保存end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入saveHtmlPageInfo方法,系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入saveHtmlPageInfo方法,保存异常",e);
		}
		logger.info("完成saveHtmlPageInfo方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月20日
	 * @description 更新html状态 0：废弃，1:正常激活
	 * @param detailConfigEntity
	 * @return
	 */
	@RequestMapping("/updateHtmlPageInfoStatus")
	public @ResponseBody ResultEntity updateHtmlPageInfoStatus(@RequestBody DetailConfigEntity detailConfigEntity){
		logger.info(detailConfigEntity.toString());
		logger.info("进入activeHtmlPageInfo方法,根据项目ID："+detailConfigEntity.getOwnerId()+"，ID："+detailConfigEntity.getId()+"，status："+detailConfigEntity.getStatus()+"更新HTML页面信息start");
		ResultEntity result = new ResultEntity();
		try {
			String check=checkNull(new String[]{detailConfigEntity.getOwnerId(),detailConfigEntity.getId(),String.valueOf(detailConfigEntity.getStatus())},new String[]{"ownerId","id","status"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			htmlPageInfoService.updateHtmlPageInfoStatus(detailConfigEntity);
			result.setSuccess(true);
			if(detailConfigEntity.getStatus() == 0) {
				result.setCode(BusinessCode.SCRAP_SUCC+"");
			}else if(detailConfigEntity.getStatus() == 1) {
				result.setCode(BusinessCode.ACTIVATE_SUCC+"");
			}
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入activeHtmlPageInfo方法,根据项目ID："+detailConfigEntity.getOwnerId()+"，ID："+detailConfigEntity.getId()+"，status："+detailConfigEntity.getStatus()+"更新HTML页面信息end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入activeHtmlPageInfo方法,系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入activeHtmlPageInfo方法,根据项目ID："+detailConfigEntity.getOwnerId()+"，ID："+detailConfigEntity.getId()+"，status："+detailConfigEntity.getStatus()+"更新HTML页面信息异常",e);
		}
		logger.info("完成activeHtmlPageInfo方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月13日
	 * @description 查询组件
	 * @param owner_id
	 * @param app_id
	 * @param collect_class_id
	 * @return
	 */
	@RequestMapping("/queryDetailConfigs")
	public @ResponseBody ResultEntity queryDetailConfigs(@RequestBody DetailConfigEntity detailConfigEntity) {
		logger.info(detailConfigEntity.toString());
		logger.info("进入queryDetailConfigs方法，根据项目ID："+detailConfigEntity.getOwnerId()+",采集品类大类类ID："+detailConfigEntity.getCollectClassParentId()+",采集品类小类ID："+detailConfigEntity.getCollectClassId()+"查询组件信息start");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{detailConfigEntity.getOwnerId(),detailConfigEntity.getCollectClassId()},new String[]{"ownerId","collectClassId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			DetailConfigEntity dce = detailConfigService.queryDetailConfigs(detailConfigEntity.getOwnerId(),detailConfigEntity.getCollectClassParentId(), detailConfigEntity.getCollectClassId());
			result.setInfo(dce);
			result.setSuccess(true);
			logger.info("根据项目ID："+detailConfigEntity.getOwnerId()+",采集品类大类类ID："+detailConfigEntity.getCollectClassParentId()+",采集品类小类ID："+detailConfigEntity.getCollectClassId()+"查询组件信息end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入queryDetailConfigs方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入queryDetailConfigs方法，根据项目ID："+detailConfigEntity.getOwnerId()+",采集品类大类类ID："+detailConfigEntity.getCollectClassParentId()+",采集品类小类ID："+detailConfigEntity.getCollectClassId()+"查询组件异常",e);
		}
		logger.info("完成queryDetailConfigs方法操作，"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description  批量保存组件信息
	 * @param detailConfigEntity
	 * @return
	 */
	@RequestMapping("/saveDetailConfigs")
	public @ResponseBody ResultEntity saveDetailConfigs(@RequestBody DetailConfigEntity detailConfigEntity){
		logger.info(detailConfigEntity.toString());
		logger.info("进入queryDetailConfigs方法,保存start");
		ResultEntity result = new ResultEntity();
		try {
			String check=checkNull(new String[]{detailConfigEntity.getOwnerId(),detailConfigEntity.getCollectClassParentId(),detailConfigEntity.getCollectClassId(),detailConfigEntity.getCustomId()},new String[]{"ownerId","collectClassParentId","collectClassId","customId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			String versionNo =detailConfigService.saveDetailConfigs(detailConfigEntity);
			detailConfigEntity.setVersionNo(versionNo);
			detailConfigEntity.setDatas(null);
			result.setSuccess(true);
			result.setCode(BusinessCode.SAVE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			result.setInfo(detailConfigEntity);
			logger.info("进入queryDetailConfigs方法,保存end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入queryDetailConfigs方法,系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入queryDetailConfigs方法,保存异常",e);
		}
		logger.info("完成saveDetailConfigs方法操作，"+result.toString());
		return result;
			
	}

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月13日
	 * @description 校验参数是否为null
	 * @param values
	 * @param desc
	 * @return
	 */
	private String checkNull(String[] values,String[] desc){
		if(values==null||desc==null){
			return "校验的值和要校验的数据不符";
		}else if(values.length!=desc.length){
			return "校验的值和要校验的数据不符";
		}else{
			for(int i=0;i<values.length;i++){
				if(values[i]==null||values[i].equals("")){
					return desc[i]+"不能为空";
				}
			}
		}
		return "";
	}
}
