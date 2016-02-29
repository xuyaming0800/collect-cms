package com.autonavi.cc.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import autonavi.online.framework.sharding.dao.constant.ReservedWord;
import autonavi.online.framework.sharding.entry.aspect.annotation.Author;
import autonavi.online.framework.sharding.entry.aspect.annotation.Insert;
import autonavi.online.framework.sharding.entry.aspect.annotation.Select;
import autonavi.online.framework.sharding.entry.aspect.annotation.Shard;
import autonavi.online.framework.sharding.entry.aspect.annotation.SqlParameter;
import autonavi.online.framework.sharding.entry.aspect.annotation.Update;
import autonavi.online.framework.sharding.entry.entity.CollectionType;

import com.autonavi.cc.api.bean.DetailConfig;

/**
 * 增加，删除，查询和修改组件信息
 * 
 * @author wenpeng.jin
 *
 */
@Repository
public class DetailConfigDao {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月12日
	 * @description  根据客户ID,应用ID和采集品类ID查询组件
	 * @param dsKey
	 * @param owner_id
	 * @param collect_class_id
	 * @return
	 * @throws Exception
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "detailConfig.customId")
	@Select(collectionType = CollectionType.beanList, resultType = DetailConfig.class)
	public Object queryDetailConfigs(@SqlParameter("detailConfig") DetailConfig detailConfig) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID AS id,OWNER_ID AS ownerId,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_ID AS collectClassId,JSON_TEXT AS jsonText,TYPE AS type,ORDER_NO AS orderNo,VERSION_NO AS versionNo FROM CC_HTML5_DETAIL_CONFIG WHERE ");
		sql.append(" OWNER_ID = #{detailConfig.ownerId} ");
		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{detailConfig.collectClassParentId} ");
		sql.append(" AND COLLECT_CLASS_ID = #{detailConfig.collectClassId} ");
		sql.append(" AND STATUS = 1 ");
		sql.append(" ORDER BY  ORDER_NO");
		return sql.toString();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月17日
	 * @description 根据版本号查询组件信息
	 * @param detailConfig
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "detailConfig.customId")
	@Select(collectionType = CollectionType.beanList, resultType = DetailConfig.class)
	public Object queryDetailConfigsByVersionNo(@SqlParameter("detailConfig") DetailConfig detailConfig) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID AS id,OWNER_ID AS ownerId,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_ID AS collectClassId,JSON_TEXT AS jsonText,TYPE AS type,ORDER_NO AS orderNo,VERSION_NO AS versionNo  FROM CC_HTML5_DETAIL_CONFIG WHERE ");
		sql.append(" VERSION_NO = #{detailConfig.versionNo} and custom_id=#{detailConfig.customId} ");
		sql.append(" ORDER BY  ORDER_NO");
		return sql.toString();
	}
	
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 保存组件信息
	 * @param dataSourceKey
	 * @param list
	 * @param version_no
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "list."+ ReservedWord.index+".customId")
	@Insert
	public Object saveDetailConfigs(@SqlParameter("list") List<DetailConfig> list){
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO CC_HTML5_DETAIL_CONFIG (ID,OWNER_ID,COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_ID,JSON_TEXT,TYPE,STATUS,ORDER_NO,CREATE_TIME,VERSION_NO,CUSTOM_ID) VALUES (#{");
		sql.append(ReservedWord.snowflake + "},#{list." + ReservedWord.index);
		sql.append(".ownerId},#{list." + ReservedWord.index);
		sql.append(".collectClassParentId},#{list." + ReservedWord.index);
		sql.append(".collectClassId},#{list." + ReservedWord.index);
		sql.append(".jsonText},#{list." + ReservedWord.index);
		sql.append(".type},#{list." + ReservedWord.index);
		sql.append(".status},#{list." + ReservedWord.index);
		sql.append(".orderNo},unix_timestamp(now())*1000,#{list." + ReservedWord.index);
		sql.append(".versionNo},#{list." + ReservedWord.index);
		sql.append(".customId}) ");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月14日
	 * @description 根据版本号删除组件（逻辑删除）
	 * @param dataSourceKey
	 * @param detailConfig
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "detailConfig.customId")
	@Update
	public Object deleteDetailConfigs(@SqlParameter("detailConfig") DetailConfig detailConfig){
		String sql = "UPDATE CC_HTML5_DETAIL_CONFIG SET STATUS = 0 , UPDATE_TIME = #{detailConfig.updateTime}  WHERE VERSION_NO = #{detailConfig.versionNo} "; 
		return sql;
	}
}
