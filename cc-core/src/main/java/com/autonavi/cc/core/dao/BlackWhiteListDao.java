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

import com.autonavi.cc.api.bean.BlackWhiteList;
import com.autonavi.cc.api.bean.ProjectInfo;


@Repository
public class BlackWhiteListDao {
	
	
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "blackWhiteList.customId")
	@Select(collectionType = CollectionType.beanList, resultType = BlackWhiteList.class)
	public Object queryBlackWhiteList(@SqlParameter("blackWhiteList") BlackWhiteList  blackWhiteList,@SqlParameter("start") int start, @SqlParameter("limit") int limit) {
		StringBuffer sql = new StringBuffer();
		 sql.append("SELECT cbwl.ID AS id,cbwl.OWNER_ID AS ownerId,cpi.PROJECT_NAME AS projectName,cbwl.BLACK_WHITE_LIST_TYPE AS blackWhiteListType,cbwl.BLACK_WHITE_LIST_DESC AS blackWhiteListDesc,cbwl.STATUS AS status FROM cc_black_white_list cbwl LEFT JOIN cc_project_info cpi ON cbwl.OWNER_ID = cpi.ID  WHERE  ") ;
		 sql.append( " cbwl.STATUS = 1 ");
		 if(blackWhiteList.getProjectName() != null && !"".equals(blackWhiteList.getProjectName())) {
			 sql.append( " AND cpi.PROJECT_NAME LIKE '${blackWhiteList.projectName}%' ");
		}
		if(blackWhiteList.getBlackWhiteListType() != 9) {
			 sql.append( " AND cbwl.BLACK_WHITE_LIST_TYPE = #{blackWhiteList.blackWhiteListType} ");
		}
		 sql.append(" ORDER BY cbwl.CREATE_TIME DESC ");
		 sql.append("  LIMIT #{start},#{limit}");
		return sql.toString();
	}
	

	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "blackWhiteList.customId")
	@Select(collectionType = CollectionType.column, resultType = Long.class)
	public Object queryBlackWhiteListCount(@SqlParameter("blackWhiteList") BlackWhiteList  blackWhiteList) {
		StringBuffer sql = new StringBuffer();
		 sql.append("SELECT COUNT(cbwl.ID) FROM cc_black_white_list cbwl LEFT JOIN cc_project_info cpi ON cbwl.OWNER_ID = cpi.ID  WHERE ");
		 sql.append( " cbwl.STATUS = 1 ");
		if(blackWhiteList.getProjectName() != null && !"".equals(blackWhiteList.getProjectName())) {
			 sql.append( " AND cpi.PROJECT_NAME LIKE '${blackWhiteList.projectName}%' ");
		}
		return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "blackWhiteList.customId")
	@Insert
	public Object saveBlackWhiteList(@SqlParameter("blackWhiteList") BlackWhiteList  blackWhiteList) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO cc_black_white_list (ID,OWNER_ID,BLACK_WHITE_LIST_TYPE,BLACK_WHITE_LIST_DESC,STATUS,CREATE_TIME) VALUES (");
		sql.append("#{"+ ReservedWord.snowflake + "}");
		sql.append(" ,#{blackWhiteList.ownerId}");
		sql.append(" ,#{blackWhiteList.blackWhiteListType}");
		sql.append(" ,#{blackWhiteList.blackWhiteListDesc}");
		sql.append(" ,#{blackWhiteList.status}");
		sql.append(" ,#{blackWhiteList.createTime} )");
		return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "blackWhiteList.customId")
	@Update
	public Object updateBlackWhiteList(@SqlParameter("blackWhiteList") BlackWhiteList  blackWhiteList) {
		String sql = "UPDATE cc_black_white_list SET OWNER_ID = #{blackWhiteList.ownerId},BLACK_WHITE_LIST_TYPE = #{blackWhiteList.blackWhiteListType},BLACK_WHITE_LIST_DESC = #{blackWhiteList.blackWhiteListDesc},UPDATE_TIME = #{blackWhiteList.updateTime}  WHERE ID =  #{blackWhiteList.id}";
		return sql;
	}
	
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "blackWhiteList.customId")
	@Update
	public Object updateBlackWhiteListStatus(@SqlParameter("blackWhiteList") BlackWhiteList  blackWhiteList) {
		String sql = "UPDATE cc_black_white_list SET STATUS = #{blackWhiteList.status} ,UPDATE_TIME = #{blackWhiteList.updateTime} WHERE ID =  #{blackWhiteList.id}";
		return sql;
	}
	
	@Author("wenpeng.jin")
	@Shard(indexName = "cc_custom_id_index", indexColumn = "blackWhiteList.customId")
	@Select(collectionType = CollectionType.beanList, resultType = BlackWhiteList.class)
	public Object checkUniqueProject(@SqlParameter("blackWhiteList") BlackWhiteList  blackWhiteList) {
		String sql = "SELECT ID AS id,OWNER_ID AS ownerId,BLACK_WHITE_LIST_TYPE AS blackWhiteListType,BLACK_WHITE_LIST_DESC AS blackWhiteListDesc,STATUS AS status FROM cc_black_white_list WHERE OWNER_ID = #{blackWhiteList.ownerId} AND STATUS = 1";
		return sql;
	}
}
