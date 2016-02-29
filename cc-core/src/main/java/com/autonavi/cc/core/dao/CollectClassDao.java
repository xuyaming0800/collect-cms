package com.autonavi.cc.core.dao;

import org.springframework.stereotype.Repository;

import autonavi.online.framework.sharding.dao.constant.ReservedWord;
import autonavi.online.framework.sharding.entry.aspect.annotation.Author;
import autonavi.online.framework.sharding.entry.aspect.annotation.Insert;
import autonavi.online.framework.sharding.entry.aspect.annotation.Select;
import autonavi.online.framework.sharding.entry.aspect.annotation.Shard;
import autonavi.online.framework.sharding.entry.aspect.annotation.SingleDataSource;
import autonavi.online.framework.sharding.entry.aspect.annotation.SqlParameter;
import autonavi.online.framework.sharding.entry.aspect.annotation.Update;
import autonavi.online.framework.sharding.entry.entity.CollectionType;

import com.autonavi.cc.api.bean.CollectClass;
import com.autonavi.cc.api.bean.CollectTaskClazz;


@Repository
public class CollectClassDao {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 查询品类分页
	 * @param collectClass
	 * @param customId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.beanList, resultType = CollectClass.class)
	public Object queryCollectClass(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String customId,@SqlParameter("start") int start, @SqlParameter("limit") int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT task_Type as taskType, ID AS id,custom_Id as customId,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_TYPE AS collectClassType,COLLECT_CLASS_NAME AS collectClassName,STATUS AS status,ENTRANCE_STATUS AS entranceStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS collectClassPay,COLLECT_COUNT AS collectCount,COLLECT_CLASS_DISTANCE AS collectClassDistance,IS_REQUIRED AS isRequired  FROM cc_collect_class  WHERE  ");
		sql.append("  OWNER_ID = #{collectClass.ownerId} and CUSTOM_ID=#{customId} ");
				if(collectClass.getCollectClassParentId() != null && !"".equals(collectClass.getCollectClassParentId() )) {
					sql.append(" AND COLLECT_CLASS_PARENT_ID = #{collectClass.collectClassParentId}");
				}else {
					sql.append("  AND (COLLECT_CLASS_PARENT_ID IS NULL  OR COLLECT_CLASS_PARENT_ID = '' )");
				}
				sql.append("  AND STATUS = 1 ");
				sql.append("  LIMIT #{start},#{limit}");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 查询品类不分页
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.beanList, resultType = CollectClass.class)
	public Object queryCollectClass(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String customId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_TYPE AS collectClassType,COLLECT_CLASS_NAME AS collectClassName,STATUS AS status,ENTRANCE_STATUS AS entranceStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS collectClassPay,COLLECT_COUNT AS collectCount,COLLECT_CLASS_DISTANCE AS collectClassDistance,IS_REQUIRED AS isRequired  FROM cc_collect_class  WHERE  ");
		sql.append("  OWNER_ID = #{collectClass.ownerId} and CUSTOM_ID=#{customId} ");
				if(collectClass.getCollectClassParentId() != null && !"".equals(collectClass.getCollectClassParentId() )) {
					sql.append(" AND COLLECT_CLASS_PARENT_ID = #{collectClass.collectClassParentId}");
				}else {
					sql.append("  AND (COLLECT_CLASS_PARENT_ID IS NULL  OR COLLECT_CLASS_PARENT_ID = '' )");
				}
				//sql.append("  AND ENTRANCE_STATUS != 1 ");
				sql.append("  AND STATUS = 1 ");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 获取导航页
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.beanList, resultType = CollectClass.class)
	public Object queryNavCollectClass(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String customId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_TYPE AS collectClassType,COLLECT_CLASS_NAME AS collectClassName,STATUS AS status,ENTRANCE_STATUS AS entranceStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS collectClassPay,COLLECT_COUNT AS collectCount,COLLECT_CLASS_DISTANCE AS collectClassDistance,IS_REQUIRED AS isRequired  FROM cc_collect_class  WHERE  ");
		sql.append("  OWNER_ID = #{collectClass.ownerId} and CUSTOM_ID=#{customId} ");
				if(collectClass.getCollectClassParentId() != null && !"".equals(collectClass.getCollectClassParentId() )) {
					sql.append(" AND COLLECT_CLASS_PARENT_ID = #{collectClass.collectClassParentId}");
				}else {
					sql.append("  AND (COLLECT_CLASS_PARENT_ID IS NULL  OR COLLECT_CLASS_PARENT_ID = '' )");
				}
				sql.append("  AND ENTRANCE_STATUS = 1 ");
				sql.append("  AND STATUS = 1 ");
		return sql.toString();
	}
	/**
	 * 查询分类信息
	 * @param id
	 * @param ownerId
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.bean, resultType = CollectClass.class)
	public Object queryCollectClassById(@SqlParameter("id") String  id,@SqlParameter("ownerId") String ownerId,@SqlParameter("customId") String customId,@SqlParameter("collectClassParentId") String collectClassParentId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_TYPE AS collectClassType,COLLECT_CLASS_NAME AS collectClassName,STATUS AS status,ENTRANCE_STATUS AS entranceStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS collectClassPay,COLLECT_COUNT AS collectCount,COLLECT_CLASS_DISTANCE AS collectClassDistance,IS_REQUIRED AS isRequired  FROM cc_collect_class  WHERE  ");
		sql.append("  OWNER_ID = #{ownerId} and CUSTOM_ID=#{customId} ");
		sql.append(" and id=#{id} and COLLECT_CLASS_PARENT_ID=#{collectClassParentId}");
		return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.bean, resultType = CollectClass.class)
	public Object queryCollectEntranceClass(@SqlParameter("ownerId") String ownerId,@SqlParameter("customId") String customId,@SqlParameter("collectClassParentId") String collectClassParentId,@SqlParameter("entranceStatus")  Integer entranceStatus ) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_TYPE AS collectClassType,COLLECT_CLASS_NAME AS collectClassName,STATUS AS status,ENTRANCE_STATUS AS entranceStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS collectClassPay,COLLECT_COUNT AS collectCount,COLLECT_CLASS_DISTANCE AS collectClassDistance,IS_REQUIRED AS isRequired  FROM cc_collect_class  WHERE  ");
		sql.append("  OWNER_ID = #{ownerId} and CUSTOM_ID=#{customId} ");
		sql.append(" and COLLECT_CLASS_PARENT_ID=#{collectClassParentId} and ENTRANCE_STATUS=#{entranceStatus} ");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 获取记录数
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.column, resultType = Long.class)
	public Object queryCollectClassCount(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String customId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(ID) FROM cc_collect_class  WHERE  ");
		sql.append("  OWNER_ID = #{collectClass.ownerId} ");
		if(collectClass.getCollectClassParentId() != null && !"".equals(collectClass.getCollectClassParentId() )) {
			sql.append(" AND COLLECT_CLASS_PARENT_ID = #{collectClass.collectClassParentId}");
		}else {
			sql.append("  AND (COLLECT_CLASS_PARENT_ID IS NULL  OR COLLECT_CLASS_PARENT_ID = '' ) ");
		}
		sql.append("  AND STATUS = 1 ");
		return sql.toString();
	}
	
	
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="dsKey")
	@Select(collectionType = CollectionType.beanList, resultType = CollectTaskClazz.class)
	public Object selectAllVaildTaskClazz(@SqlParameter("dsKey") Integer dsKey){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS parentId,COLLECT_CLASS_TYPE AS clazzType,COLLECT_CLASS_NAME AS clazzName,STATUS AS clazzStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS clazzPay,COLLECT_CLASS_DISTANCE AS clazzDistance  FROM cc_collect_class  ");
		sql.append("  WHERE STATUS = 1 ");
		return sql.toString();
	}
	/**
	 * 查询所有分类
	 * @param dsKey
	 * @return
	 */
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="dsKey")
	@Select(collectionType = CollectionType.beanList, resultType = CollectTaskClazz.class)
	public Object selectAllTaskClazz(@SqlParameter("dsKey") Integer dsKey){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS parentId,COLLECT_CLASS_TYPE AS clazzType,COLLECT_CLASS_NAME AS clazzName,STATUS AS clazzStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS clazzPay,COLLECT_CLASS_DISTANCE AS clazzDistance  FROM cc_collect_class  ");
		return sql.toString();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月9日
	 * @description 根据ID查询分类
	 * @param dsKey
	 * @param id
	 * @return
	 */
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="dsKey")
	@Select(collectionType = CollectionType.bean, resultType = CollectTaskClazz.class)
	public Object selectTaskClazzById(@SqlParameter("dsKey") Integer dsKey,@SqlParameter("id") String id){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType, ID AS id ,COLLECT_CLASS_PARENT_ID AS parentId,COLLECT_CLASS_TYPE AS clazzType,COLLECT_CLASS_NAME AS clazzName,STATUS AS clazzStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS clazzPay,COLLECT_CLASS_DISTANCE AS clazzDistance  FROM cc_collect_class  ");
		sql.append(" WHERE id = #{id} ");
		return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.bean, resultType = CollectTaskClazz.class)
	public Object selectTaskClazzById(@SqlParameter("id") String id,@SqlParameter("customId") String customId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS parentId,COLLECT_CLASS_TYPE AS clazzType,COLLECT_CLASS_NAME AS clazzName,STATUS AS clazzStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS clazzPay,COLLECT_CLASS_DISTANCE AS clazzDistance  FROM cc_collect_class  ");
		sql.append(" WHERE id = #{id} ");
		return sql.toString();
	}
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.beanList, resultType = CollectTaskClazz.class)
	public Object selectVaildTaskClazzByOwnerId(@SqlParameter("owner_id") String ownerId,@SqlParameter("customId") String customId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS parentId,COLLECT_CLASS_TYPE AS clazzType,COLLECT_CLASS_NAME AS clazzName,STATUS AS clazzStatus,OWNER_ID AS ownerId,COLLECT_CLASS_PAY AS clazzPay,COLLECT_CLASS_DISTANCE AS clazzDistance  FROM cc_collect_class  ");
		sql.append(" WHERE owner_id = #{owner_id} and status=1 ");
		return sql.toString();
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 保存品类基本信息
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Insert
	public Object saveCollectClass(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String  customId) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO cc_collect_class (ID,INIT_CLASS_ID,COLLECT_CLASS_PARENT_ID,COLLECT_CLASS_TYPE,COLLECT_CLASS_NAME,OWNER_ID,COLLECT_CLASS_PAY,COLLECT_COUNT,COLLECT_CLASS_DISTANCE ,IS_REQUIRED,CREATE_TIME,UPDATE_TIME,CUSTOM_ID,TASK_TYPE) VALUES (");
		sql.append("#{"+ReservedWord.snowflake+"}");
		sql.append(" ,#{collectClass.initClassId}");
		sql.append(" ,#{collectClass.collectClassParentId}");
		sql.append(" ,#{collectClass.collectClassType}");
		sql.append(" ,#{collectClass.collectClassName}");
		sql.append(" ,#{collectClass.ownerId}");
		sql.append(" ,#{collectClass.collectClassPay}");
		sql.append(" ,#{collectClass.collectCount}");
		sql.append(" ,#{collectClass.collectClassDistance}");
		sql.append(" ,#{collectClass.isRequired}");
		sql.append(" ,unix_timestamp(now())*1000");
		sql.append(" ,unix_timestamp(now())*1000");
		sql.append(" ,#{customId}");
		sql.append(" ,#{collectClass.taskType})");
		return sql.toString();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 更新品类基本信息
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Update
	public Object updateCollectClass(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String  customId){
		String sql = "UPDATE cc_collect_class SET COLLECt_CLASS_NAME = #{collectClass.collectClassName},COLLECT_CLASS_PAY = #{collectClass.collectClassPay},IS_REQUIRED = #{collectClass.isRequired},COLLECT_COUNT = #{collectClass.collectCount},COLLECT_CLASS_DISTANCE =#{collectClass.collectClassDistance},UPDATE_TIME =  unix_timestamp(now())*1000  WHERE ID = #{collectClass.id} and custom_id=#{customId}"; 
		return sql;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 更新品类状态
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Update
	public Object updateCollectClassStatus(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String  customId){
		String sql = "UPDATE cc_collect_class SET STATUS = #{collectClass.status},UPDATE_TIME =  unix_timestamp(now())*1000   WHERE ID = #{collectClass.id} and custom_id=#{customId} "; 
		return sql;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 同一父类的都设置为非入口
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Update
	public Object updateAllCollectClassEntranceStatus(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String  customId){
		String sql = "UPDATE cc_collect_class  SET ENTRANCE_STATUS = 0,UPDATE_TIME =  unix_timestamp(now())*1000 WHERE COLLECT_CLASS_PARENT_ID = #{collectClass.collectClassParentId} and CUSTOM_ID=#{customId}"; 
		return sql;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月2日
	 * @description 设置为入口状态
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Update
	public Object updateCollectClassEntranceStatus(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String  customId){
		String sql = "UPDATE cc_collect_class SET ENTRANCE_STATUS = #{collectClass.entranceStatus},UPDATE_TIME =  unix_timestamp(now())*1000   WHERE ID = #{collectClass.id} and CUSTOM_ID=#{customId}"; 
		return sql;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月1日
	 * @description 根据ownerId和collectclassParentId查询所有子类
	 * @param collectClass
	 * @param customId
	 * @return
	 */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.beanList, resultType = CollectClass.class)
	public Object queryCollectClassChild(@SqlParameter("collectClass") CollectClass  collectClass,@SqlParameter("customId") String customId) {
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT ID AS id ,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_TYPE AS collectClassType,COLLECT_CLASS_NAME AS collectClassName,STATUS AS status,OWNER_ID AS ownerId,COLLECT_COUNT AS collectCount,IS_REQUIRED AS isRequired FROM cc_collect_class  WHERE  ");
//		sql.append("  OWNER_ID = #{collectClass.ownerId} ");
//		sql.append(" AND COLLECT_CLASS_PARENT_ID = #{collectClass.collectClassParentId}");
//		sql.append("  AND STATUS = 1 ");
		sql.append(" SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_TYPE AS ");
		sql.append(" collectClassType,COLLECT_CLASS_NAME AS collectClassName,STATUS AS status,OWNER_ID AS ownerId,");
		sql.append(" COLLECT_COUNT AS collectCount,COLLECT_CLASS_DISTANCE AS collectClassDistance,IS_REQUIRED AS isRequired,d.version_no as versionNo FROM cc_collect_class a inner join( ");
		sql.append(" select b.collect_class_id collect_class_id,VERSION_NO from ( ");
		sql.append(" select collect_class_id,max(update_time) update_time from cc_html5_page_info where ");
		sql.append(" collect_class_parent_id=#{collectClass.collectClassParentId} and OWNER_ID = #{collectClass.ownerId} AND (STATUS = 1 or STATUS=2 ) group by collect_class_id)b  ");
		sql.append(" inner join ( ");
		sql.append(" select collect_class_id,update_time,version_no from cc_html5_page_info where ");
		sql.append(" collect_class_parent_id=#{collectClass.collectClassParentId} and OWNER_ID = #{collectClass.ownerId} AND (STATUS = 1 or STATUS=2 ) )c  ");
		sql.append(" on c.update_time=b.update_time and c.collect_class_id=b.collect_class_id )d ");
		sql.append(" on a.id=d.collect_class_id and a.status=1 ");

		return sql.toString();
	}
	
	 /**
	  * 
	  * @author wenpeng.jin
	  * @date 2015年10月20日
	  * @description 查询项目的采集品类大类
	  * @param ownerId
	  * @param customId
	  * @return
	  */
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "customId")
	@Select(collectionType = CollectionType.beanList, resultType = CollectClass.class)
	public Object queryCollectClass(@SqlParameter("ownerId") String  ownerId,@SqlParameter("customId") String customId,@SqlParameter("taskType") int taskType) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TASK_TYPE as taskType,ID AS id ,COLLECT_CLASS_PARENT_ID AS collectClassParentId,COLLECT_CLASS_TYPE AS collectClassType,COLLECT_CLASS_NAME AS collectClassName,STATUS AS status,OWNER_ID AS ownerId,COLLECT_COUNT AS collectCount,COLLECT_CLASS_DISTANCE AS collectClassDistance,IS_REQUIRED AS isRequired FROM cc_collect_class  WHERE  ");
		sql.append(" OWNER_ID = #{ownerId} ");
		sql.append(" AND CUSTOM_ID = #{customId}");
		sql.append(" AND TASK_TYPE = #{taskType}");
		sql.append(" AND STATUS = 1 ");
		sql.append(" AND COLLECT_CLASS_TYPE = 0");
		sql.append(" AND (COLLECT_CLASS_PARENT_ID IS NULL OR COLLECT_CLASS_PARENT_ID = '')");
		return sql.toString();
	}

	
}
