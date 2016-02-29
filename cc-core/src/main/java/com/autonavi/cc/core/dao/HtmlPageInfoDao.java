package com.autonavi.cc.core.dao;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import autonavi.online.framework.sharding.dao.constant.ReservedWord;
import autonavi.online.framework.sharding.entry.aspect.annotation.Author;
import autonavi.online.framework.sharding.entry.aspect.annotation.Insert;
import autonavi.online.framework.sharding.entry.aspect.annotation.Select;
import autonavi.online.framework.sharding.entry.aspect.annotation.Shard;
import autonavi.online.framework.sharding.entry.aspect.annotation.SqlParameter;
import autonavi.online.framework.sharding.entry.aspect.annotation.Update;
import autonavi.online.framework.sharding.entry.entity.CollectionType;

import com.autonavi.cc.api.bean.HtmlPageInfo;


@Repository
public class HtmlPageInfoDao {
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 条件查询html页面记录
	 * @param htmlPageInfo
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.beanList, resultType = HtmlPageInfo.class)
	public Object queryHtmlPageInfos(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo,@SqlParameter("start") int start, @SqlParameter("limit") int limit) {
		StringBuffer sql = new StringBuffer();
		 sql.append(" SELECT chpi.ID AS id,chpi.OWNER_ID AS ownerId,chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId,chpi.COLLECT_CLASS_ID AS collectClassId,chpi.HTML_TEXT AS htmlText,chpi.VERSION_NO AS versionNo,cpi.PROJECT_NAME AS ownerName,ccc.COLLECT_CLASS_NAME AS collectClassName,ccc.COLLECT_CLASS_PAY AS collectClassPay,cccc.COLLECT_CLASS_NAME  AS collectClassParentName   FROM CC_HTML5_PAGE_INFO chpi JOIN ");
		 sql.append(" (SELECT COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID,MAX(UPDATE_TIME) as UPDATE_TIME  FROM CC_HTML5_PAGE_INFO WHERE ");
				 sql.append( "  STATUS = 1 ");
				if(htmlPageInfo.getOwnerId() != null && !"".equals(htmlPageInfo.getOwnerId())) {
					sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId} ");
				}
				if(htmlPageInfo.getCollectClassParentId() != null && !"".equals(htmlPageInfo.getCollectClassParentId())) {
					sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
				}
				if(htmlPageInfo.getCollectClassId() != null && !"".equals(htmlPageInfo.getCollectClassId())) {
					sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId} ");
				}
				sql.append(" GROUP BY OWNER_ID, COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID) tmp ");
				sql.append(" ON chpi.COLLECT_CLASS_PARENT_ID = tmp.COLLECT_CLASS_PARENT_ID AND chpi.COLLECT_CLASS_ID = tmp.COLLECT_CLASS_ID AND chpi.UPDATE_TIME = tmp.UPDATE_TIME  ");
				sql.append(" LEFT JOIN cc_project_info cpi ");
				sql.append(" ON chpi.OWNER_ID = cpi.ID ");
				sql.append(" LEFT JOIN cc_collect_class ccc ");
				sql.append("  ON chpi.COLLECT_CLASS_ID = ccc.ID ");
				sql.append(" LEFT JOIN cc_collect_class cccc ");
				sql.append(" ON chpi.COLLECT_CLASS_PARENT_ID = cccc.ID ");
			    sql.append(" ORDER BY  chpi.UPDATE_TIME DESC ");
				sql.append("  LIMIT #{start},#{limit}");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 条件查询html页面记录
	 * @param htmlPageInfo
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.column, resultType = Long.class)
	public Object queryHtmlPageInfosCount(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(chpi.ID) FROM CC_HTML5_PAGE_INFO chpi JOIN ");
		sql.append(" (SELECT COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID, MAX(UPDATE_TIME) as UPDATE_TIME  FROM CC_HTML5_PAGE_INFO WHERE ");
		sql.append("  STATUS = 1 ");
				if(htmlPageInfo.getOwnerId() != null && !"".equals(htmlPageInfo.getOwnerId())) {
					sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId}  and CUSTOM_ID=#{htmlPageInfo.customId}  ");
				}
				if(htmlPageInfo.getCollectClassParentId() != null && !"".equals(htmlPageInfo.getCollectClassParentId())) {
					sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
				}
				if(htmlPageInfo.getCollectClassId() != null && !"".equals(htmlPageInfo.getCollectClassId())) {
					sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId} ");
				}
				sql.append(" GROUP BY OWNER_ID, COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID) tmp ");
				sql.append(" ON chpi.COLLECT_CLASS_PARENT_ID = tmp.COLLECT_CLASS_PARENT_ID AND chpi.COLLECT_CLASS_ID = tmp.COLLECT_CLASS_ID AND chpi.UPDATE_TIME = tmp.UPDATE_TIME");
		return sql.toString();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 查询当前小品类历史版本页面记录
	 * @param htmlPageInfo
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.beanList, resultType = HtmlPageInfo.class)
	public Object queryHistoryHtmls(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo,@SqlParameter("start") int start, @SqlParameter("limit") int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  from_unixtime(chpi.CREATE_TIME/1000,'%Y-%m-%d %H:%i:%s') as createTimeFormat ,chpi.ID AS id,  chpi.OWNER_ID AS ownerId, chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId, chpi.COLLECT_CLASS_ID AS collectClassId, chpi.VERSION_NO AS versionNo, chpi.STATUS AS STATUS,cpi.PROJECT_NAME AS ownerName,ccc.COLLECT_CLASS_NAME AS collectClassName,ccc.COLLECT_CLASS_PAY AS collectClassPay,cccc.COLLECT_CLASS_NAME  AS collectClassParentName FROM CC_HTML5_PAGE_INFO chpi ");
		sql.append(" LEFT JOIN cc_project_info cpi ");
		sql.append(" ON chpi.OWNER_ID = cpi.ID ");
		sql.append(" LEFT JOIN cc_collect_class ccc ");
		sql.append("  ON chpi.COLLECT_CLASS_ID = ccc.ID ");
		sql.append(" LEFT JOIN cc_collect_class cccc ");
		sql.append(" ON chpi.COLLECT_CLASS_PARENT_ID = cccc.ID ");
		sql.append(" WHERE chpi.OWNER_ID = #{htmlPageInfo.ownerId} and chpi.CUSTOM_ID=#{htmlPageInfo.customId} ");
		sql.append(" AND chpi.COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND chpi.COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId} ");
		sql.append(" ORDER BY chpi.VERSION_NO DESC ");
		sql.append("  LIMIT #{start},#{limit}");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 查询小品类历史版本页面记录
	 * @param htmlPageInfo
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.column, resultType = Long.class)
	public Object queryHistoryHtmlsCount(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  COUNT(ID) FROM CC_HTML5_PAGE_INFO WHERE ");
		sql.append(" OWNER_ID = #{htmlPageInfo.ownerId}  and CUSTOM_ID=#{htmlPageInfo.customId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId} ");
		sql.append(" ORDER BY VERSION_NO DESC ");
		return sql.toString();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 根据项目ID和采集品类ID（大类和小类）查询html页面记录
	 * @param dataSourceKey
	 * @param owner_id
	 * @param collect_class_id
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.bean, resultType = HtmlPageInfo.class)
	public Object queryHtmlPageInfo(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT chpi.ID AS id,chpi.OWNER_ID AS ownerId,chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId,chpi.COLLECT_CLASS_ID AS collectClassId,chpi.VERSION_NO AS versionNo,chpi.HTML_TEXT AS htmlText,chpi.JS_TEXT AS jsText,chpi.CSS_TEXT AS cssText,chpi.WEBFLOW_TEXT AS webFlowText  FROM CC_HTML5_PAGE_INFO chpi JOIN ");
//		sql.append(" (SELECT COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID, MAX(VERSION_NO) AS VERSION_NO  FROM CC_HTML5_PAGE_INFO WHERE ");
//		sql.append(" OWNER_ID = #{htmlPageInfo.ownerId} ");
//		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
//		sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId}");
//		if(!StringUtils.isEmpty(htmlPageInfo.getVersionNo())) {
//			sql.append(" AND VERSION_NO = #{htmlPageInfo.versionNo}");
//		}
//		sql.append(" AND STATUS = 1");
//		sql.append(" GROUP BY OWNER_ID,COLLECT_CLASS_PARENT_ID, COLLECT_CLASS_ID) tmp ");
//		sql.append(" ON chpi.COLLECT_CLASS_PARENT_ID = tmp.COLLECT_CLASS_PARENT_ID AND chpi.COLLECT_CLASS_ID = tmp.COLLECT_CLASS_ID AND chpi.VERSION_NO = tmp.VERSION_NO");
		StringBuffer sql=new StringBuffer();
		if(!StringUtils.isEmpty(htmlPageInfo.getVersionNo())) {
			sql.append("SELECT chpi.ID AS id,chpi.OWNER_ID AS ownerId,chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId,chpi.COLLECT_CLASS_ID AS collectClassId,chpi.VERSION_NO AS versionNo,chpi.HTML_TEXT AS htmlText,chpi.JS_TEXT AS jsText,chpi.CSS_TEXT AS cssText,chpi.WEBFLOW_TEXT AS webFlowText  FROM CC_HTML5_PAGE_INFO chpi ");
		}else{
			sql.append(" SELECT chpi.ID AS id,chpi.OWNER_ID AS ownerId,chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId,chpi.COLLECT_CLASS_ID AS collectClassId,chpi.VERSION_NO AS versionNo,chpi.HTML_TEXT AS htmlText,chpi.JS_TEXT AS jsText,chpi.CSS_TEXT AS cssText,chpi.WEBFLOW_TEXT AS webFlowText  FROM ( ");
			sql.append(" SELECT max(update_time) update_time from CC_HTML5_PAGE_INFO  ");
			sql.append(" where 1=1 ");
			sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId} ");
			sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
			sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId} AND STATUS = 1 )a ");
			sql.append(" inner join (");
			sql.append(" SELECT update_time,ID,OWNER_ID,COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID,VERSION_NO,HTML_TEXT,JS_TEXT,CSS_TEXT,WEBFLOW_TEXT  FROM CC_HTML5_PAGE_INFO ");
		}
		sql.append(" where 1=1 ");
		sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId}");
		if(!StringUtils.isEmpty(htmlPageInfo.getVersionNo())) {
			sql.append(" AND VERSION_NO = #{htmlPageInfo.versionNo}");
		}else{
			sql.append(" AND STATUS = 1 ");
			sql.append(")chpi on a.update_time=chpi.update_time ");
		}
		return sql.toString();
	}
	
	@Author("yaming.xu")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.bean, resultType = HtmlPageInfo.class)
	public Object queryLastVersionNo(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT chpi.VERSION_NO AS versionNo,chpi.COLLECT_CLASS_ID as collectClassId  FROM ");
		sql.append("( SELECT max(update_time) update_time from CC_HTML5_PAGE_INFO  ");
		sql.append(" where 1=1 ");
		sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId}");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId}");
		sql.append(" AND STATUS = 1 ) a inner join ");
		sql.append("(SELECT VERSION_NO,COLLECT_CLASS_ID, update_time FROM CC_HTML5_PAGE_INFO ");
		sql.append(" where 1=1 ");
		sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId}");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId}");
		sql.append(" AND STATUS = 1 )chpi on a.update_time=chpi.update_time");
		
		return sql.toString();
	}
	
	@Author("yaming.xu")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.bean, resultType = HtmlPageInfo.class)
	public Object queryLastVersionNo(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo,@SqlParameter("entranceStatus")  String entranceStatus) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT chpi.VERSION_NO AS versionNo,COLLECT_CLASS_ID as collectClassId  FROM ");
		sql.append("( SELECT max(update_time) update_time from CC_HTML5_PAGE_INFO  ");
		sql.append(" where 1=1 ");
		sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId}");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID in (SELECT c.ID FROM cc_collect_class c WHERE c.COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} AND c.OWNER_ID = #{htmlPageInfo.ownerId} AND c.ENTRANCE_STATUS = #{entranceStatus}) ");
		sql.append(" AND STATUS = 1) a inner join ");
		sql.append("(SELECT VERSION_NO,COLLECT_CLASS_ID,update_time FROM CC_HTML5_PAGE_INFO ");
		sql.append(" where 1=1 ");
		sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId}");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID in (SELECT c.ID FROM cc_collect_class c WHERE c.COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} AND c.OWNER_ID = #{htmlPageInfo.ownerId} AND c.ENTRANCE_STATUS = #{entranceStatus}) ");
		sql.append(" AND STATUS = 1 )chpi on a.update_time=chpi.update_time");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 查询功能入口页信息
	 * @param htmlPageInfo
	 * @param entranceStatus
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.bean, resultType = HtmlPageInfo.class)
	public Object queryHtmlPageInfoByEntranceStatus(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo,@SqlParameter("entranceStatus") int entranceStatus) {
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT chpi.ID AS id,chpi.OWNER_ID AS ownerId,chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId,chpi.COLLECT_CLASS_ID AS collectClassId,chpi.VERSION_NO AS versionNo,chpi.HTML_TEXT AS htmlText,chpi.JS_TEXT AS jsText,chpi.CSS_TEXT AS cssText FROM CC_HTML5_PAGE_INFO chpi JOIN ");
//		sql.append(" (SELECT COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID, MAX(VERSION_NO) AS VERSION_NO  FROM CC_HTML5_PAGE_INFO WHERE ");
//		sql.append(" OWNER_ID = #{htmlPageInfo.ownerId} ");
//		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
//		sql.append(" AND COLLECT_CLASS_ID in (SELECT c.ID FROM cc_collect_class c WHERE c.COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} AND c.OWNER_ID = #{htmlPageInfo.ownerId} AND c.ENTRANCE_STATUS = #{entranceStatus})");
//		sql.append(" AND STATUS = 1");
//		sql.append(" GROUP BY OWNER_ID,COLLECT_CLASS_PARENT_ID, COLLECT_CLASS_ID) tmp ");
//		sql.append(" ON chpi.COLLECT_CLASS_PARENT_ID = tmp.COLLECT_CLASS_PARENT_ID AND chpi.COLLECT_CLASS_ID = tmp.COLLECT_CLASS_ID AND chpi.VERSION_NO = tmp.VERSION_NO");
		if(!StringUtils.isEmpty(htmlPageInfo.getVersionNo())) {
			sql.append("SELECT chpi.ID AS id,chpi.OWNER_ID AS ownerId,chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId,chpi.COLLECT_CLASS_ID AS collectClassId,chpi.VERSION_NO AS versionNo,chpi.HTML_TEXT AS htmlText,chpi.JS_TEXT AS jsText,chpi.CSS_TEXT AS cssText FROM CC_HTML5_PAGE_INFO chpi ");
		}else{
			sql.append("SELECT chpi.ID AS id,chpi.OWNER_ID AS ownerId,chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId,chpi.COLLECT_CLASS_ID AS collectClassId,chpi.VERSION_NO AS versionNo,chpi.HTML_TEXT AS htmlText,chpi.JS_TEXT AS jsText,chpi.CSS_TEXT AS cssText FROM ( ");
			sql.append(" SELECT max(update_time) update_time from CC_HTML5_PAGE_INFO  ");
			sql.append(" where 1=1 ");
			sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId}");
			sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
			sql.append(" AND COLLECT_CLASS_ID in (SELECT c.ID FROM cc_collect_class c WHERE c.COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} AND c.OWNER_ID = #{htmlPageInfo.ownerId} AND c.ENTRANCE_STATUS = #{entranceStatus}) ");
			sql.append(" AND STATUS = 1 ) a ");
			sql.append(" inner join ( ");
			sql.append(" SELECT update_time,ID,OWNER_ID,COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID,VERSION_NO,HTML_TEXT,JS_TEXT,CSS_TEXT,WEBFLOW_TEXT  FROM CC_HTML5_PAGE_INFO ");
		}
		
		sql.append(" where 1=1 ");
		sql.append(" AND OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId}");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID in (SELECT c.ID FROM cc_collect_class c WHERE c.COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} AND c.OWNER_ID = #{htmlPageInfo.ownerId} AND c.ENTRANCE_STATUS = #{entranceStatus}) ");
		if(!StringUtils.isEmpty(htmlPageInfo.getVersionNo())) {
			sql.append(" AND VERSION_NO = #{htmlPageInfo.versionNo}");
		}else{
			sql.append(" AND STATUS = 1 ");
			sql.append(")chpi on a.update_time=chpi.update_time ");
		}
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 根据采集品类大类和ownerId获取页面信息
	 * @param htmlPageInfo
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.beanList, resultType = HtmlPageInfo.class) 
	public Object queryHtmlPageInfosByCCPID(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ");
		sql.append(" chpi.ID AS id, ");
		sql.append(" chpi.OWNER_ID AS ownerId, ");
		sql.append(" chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId, ");
		sql.append(" chpi.COLLECT_CLASS_ID AS collectClassId, ");
		sql.append(" chpi.VERSION_NO AS versionNo, ");
		sql.append(" chpi.HTML_TEXT AS htmlText, ");
		sql.append(" chpi.JS_TEXT AS jsText, ");
		sql.append(" chpi.CSS_TEXT AS cssText, ");
		sql.append(" chpi.WEBFLOW_TEXT AS webFlowText ");
		sql.append(" FROM ( ");
		sql.append(" SELECT max(update_time) update_time,");
		sql.append(" collect_class_id ");
		sql.append(" FROM cc_html5_page_info ");
		sql.append(" WHERE CUSTOM_ID=#{htmlPageInfo.customId} and OWNER_ID = #{htmlPageInfo.ownerId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} and status=1 group by collect_class_id )a ");
		sql.append(" inner join ( ");
		sql.append(" SELECT  ");
		sql.append(" ID, ");
		sql.append(" OWNER_ID, ");
		sql.append(" COLLECT_CLASS_PARENT_ID, ");
		sql.append(" COLLECT_CLASS_ID, ");
		sql.append(" VERSION_NO, ");
		sql.append(" HTML_TEXT, ");
		sql.append(" JS_TEXT, ");
		sql.append(" CSS_TEXT, ");
		sql.append(" WEBFLOW_TEXT, ");
		sql.append(" update_time ");
		sql.append(" FROM cc_html5_page_info ");
		sql.append(" WHERE CUSTOM_ID=#{htmlPageInfo.customId} and OWNER_ID = #{htmlPageInfo.ownerId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} and status=1 )chpi ");
		sql.append("on  a.update_time=chpi.update_time and a.collect_class_id=chpi.collect_class_id ");
		return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.bean, resultType = HtmlPageInfo.class) 
	public Object queryHtmlPageInfosById(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ");
		sql.append(" chpi.ID AS id, ");
		sql.append(" chpi.OWNER_ID AS ownerId, ");
		sql.append(" chpi.COLLECT_CLASS_PARENT_ID AS collectClassParentId, ");
		sql.append(" chpi.COLLECT_CLASS_ID AS collectClassId, ");
		sql.append(" chpi.VERSION_NO AS versionNo, ");
		sql.append(" chpi.HTML_TEXT AS htmlText, ");
		sql.append(" chpi.JS_TEXT AS jsText, ");
		sql.append(" chpi.CSS_TEXT AS cssText, ");
		sql.append(" chpi.WEBFLOW_TEXT AS webFlowText ");
		sql.append(" FROM cc_html5_page_info chpi ");
		sql.append(" WHERE CUSTOM_ID=#{htmlPageInfo.customId} and OWNER_ID = #{htmlPageInfo.ownerId} ");
		sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId} and version_no=#{htmlPageInfo.versionNo} ");
		return sql.toString();
	}

	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 保存html页面信息
	 * @param dataSourceKey
	 * @param htmlPageInfo
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Insert
	public Object saveHtmlPageInfo(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO CC_HTML5_PAGE_INFO (ID,OWNER_ID,COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID,HTML_TEXT,JS_TEXT,CSS_TEXT,WEBFLOW_TEXT,STATUS,CREATE_TIME,VERSION_NO,CUSTOM_ID,UPDATE_TIME) VALUES (");
		sql.append("#{"+ ReservedWord.snowflake + "}");
		sql.append(" ,#{htmlPageInfo.ownerId}");
		sql.append(" ,#{htmlPageInfo.collectClassParentId}");
		sql.append(" ,#{htmlPageInfo.collectClassId}");
		sql.append(" ,#{htmlPageInfo.htmlText}");
		sql.append(" ,#{htmlPageInfo.jsText}");
		sql.append(" ,#{htmlPageInfo.cssText}");
		sql.append(" ,#{htmlPageInfo.webFlowText}");
		sql.append(" ,#{htmlPageInfo.status}");
		sql.append(" ,unix_timestamp(now())*1000");
		sql.append(" ,#{htmlPageInfo.versionNo}");
		sql.append(" ,#{htmlPageInfo.customId} ");
		sql.append(" ,unix_timestamp(now())*1000 )");
		return sql.toString();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 查询当前项目和采集品类下的最新版本号
	 * @param dataSourceKey
	 * @param owner_id
	 * @param collect_class_id
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.column, resultType = String.class)
	public Object queryMaxVersionNo(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.version_no FROM ( ");
		sql.append(" SELECT max(update_time) update_time from CC_HTML5_PAGE_INFO  ");
		sql.append(" OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId} ");
		sql.append(" AND STATUS = 1 ) a ");
		sql.append(" inner join ( ");
		sql.append(" SELECT version_no from CC_HTML5_PAGE_INFO  ");
		sql.append(" OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID = #{htmlPageInfo.collectClassId} ");
		sql.append(" AND STATUS = 1 ) b ");
		sql.append(" on a.update_time=b.update_time ");
		return sql.toString();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 查询当前项目和采集品类下的最新版本号
	 * @param dataSourceKey
	 * @param owner_id
	 * @param collect_class_id
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Select(collectionType = CollectionType.column, resultType = String.class)
	public Object queryMaxVersionNoByEntranceStatus(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo,@SqlParameter("entranceStatus") int entranceStatus) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.version_no FROM ( ");
		sql.append(" SELECT max(update_time) update_time from CC_HTML5_PAGE_INFO  ");
		sql.append(" OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID in (SELECT c.ID FROM cc_collect_class c WHERE c.COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} AND c.OWNER_ID = #{htmlPageInfo.ownerId} AND c.ENTRANCE_STATUS = #{entranceStatus})");
		sql.append(" AND STATUS = 1 ) a ");
		sql.append(" inner join ( ");
		sql.append(" SELECT version_no FROM CC_HTML5_PAGE_INFO WHERE ");
		sql.append(" OWNER_ID = #{htmlPageInfo.ownerId} and CUSTOM_ID=#{htmlPageInfo.customId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID in (SELECT c.ID FROM cc_collect_class c WHERE c.COLLECT_CLASS_PARENT_ID = #{htmlPageInfo.collectClassParentId} AND c.OWNER_ID = #{htmlPageInfo.ownerId} AND c.ENTRANCE_STATUS = #{entranceStatus})");
		sql.append(" AND STATUS = 1 ) b ");
		sql.append(" on a.update_time=b.update_time ");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 根据ID更新页面的状态0：废弃，1:正常激活
	 * @param dataSourceKey
	 * @param detailConfig
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Update
	public Object updateHtmlPageInfoStatus(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo){
		String sql = "UPDATE CC_HTML5_PAGE_INFO SET STATUS = #{htmlPageInfo.status} , UPDATE_TIME = unix_timestamp(now())*1000  WHERE ID = #{htmlPageInfo.id} and CUSTOM_ID=#{htmlPageInfo.customId} "; 
		return sql;
	}
	/**
	 * 发布项目中的H5
	 * @param htmlPageInfo
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "htmlPageInfo.customId")
	@Update
	public Object publishProject(@SqlParameter("htmlPageInfo") HtmlPageInfo htmlPageInfo){
		String sql = "UPDATE CC_HTML5_PAGE_INFO SET STATUS = 1  WHERE OWNER_ID = #{htmlPageInfo.ownerId} and STATUS=2 and CUSTOM_ID=#{htmlPageInfo.customId} "; 
		return sql;
	}
	
	
}
