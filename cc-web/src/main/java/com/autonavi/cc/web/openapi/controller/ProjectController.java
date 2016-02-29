package com.autonavi.cc.web.openapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autonavi.online.framework.util.json.JsonBinder;

import com.autonavi.cc.api.bean.CollectClass;
import com.autonavi.cc.api.bean.CollectTaskClazz;
import com.autonavi.cc.api.bean.Pagination;
import com.autonavi.cc.api.entity.ResultEntity;
import com.autonavi.cc.api.entity.TaskClazzMenuEntity;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.api.exception.ExceptionCode;
import com.autonavi.cc.api.openapi.service.ProjectService;
import com.autonavi.cc.api.service.CCApiToolsService;
import com.autonavi.cc.web.openapi.util.RequestParams;
import com.autonavi.cc.web.openapi.util.RequestParamsHandler;
import com.autonavi.cc.web.util.CommonUtil;
/**
 * 提供对外接口查询项目相关信息
 * @author wenpeng.jin
 *
 */
@Controller
public class ProjectController {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private CCApiToolsService cCApiToolsService;
	
	@RequestMapping(value="/openapi",params={"serviceid=666001"})
	public @ResponseBody String queryProjectInfos(HttpServletRequest request) {
		logger.info("进入openapi-queryProjectInfos方法，查询项目-start");
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder(false);
		String jsoncallback = null;
		ResultEntity result = new ResultEntity();
		try{
			RequestParams params = RequestParamsHandler.handleRequest(request);
			logger.entry(params);
			jsoncallback = (String)params.getValue("jsoncallback");
			String customId = (String)(params.getValue("customId") == null ? "" :params.getValue("customId"));
			String customName = (String)(params.getValue("customName") == null ? "" :params.getValue("customName"));
			String projectName = (String)(params.getValue("projectName") == null ? "" :params.getValue("projectName"));
			String status = (String)(params.getValue("status") == null ? "0" :params.getValue("status"));
			String ownerId = (String)(params.getValue("ownerId") == null ? "" :params.getValue("ownerId"));
			String pageNo =  (String)(params.getValue("pageNo") == null ? "0" :params.getValue("pageNo"));
			String limit =  (String)(params.getValue("limit") == null ? "0" :params.getValue("limit"));
			Pagination page = projectService.queryProjectInfos(ownerId,customId,customName, projectName,Integer.valueOf(status), Integer.valueOf(pageNo), Integer.valueOf(limit));
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入openapi-queryProjectInfos方法，查询项目-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入openapi-queryProjectInfos方法，查询项目-业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入openapi-queryProjectInfos方法，查询项目-系统异常",e);
		}
		logger.info("完成openapi-queryProjectInfos方法操作");
		logger.info(jsonBinder.toJson(result));
		if (jsoncallback != null && !jsoncallback.equals(""))
			return jsoncallback + "(" + jsonBinder.toJson(result) + ")";
		else
			return jsonBinder.toJson(result);
	}
	
	@RequestMapping(value="/openapi",params={"serviceid=666003"})
	public @ResponseBody String queryCollectClass(HttpServletRequest request) {
		logger.info("进入openapi-queryCollectClass方法，查询项目的采集品类父类-start");
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder(false);
		String jsoncallback = null;
		ResultEntity result = new ResultEntity();
		try{
			RequestParams params = RequestParamsHandler.handleRequest(request);
			logger.entry(params);
			jsoncallback = (String)params.getValue("jsoncallback");
//			String customId = (String)(params.getValue("customId") == null ? "" :params.getValue("customId"));
			String ownerId = (String)(params.getValue("ownerId") == null ? "" :params.getValue("ownerId"));
			String taskType = (String)(params.getValue("taskType") == null ? "1" :params.getValue("taskType"));
			String check=CommonUtil.checkNull(new String[]{ownerId,taskType},new String[]{"ownerId","taskType"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			}else {
				List<CollectClass> collectClasses = projectService.queryCollectClass(ownerId,taskType);
				result.setSuccess(true);
				result.setInfo(collectClasses);
				logger.info("进入openapi-queryCollectClass方法，查询项目的采集品类父类-end");
			}
			
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入openapi-queryCollectClass方法，查询项目的采集品类父类-业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入openapi-queryCollectClass方法，查询项目的采集品类父类-系统异常",e);
		}
		logger.info("完成openapi-queryCollectClass方法操作");
		logger.info(jsonBinder.toJson(result));
		if (jsoncallback != null && !jsoncallback.equals(""))
			return jsoncallback + "(" + jsonBinder.toJson(result) + ")";
		else
			return jsonBinder.toJson(result);
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月13日
	 * @description 修改项目状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/openapi",params={"serviceid=666002"})
	public @ResponseBody String updateProject(HttpServletRequest request) {
		logger.info("进入openapi-updateProject方法,启动or暂停项目-start");
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder(false);
		String jsoncallback = null;
		ResultEntity result = new ResultEntity();
		try{
			RequestParams params = RequestParamsHandler.handleRequest(request);
			logger.entry(params);
			jsoncallback = (String)params.getValue("jsoncallback");
			String customId = (String)(params.getValue("customId") == null ? "" :params.getValue("customId"));
			String ownerId = (String)(params.getValue("ownerId") == null ? "" :params.getValue("ownerId"));
			String status =  (String)(params.getValue("status") == null ? "" :params.getValue("status"));
			String check=CommonUtil.checkNull(new String[]{customId,ownerId,status},new String[]{"customId","ownerId","status"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			}else {
				projectService.updateProjectStatus(customId,ownerId, status);
				result.setSuccess(true);
				result.setInfo("修改成功,状态为"+status);
				logger.info("进入openapi-updateProject方法,启动or暂停项目-end");
			}
			
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入openapi-updateProject方法,启动or暂停项目-业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入openapi-updateProject方法,启动or暂停项目-系统异常",e);
		}
		logger.info("完成openapi-updateProject方法,启动or暂停项目操作");
		logger.info(jsonBinder.toJson(result));
		if (jsoncallback != null && !jsoncallback.equals(""))
			return jsoncallback + "(" + jsonBinder.toJson(result) + ")";
		else
			return jsonBinder.toJson(result);
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月22日
	 * @description 查询项目的采集品类父类以及child
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/openapi",params={"serviceid=666004"})
	public @ResponseBody String getCollectClazzTree(HttpServletRequest request) {
		logger.info("进入openapi-getCollectClazzTree方法，查询项目的采集品类父类以及child-start");
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder(false);
		String jsoncallback = null;
		ResultEntity result = new ResultEntity();
		try{
			RequestParams params = RequestParamsHandler.handleRequest(request);
			logger.entry(params);
			jsoncallback = (String)params.getValue("jsoncallback");
//			String customId = (String)(params.getValue("customId") == null ? "" :params.getValue("customId"));
			String ownerId = (String)(params.getValue("ownerId") == null ? "" :params.getValue("ownerId"));
			String taskType = (String)(params.getValue("taskType") == null ? "1" :params.getValue("taskType"));
			String check=CommonUtil.checkNull(new String[]{ownerId,taskType},new String[]{"ownerId","taskType"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			}else {
				List<TaskClazzMenuEntity> collectClasses = projectService.getCollectClazzTree(ownerId,taskType);
				result.setSuccess(true);
				result.setInfo(collectClasses);
				logger.info("进入openapi-getCollectClazzTree方法，查询项目的采集品类父类以及child-end");
			}
			
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入openapi-getCollectClazzTree方法，查询项目的采集品类父类以及child-业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入openapi-getCollectClazzTree方法，查询项目的采集品类父类以及child-系统异常",e);
		}
		logger.info("完成openapi-getCollectClazzTree方法操作");
		logger.info(jsonBinder.toJson(result));
		if (jsoncallback != null && !jsoncallback.equals(""))
			return jsoncallback + "(" + jsonBinder.toJson(result) + ")";
		else
			return jsonBinder.toJson(result);
	}
	/**
	 * 获取分类信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/openapi",params={"serviceid=666005"})
	public @ResponseBody String getCollectClazz(HttpServletRequest request) {
		logger.info("进入openapi-getCollectClazz方法，查询项目的采集品类信息");
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder(false);
		String jsoncallback = null;
		ResultEntity result = new ResultEntity();
		try{
			RequestParams params = RequestParamsHandler.handleRequest(request);
			logger.entry(params);
			jsoncallback = (String)params.getValue("jsoncallback");
			String ownerId = params.getValue("ownerId") == null ? null :params.getValue("ownerId").toString();
			String collectClassId = params.getValue("collectClassId") == null ? null :params.getValue("collectClassId").toString();
			String check=CommonUtil.checkNull(new String[]{ownerId,collectClassId},new String[]{"ownerId","collectClassId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			}else {
				CollectTaskClazz clazz = projectService.getCollectClazz(ownerId,collectClassId);
				result.setSuccess(true);
				result.setInfo(clazz);
			}
			
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入openapi-getCollectClazz方法，查询业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入openapi-getCollectClazz方法，查询系统异常",e);
		}
		logger.info("完成openapi-getCollectClazz方法操作");
		logger.info(jsonBinder.toJson(result));
		if (jsoncallback != null && !jsoncallback.equals(""))
			return jsoncallback + "(" + jsonBinder.toJson(result) + ")";
		else
			return jsonBinder.toJson(result);
	}

}
