package com.autonavi.cc.web.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.autonavi.cc.api.entity.ResultEntity;
import com.autonavi.cc.api.exception.BusinessException;
import com.autonavi.cc.api.exception.ExceptionCode;
import com.autonavi.cc.api.service.CCApiToolsService;
import com.autonavi.cc.api.service.CommonService;
import com.autonavi.cc.web.util.CommonUtil;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CCApiToolsService cCApiToolsService;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取客户信息 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/getCustoms")
	public @ResponseBody ResultEntity getCustoms(@RequestParam(value="userName",required=false,defaultValue="")  String userName) {
		logger.info("进入getCustoms方法,条件查询客户信息,条件:userName="+userName+"------start");
		ResultEntity result = new ResultEntity();
		try{
			List<Object> objectList = commonService.getCustomList(userName);
			result.setSuccess(true);
			result.setInfo(objectList);
			logger.info("进入getCustoms方法,条件查询客户信息,条件:userName="+userName+"------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("进入getCustoms方法，条件查询客户信息业务异常",e);
		}catch(Exception e){
			
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入getCustoms方法，条件查询客户信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
 
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取项目负责人 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/getProjectLeaders")
	public @ResponseBody ResultEntity getProjectLeaders(@RequestParam(value="userName",required=false,defaultValue="")  String userName) {
		logger.info("进入getProjectLeaders方法,条件查询项目负责人信息,条件:userName="+userName+"------start");
		ResultEntity result = new ResultEntity();
		try{
			List<Object> objectList = commonService.getProjectLeaderList(userName);
			result.setSuccess(true);
			result.setInfo(objectList);
			logger.info("进入getProjectLeaders方法,条件查询项目负责人信息,条件:userName="+userName+"------end");
		}catch(BusinessException e){		
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("进入getProjectLeaders方法，条件查询项目负责人信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入getProjectLeaders方法，条件查询项目负责人信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月27日
	 * @description 获取采集品类价格
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @param customId
	 * @return
	 */
	@RequestMapping("/getCollectClassPrice")
	public @ResponseBody ResultEntity getCollectClassPrice(@RequestParam("ownerId")  String ownerId,@RequestParam("collectClassParentId")  String collectClassParentId,@RequestParam("collectClassId")  String collectClassId,@RequestParam("customId")  String customId) {
		logger.info("进入getCollectClassPrice方法,查询品类价格,条件:根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"客户ID："+customId+"------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{ownerId,collectClassParentId,collectClassId,customId},new String[]{"ownerId","collectClassParentId","collectClassId","customId"});
			if(!check.equals("")){
				logger.error("校验失败："+check);
				result.setSuccess(false);
				result.setCode(ExceptionCode.MISS_REQUEST_PARAMS+"");
				result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
				return result;
			}
			Object collectClassPrice = commonService.getCollectClassPrice(ownerId, collectClassParentId, collectClassId, customId);
			if(collectClassPrice == null) {
				result.setSuccess(false);
			}else {
				result.setSuccess(true);
			}
			result.setInfo(collectClassPrice);
			logger.info("进入getCollectClassPrice方法,查询品类价格,条件:根据项目ID："+ownerId+"和采集品类大类ID："+collectClassParentId+"和采集品类小类ID："+collectClassId+"客户ID："+customId+"------end");
		}catch(BusinessException e){		
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("进入getCollectClassPrice方法，条件查询采集品类价格信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(ExceptionCode.SYSTEM_ERROR+"");
			result.setDesc(cCApiToolsService.getExceptionMessage(result.getCode()));
			logger.error("进入getCollectClassPrice方法，条件查询采集品类价格信息信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
	
	
	

}
