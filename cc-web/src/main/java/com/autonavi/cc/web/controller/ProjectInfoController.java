package com.autonavi.cc.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.autonavi.cc.api.bean.BlackWhiteList;
import com.autonavi.cc.api.bean.CollectClass;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.bean.ProjectInfo;
import com.autonavi.cc.api.entity.ProjectInfoEntity;
import com.autonavi.cc.api.entity.ResultEntity;
import com.autonavi.cc.api.exception.BusinessCode;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.api.exception.ExceptionCode;
import com.autonavi.cc.api.service.BlackWhiteListService;
import com.autonavi.cc.api.service.CCApiToolsService;
import com.autonavi.cc.api.service.CollectClassService;
import com.autonavi.cc.api.service.HtmlPageInfoService;
import com.autonavi.cc.api.service.ProjectInfoService;

@Controller
@RequestMapping("/project")
public class ProjectInfoController {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private BlackWhiteListService blackWhiteListService;
	@Autowired
	private CollectClassService collectClassService;
	@Autowired
	private CCApiToolsService cCApiToolsService;
	
	@Autowired
	private HtmlPageInfoService htmlPageInfoService;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月25日
	 * @description 项目主页
	 * @return
	 */
	@RequestMapping("/main")
	public String mainPage() {
		return "project/main";
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 黑白名单首页
	 * @return
	 */
	@RequestMapping("/blackWhite")
	public String blackWhitePage() {
		return "project/blackWhiteMain";
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月8日
	 * @description 查询所有的项目信息
	 * @param projectInfoEntity
	 * @return
	 */
	@RequestMapping("/queryAllProjectInfos")
	public @ResponseBody ResultEntity queryAllProjectInfos(@RequestParam(value="pageNo", required=false, defaultValue="0") int pageNo,@RequestParam(value="limit", required=false, defaultValue="0") int limit) {
		logger.info("进入queryAllProjectInfos方法，（查询条件，当前页数："+pageNo+"，每页记录数："+limit+"），查询所有项目-start");
		ResultEntity result = new ResultEntity();
		try{
			Pagination page = projectInfoService.queryAllProjectInfos(pageNo, limit);
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入queryAllProjectInfos方法，（查询条件，当前页数："+pageNo+"，每页记录数："+limit+"），查询所有项目-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入queryAllProjectInfos方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入queryAllProjectInfos方法，查询所有项目-异常",e);
		}
		logger.info("完成queryAllProjectInfos方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 条件分页查询项目
	 * @param projectInfoEntity
	 * @return
	 */
	@RequestMapping("/queryProjectInfos")
	public @ResponseBody ResultEntity queryProjectInfos(@RequestBody ProjectInfoEntity  projectInfoEntity) {
		logger.info(projectInfoEntity.toString());
		logger.info("进入queryProjectInfos方法，查询项目-start");
		ResultEntity result = new ResultEntity();
		try{

			Pagination page = projectInfoService.queryProjectInfos(projectInfoEntity);
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入queryProjectInfos方法，查询项目-end");
		}catch(BusinessException e){
				result.setSuccess(false);  
				result.setCode(e.getErrorCode()+"");
				result.setDesc(e.getErrorMessage());
				logger.error("进入queryProjectInfos方法，系统内容异常",e);
				return result;
			}catch(Exception e){
				result.setSuccess(false);
				result.setCode(ExceptionCode.SYSTEM_ERROR+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				logger.error("进入queryProjectInfos方法，查询项目-异常",e);
			}
		logger.info("完成queryProjectInfos方法操作，"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 保存项目
	 * @param projectInfo
	 * @return
	 */
	@RequestMapping("/saveProjectInfo")
	public @ResponseBody ResultEntity saveProjectInfo(@RequestBody ProjectInfo projectInfo) {
		logger.info(projectInfo.toString());
		logger.info("进入saveProjectInfo方法，保存项目-start");
		ResultEntity result = new ResultEntity();
		try {
			projectInfoService.saveProjectInfo(projectInfo);
			result.setSuccess(true);
			result.setCode(BusinessCode.SAVE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入saveProjectInfo方法，保存项目-end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入saveProjectInfo方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入saveProjectInfo,保存异常",e);
		}
		logger.info("完成saveProjectInfo方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新项目基本信息
	 * @param projectInfo
	 * @return
	 */
	@RequestMapping("/updateProjectInfo")
	public @ResponseBody ResultEntity updateProjectInfo(@RequestBody ProjectInfo projectInfo) {
		logger.info(projectInfo.toString());
		logger.info("进入updateProjectInfo方法，更新项目-start");
		ResultEntity result = new ResultEntity();
		try {
			projectInfoService.updateProjectInfo(projectInfo);
			result.setSuccess(true);
			result.setCode(BusinessCode.UPDATE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入updateProjectInfo方法，更新项目-end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入updateProjectInfo方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入updateProjectInfo,更新项目异常",e);
		}
		logger.info("完成updateProjectInfo方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新项目的状态
	 * @param projectInfo
	 * @return
	 */
	@RequestMapping("/updateProjectInfoStatus")
	public @ResponseBody ResultEntity updateProjectInfoStatus(@RequestBody ProjectInfo projectInfo) {
		logger.info(projectInfo.toString());
		logger.info("进入updateProjectInfoStatus方法，更新项目状态-start");
		ResultEntity result = new ResultEntity();
		try {
			projectInfoService.updateProjectInfoStatus(projectInfo);
			result.setSuccess(true);
			result.setCode(BusinessCode.UPDATE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入updateProjectInfoStatus方法，更新项目状态--end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入updateProjectInfoStatus方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入updateProjectInfoStatus,更新项目状态-异常",e);
		}
		logger.info("完成updateProjectInfoStatus方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 分页查询采集品类
	 * @param projectInfoEntity
	 * @return
	 */
	@RequestMapping("/queryCollectClass")
	public @ResponseBody ResultEntity queryCollectClass(@RequestBody ProjectInfoEntity  projectInfoEntity) {
		logger.info(projectInfoEntity.toString());
		logger.info("进入queryCollectClass方法，查询采集品类-start");
		ResultEntity result = new ResultEntity();
		try{

			Pagination page = collectClassService.queryCollectClass(projectInfoEntity);
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入queryCollectClass方法，查询采集品类-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入queryCollectClass方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入queryCollectClass方法，查询采集品类-异常",e);
		}
		logger.info("完成queryCollectClass方法操作，"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 保存采集品类
	 * @param projectInfoEntity
	 * @return
	 */
	@RequestMapping("/saveCollectClass")
	public @ResponseBody ResultEntity saveCollectClass(@RequestBody ProjectInfoEntity projectInfoEntity) {
		logger.info(projectInfoEntity.toString());
		logger.info("进入saveCollectClass方法，保存采集品类-start");
		ResultEntity result = new ResultEntity();
		try {
			collectClassService.saveCollectClass(projectInfoEntity);
			result.setSuccess(true);
			result.setCode(BusinessCode.SAVE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入saveCollectClass方法，保存采集品类-end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入saveCollectClass方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入saveCollectClass,保存采集品类异常",e);
		}
		logger.info("完成saveCollectClass方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新采集品类基本信息
	 * @param projectInfoEntity
	 * @return
	 */
	@RequestMapping("/updateCollectClass")
	public @ResponseBody ResultEntity updateCollectClass(@RequestBody ProjectInfoEntity projectInfoEntity) {
		logger.info(projectInfoEntity.toString());
		logger.info("进入updateCollectClass方法，更新采集品类-start");
		ResultEntity result = new ResultEntity();
		try {
			collectClassService.updateCollectClass(projectInfoEntity);
			result.setSuccess(true);
			result.setCode(BusinessCode.UPDATE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入updateCollectClass方法，更新采集品类-end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入updateCollectClass方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入updateCollectClass,更新采集品类异常",e);
		}
		logger.info("完成updateCollectClass方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新采集品类状态
	 * @param projectInfoEntity
	 * @return
	 */
	@RequestMapping("/updateCollectClassStatus")
	public @ResponseBody ResultEntity updateCollectClassStatus(@RequestBody ProjectInfoEntity projectInfoEntity) {
		logger.info(projectInfoEntity.toString());
		logger.info("进入updateCollectClassStatus方法，更新采集品类状态-start");
		ResultEntity result = new ResultEntity();
		try {
			collectClassService.updateCollectClassStatus(projectInfoEntity);
			result.setSuccess(true);
			result.setCode(BusinessCode.UPDATE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入updateCollectClassStatus方法，更新采集品类状态-end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入updateCollectClassStatus方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入updateCollectClassStatus,更新采集品类状态异常",e);
		}
		logger.info("完成updateCollectClassStatus方法操作，"+result.toString());
		return result;
	}
	
	@RequestMapping("/updateCollectClassEntranceStatus")
	public @ResponseBody ResultEntity updateCollectClassEntranceStatus(@RequestBody ProjectInfoEntity projectInfoEntity) {
		logger.info(projectInfoEntity.toString());
		logger.info("进入updateCollectClassEntranceStatus方法，更新采集品类入口状态-start");
		ResultEntity result = new ResultEntity();
		try {
			collectClassService.updateCollectClassEntranceStatus(projectInfoEntity);
			result.setSuccess(true);
			result.setCode(BusinessCode.UPDATE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入updateCollectClassEntranceStatus方法，更新采集品类入口状态-end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入updateCollectClassEntranceStatus方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入updateCollectClassEntranceStatus,更新采集品类入口状态异常",e);
		}
		logger.info("完成updateCollectClassEntranceStatus方法操作，"+result.toString());
		return result;
	}
	
	@RequestMapping("/queryCollectClassChild")
	public @ResponseBody ResultEntity queryCollectClassChild(@RequestParam("customId") String customId,@RequestParam("ownerId") String ownerId, @RequestParam("collectClassParentId") String collectClassParentId) {
		logger.info("customId:"+customId+",ownerId:"+ownerId+",collectClassParentId:"+collectClassParentId);
		logger.info("进入queryCollectClassChild方法，查询采集品类-start");
		ResultEntity result = new ResultEntity();
		try{

			List<CollectClass> collectClassList = collectClassService.queryCollectClassChild(customId, ownerId, collectClassParentId);
			result.setSuccess(true);
			result.setInfo(collectClassList);
			logger.info("进入queryCollectClassChild方法，根据ownerId和collectclassParentId查询所有子类-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入queryCollectClassChild方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入queryCollectClassChild方法，根据ownerId和collectclassParentId查询所有子类-异常",e);
		}
		logger.info("完成queryCollectClass方法操作，"+result.toString());
		return result;
	}
	
	@RequestMapping("/queryBlackWhiteList")
	public @ResponseBody ResultEntity queryBlackWhiteList(@RequestBody ProjectInfoEntity  projectInfoEntity) {
		logger.info(projectInfoEntity.toString());
		logger.info("进入queryBlackWhiteList方法，查询黑白名单-start");
		ResultEntity result = new ResultEntity();
		try{

			Pagination page = blackWhiteListService.queryBlackWhiteList(projectInfoEntity);
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入queryBlackWhiteList方法，查询黑白名单-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入queryBlackWhiteList方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入queryBlackWhiteList方法，查询黑白名单-异常",e);
		}
		logger.info("完成queryBlackWhiteList方法操作，"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 保存项目
	 * @param projectInfo
	 * @return
	 */
	@RequestMapping("/saveBlackWhiteList")
	public @ResponseBody ResultEntity saveBlackWhiteList(@RequestBody BlackWhiteList blackWhiteList) {
		logger.info(blackWhiteList.toString());
		logger.info("进入saveBlackWhiteList方法，保存黑白名单-start");
		ResultEntity result = new ResultEntity();
		try {
			blackWhiteListService.saveBlackWhiteList(blackWhiteList);
			result.setSuccess(true);
			result.setCode(BusinessCode.SAVE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入saveBlackWhiteList方法，保存黑白名单-end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入saveBlackWhiteList方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入saveBlackWhiteList,保存黑白名单",e);
		}
		logger.info("完成saveBlackWhiteList方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新项目基本信息
	 * @param projectInfo
	 * @return
	 */
	@RequestMapping("/updateBlackWhiteList")
	public @ResponseBody ResultEntity updateBlackWhiteList(@RequestBody BlackWhiteList blackWhiteList) {
		logger.info(blackWhiteList.toString());
		logger.info("进入updateBlackWhiteList方法，更新黑白名单-start");
		ResultEntity result = new ResultEntity();
		try {
			blackWhiteListService.updateBlackWhiteList(blackWhiteList);
			result.setSuccess(true);
			result.setCode(BusinessCode.UPDATE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入updateBlackWhiteList方法，更新黑白名单-end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入updateBlackWhiteList方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入updateBlackWhiteList,更新黑白名单异常",e);
		}
		logger.info("完成updateBlackWhiteList方法操作，"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 更新项目的状态
	 * @param projectInfo
	 * @return
	 */
	@RequestMapping("/updateBlackWhiteListStatus")
	public @ResponseBody ResultEntity updateBlackWhiteListStatus(@RequestBody BlackWhiteList blackWhiteList) {
		logger.info(blackWhiteList.toString());
		logger.info("进入updateBlackWhiteListStatus方法，更新黑白名单状态-start");
		ResultEntity result = new ResultEntity();
		try {
			blackWhiteListService.updateBlackWhiteListStatus(blackWhiteList);
			result.setSuccess(true);
			result.setCode(BusinessCode.UPDATE_SUCC+"");
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入updateBlackWhiteListStatus方法，更新黑白名单状态--end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入updateBlackWhiteListStatus方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入updateBlackWhiteListStatus,更新黑白名单状态-异常",e);
		}
		logger.info("完成updateBlackWhiteListStatus方法操作，"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月31日
	 * @description 黑白名单中验证项目唯一
	 * @param blackWhiteList
	 * @return
	 */
	@RequestMapping("/checkUniqueProject")
	public @ResponseBody ResultEntity checkUniqueProject(@RequestBody BlackWhiteList blackWhiteList) {
		logger.info(blackWhiteList.toString());
		logger.info("进入checkUniqueProject方法，黑白名单中验证项目唯一-start");
		ResultEntity result = new ResultEntity();
		try {
			boolean flag = blackWhiteListService.checkUniqueProject(blackWhiteList);
			result.setSuccess(true);
			if(flag) {
				result.setCode(BusinessCode.EXIST_PROJECT+"");
			}else {
				result.setCode(BusinessCode.NO_EXIST_PROJECT+"");
			}
			result.setDesc(cCApiToolsService.getMessage(result.getCode()));
			logger.info("进入checkUniqueProject方法，黑白名单中验证项目唯一--end");
		} catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入checkUniqueProject方法，系统内容异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入checkUniqueProject,黑白名单中验证项目唯一-异常",e);
		}
		logger.info("完成checkUniqueProject方法操作，"+result.toString());
		return result;
	}
	
	@RequestMapping("/publishProject")
	public @ResponseBody ResultEntity publishProject(
			@RequestParam("ownerId") String ownerId, 
			@RequestParam(value="customId") String customId
			) {
		
		logger.info("进入publishProject方法");
		ResultEntity result = new ResultEntity();
		try{
			String check=checkNull(new String[]{ownerId,customId},new String[]{"ownerId","customId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			htmlPageInfoService.publishProject(ownerId,customId);
			result.setSuccess(true);
			result.setDesc("项目发布成功");
		}catch(BusinessException e){
				result.setSuccess(false);  
				result.setCode(e.getErrorCode()+"");
				result.setDesc(e.getErrorMessage());
				logger.error("进入publishProject方法，系统内容异常",e);
				return result;
			}catch(Exception e){
				result.setSuccess(false);  
				result.setCode(ExceptionCode.SYSTEM_ERROR+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				logger.error("进入publishProject方法，系统内容异常",e);
			}
		logger.info("完成publishProject方法操作，"+result.toString());
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
