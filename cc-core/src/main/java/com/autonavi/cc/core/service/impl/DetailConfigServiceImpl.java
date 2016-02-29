package com.autonavi.cc.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autonavi.online.framework.constant.Miscellaneous;
import autonavi.online.framework.sharding.uniqueid.UniqueIDHolder;
import autonavi.online.framework.util.json.JsonBinder;

import com.autonavi.cc.api.bean.DetailConfig;
import com.autonavi.cc.api.entity.DetailConfigEntity;
import com.autonavi.cc.api.entity.TypeEntity;
import com.autonavi.cc.api.service.DetailConfigService;
import com.autonavi.cc.core.dao.DetailConfigDao;

/**
 * 增加，删除，查询和修改组件信息
 * 
 * @author wenpeng.jin
 *
 */
@Service("detailConfigService")
public class DetailConfigServiceImpl implements DetailConfigService{
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private DetailConfigDao detailConfigDao;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月12日
	 * @description 根据客户ID,采集品类大类ID和采集品类小类ID查询组件
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @return
	 */
	//暂未使用
	@Override
	public DetailConfigEntity queryDetailConfigs(String ownerId,String collectClassParentId,  String collectClassId) throws Exception{
		DetailConfigEntity dce = new DetailConfigEntity();
		dce.setOwnerId(ownerId);
		dce.setCollectClassParentId(collectClassParentId);
		dce.setCollectClassId(collectClassId);
		logger.info("根据客户ID："+ownerId+",采集品类大类ID："+collectClassParentId+",采集品类小类ID："+collectClassId+" 查询组件信息start");
		DetailConfig dcTmp = new DetailConfig();
		dcTmp.setOwnerId(ownerId);
		dcTmp.setCollectClassParentId(collectClassParentId);
		dcTmp.setCollectClassId(collectClassId);
		List<DetailConfig> detailConfigs = (List<DetailConfig>)detailConfigDao.queryDetailConfigs(dcTmp);
		logger.info("获取的组件信息："+detailConfigs.toString());
		if(detailConfigs.size() > 0) {
			dce.setVersionNo(detailConfigs.get(0).getVersionNo());
			List<TypeEntity> datas = new ArrayList<TypeEntity>();
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
			logger.info("根据客户ID："+ownerId+",采集品类大类ID："+collectClassParentId+",采集品类小类ID："+collectClassId+" 查询组件信息为空");
		}
		logger.info("根据客户ID："+ownerId+",采集品类大类ID："+collectClassParentId+",采集品类小类ID："+collectClassId+" 查询组件信息end");
		return dce;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 批量保存组件信息
	 * @param detailConfigEntity
	 * @return
	 * @throws Exception
	 */
	@Override
	public String saveDetailConfigs(DetailConfigEntity detailConfigEntity) throws Exception {
		String versionNo = detailConfigEntity.getVersionNo();
		String ownerId = detailConfigEntity.getOwnerId();
//		if(versionNo != null && !"".equals(versionNo)){
//			logger.info("根据版本号："+versionNo+" 删除组件信息start");
//			DetailConfig detailConfig = new DetailConfig();
//			detailConfig.setVersionNo(versionNo);
//			detailConfig.setOwnerId(ownerId);
//			detailConfigDao.deleteDetailConfigs( detailConfig);
//			logger.info("根据版本号："+versionNo+" 删除组件信息end");
//		}
//		versionNo = String.valueOf(geUniqueId());
		logger.info("获得的版本号为："+versionNo);
		String collectClassParentId = detailConfigEntity.getCollectClassParentId();
		String collectClassId = detailConfigEntity.getCollectClassId();
		String customId=detailConfigEntity.getCustomId();
		List<TypeEntity> datas = detailConfigEntity.getDatas();
		List<DetailConfig> detailConfigs = new ArrayList<DetailConfig>();
		int count = 0;
		for(TypeEntity typeEntity : datas) {
			DetailConfig dcTemp = new DetailConfig();
			dcTemp.setOwnerId(ownerId);
			dcTemp.setCollectClassParentId(collectClassParentId);
			dcTemp.setCollectClassId(collectClassId);
			dcTemp.setOrderNo(count);
			dcTemp.setCustomId(customId);
			dcTemp.setVersionNo(versionNo);
			JsonBinder binder= JsonBinder.buildNormalBinder(false);
			String jsonText = binder.toJson(typeEntity.getJsonText());
			dcTemp.setJsonText(jsonText);
			dcTemp.setType(typeEntity.getType());
			detailConfigs.add(dcTemp);
			count++;
		}
//		过多效率比较慢
//		for(int i = 0; i < datas.size(); i++) {
//			TypeEntity typeEntity = datas.get(i);
//			DetailConfig dcTemp = new DetailConfig();
//			dcTemp.setOwnerId(ownerId);
//			dcTemp.setCollectClassParentId(collectClassParentId);
//			dcTemp.setCollectClassId(collectClassId);
//			dcTemp.setOrderNo(i);
//			dcTemp.setVersionNo(versionNo);
//			JsonBinder binder= JsonBinder.buildNormalBinder(false);
//			String jsonText = binder.toJson(typeEntity.getJsonText());
//			dcTemp.setJsonText(jsonText);
//			dcTemp.setType(typeEntity.getType());
//			detailConfigs.add(dcTemp);
//		}
		
		logger.info("批量保存组件信息start");
		logger.info(detailConfigs.toString());
		detailConfigDao.saveDetailConfigs( detailConfigs);
		logger.info("批量保存组件信息end");
		return versionNo;
	}     
	
	/**
	 * 生成组件版本号
	 * 11～32位的数字或字母或数字与字母的组合，且区分大小写。	
	 * @return 
	 */
//	private String genVersionNo() {
//		String cureDate = String.valueOf(new Date().getTime());
//		String batchNoForAlipay = cureDate + "R" + getRandomString(5);
//		return batchNoForAlipay;
//	}
//	
//	
//	public static String getRandomString(int length) { //length表示生成字符串的长度  
//	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
//	    Random random = new Random();     
//	    StringBuffer sb = new StringBuffer();     
//	    for (int i = 0; i < length; i++) {     
//	        int number = random.nextInt(base.length());     
//	        sb.append(base.charAt(number));     
//	    }     
//	    return sb.toString();     
//	 }
	/**
	 * 生成组件版本号 雪花
	 * @return 
	 */
	@Override
	public Long geUniqueId() throws Exception {
		return UniqueIDHolder.getIdWorker().nextId(Miscellaneous.getMyid());
	}
	
}
