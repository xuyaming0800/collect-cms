package com.autonavi.cc.core.dao;

import java.util.List;

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

import com.autonavi.cc.api.bean.ProjectInfo;
import com.autonavi.cc.core.util.SysProps;


@Repository
public class ProjectInfoDao {
	
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="dsKey")
	@Select(collectionType = CollectionType.beanList, resultType = ProjectInfo.class)
	public Object queryAllProjectInfos(@SqlParameter("customId") String customId,@SqlParameter("dsKey") Integer dsKey,@SqlParameter("start") int start, @SqlParameter("limit") int limit) {
		StringBuffer sql = new StringBuffer();
		 sql.append(" SELECT ID AS id,PROJECT_NAME AS projectName,APP_NAME AS appName,PROJECT_TYPE AS projectType,CUSTOM_ID AS customId,PROJECT_LEADER_ID AS projectLeaderId,PROJECT_DESC AS projectDesc,STATUS AS status,CREATE_TIME AS createTime FROM cc_project_info ") ;
		 sql.append(" WHERE STATUS = 1 ");
		 if(customId != null && !"".equals(customId)) {
			sql.append(" AND CUSTOM_ID = #{customId} "); 
		 }
		 sql.append(" ORDER BY CREATE_TIME DESC ");
		 if(limit != 0) {
			 sql.append(" LIMIT #{start},#{limit}");
		 }
		 
		 return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="dsKey")
	@Select(collectionType = CollectionType.beanList, resultType = ProjectInfo.class)
	public Object queryAllProjectInfos(@SqlParameter("dsKey") Integer dsKey) {
		StringBuffer sql = new StringBuffer();
		 sql.append(" SELECT ID AS id,PROJECT_NAME AS projectName,APP_NAME AS appName,PROJECT_TYPE AS projectType,CUSTOM_ID AS customId,PROJECT_LEADER_ID AS projectLeaderId,PROJECT_DESC AS projectDesc,STATUS AS status,CREATE_TIME AS createTime FROM cc_project_info ") ;
		 return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="dsKey")
	@Select(collectionType = CollectionType.bean, resultType = ProjectInfo.class)
	public Object queryProjectInfoById(@SqlParameter("id") String id,@SqlParameter("dsKey") Integer dsKey){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID AS id,PROJECT_NAME AS projectName,APP_NAME AS appName,PROJECT_TYPE AS projectType,CUSTOM_ID AS customId,PROJECT_LEADER_ID AS projectLeaderId,PROJECT_DESC AS projectDesc,STATUS AS status,CREATE_TIME AS createTime FROM cc_project_info ") ;
		sql.append(" WHERE id = #{id} ");
		return sql.toString();
	}
	
	
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="projectInfo.dsKey")
	@Select(collectionType = CollectionType.beanList, resultType = ProjectInfo.class)
	public Object queryProjectInfos(@SqlParameter("projectInfo") ProjectInfo  projectInfo,List<String> idList,@SqlParameter("start") int start, @SqlParameter("limit") int limit) {
		StringBuffer sql = new StringBuffer();
		 sql.append("SELECT ID AS id,PROJECT_NAME AS projectName,APP_NAME AS appName,PROJECT_TYPE AS projectType,CUSTOM_ID AS customId,PROJECT_LEADER_ID AS projectLeaderId,PROJECT_DESC AS projectDesc,STATUS AS status,CREATE_TIME AS createTime FROM cc_project_info WHERE  1 = 1 ") ;
				if(idList != null) {
					if(idList.size() > 0) {
						 sql.append(" AND CUSTOM_ID in ( ");
						 int count = 0;
						 for(String id : idList) {
							 sql.append("'");
							 sql.append(id);
							 sql.append("'");
							 if(count < idList.size() -1) {
								 sql.append(",");
							 }
							 count++;
						 }
						 sql.append(") ");
					}else {
						 sql.append(" AND CUSTOM_ID IS NULL ");
					}
				}
				
				if(projectInfo.getCustomId() != null && !"".equals(projectInfo.getCustomId())) {
					 sql.append( " AND CUSTOM_ID = #{projectInfo.customId} ");
				}
				
				if(projectInfo.getId() != null && !"".equals(projectInfo.getId())) {
					 sql.append( " AND ID = #{projectInfo.id} ");
				}
		 		
				if(projectInfo.getProjectName() != null && !"".equals(projectInfo.getProjectName())) {
					 sql.append( " AND PROJECT_NAME LIKE #{projectInfo.projectName} ");
				}
				if(projectInfo.getStatus() == 1) {
					sql.append(" AND STATUS = 1");
				}
				 sql.append(" ORDER BY STATUS DESC, CREATE_TIME DESC");
				 if(limit != 0) {
					 sql.append("  LIMIT #{start},#{limit}");
				 }
				 
		return sql.toString();
	}
	

	@Author("wenpeng.jin")
	@SingleDataSource(keyName="projectInfo.dsKey")
	@Select(collectionType = CollectionType.column, resultType = Long.class)
	public Object queryProjectInfosCount(@SqlParameter("projectInfo") ProjectInfo  projectInfo,List<String> idList) {
		StringBuffer sql = new StringBuffer();
		 sql.append("SELECT COUNT(ID) FROM cc_project_info WHERE  1 = 1 ");
			 if(idList != null) {
					if(idList.size() > 0) {
						 sql.append(" AND CUSTOM_ID in ( ");
						 int count = 0;
						 for(String id : idList) {
							 sql.append("'");
							 sql.append(id);
							 sql.append("'");
							 if(count < idList.size() -1) {
								 sql.append(",");
							 }
							 count++;
						 }
						 sql.append(") ");
					}else {
						 sql.append(" AND CUSTOM_ID IS NULL ");
					}
				}
	 		
				 if(projectInfo.getCustomId() != null && !"".equals(projectInfo.getCustomId())) {
					 sql.append( " AND CUSTOM_ID = #{projectInfo.customId} ");
				}
				
				if(projectInfo.getId() != null && !"".equals(projectInfo.getId())) {
					 sql.append( " AND ID = #{projectInfo.id} ");
				}
				if(projectInfo.getProjectName() != null && !"".equals(projectInfo.getProjectName())) {
					 sql.append( " AND PROJECT_NAME LIKE '${projectInfo.projectName}%' ");
				}
				if(projectInfo.getStatus() == 1) {
					sql.append(" AND STATUS = 1");
				}
		return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="projectInfo.dsKey")
	@Insert
	public Object saveProjectInfo(@SqlParameter("projectInfo") ProjectInfo  projectInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO cc_project_info (ID,PROJECT_NAME,APP_NAME,PROJECT_TYPE,CUSTOM_ID,PROJECT_LEADER_ID,PROJECT_DESC ,STATUS,CREATE_TIME,UPDATE_TIME) VALUES (");
		sql.append("#{"+ ReservedWord.snowflake + "}");
		sql.append(" ,#{projectInfo.projectName}");
		sql.append(" ,#{projectInfo.appName}");
		sql.append(" ,#{projectInfo.projectType}");
		sql.append(" ,#{projectInfo.customId}");
		sql.append(" ,#{projectInfo.projectLeaderId}");
		sql.append(" ,#{projectInfo.projectDesc}");
		sql.append(" ,#{projectInfo.status}");
		sql.append(" ,unix_timestamp(now())*1000");
		sql.append(" ,unix_timestamp(now())*1000 )");
		return sql.toString();
	}
	
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="projectInfo.dsKey")
	@Update
	public Object updateProjectInfo(@SqlParameter("projectInfo") ProjectInfo  projectInfo) {
		String sql = "UPDATE cc_project_info SET PROJECT_NAME = #{projectInfo.projectName},APP_NAME = #{projectInfo.appName},PROJECT_TYPE = #{projectInfo.projectType},PROJECT_LEADER_ID = #{projectInfo.projectLeaderId},PROJECT_DESC = #{projectInfo.projectDesc},UPDATE_TIME = unix_timestamp(now())*1000  WHERE ID =  #{projectInfo.id} and CUSTOM_ID = #{projectInfo.customId}";
		return sql;
	}
	
	@Author("wenpeng.jin")
	@SingleDataSource(keyName="projectInfo.dsKey")
	@Update
	public Object updateProjectInfoStatus(@SqlParameter("projectInfo") ProjectInfo  projectInfo) {
		String sql = "UPDATE cc_project_info SET STATUS = #{projectInfo.status},UPDATE_TIME = unix_timestamp(now())*1000   WHERE ID =  #{projectInfo.id} and CUSTOM_ID = #{projectInfo.customId} ";
		return sql;
	}
}
