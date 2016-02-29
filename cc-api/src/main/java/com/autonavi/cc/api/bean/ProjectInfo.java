package com.autonavi.cc.api.bean;

import java.io.Serializable;
import java.util.Date;

public class ProjectInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1917251576666974681L;
	
	private String id;//主键 即为ownerId
	private String projectName;//项目Id
	private String appName;//应用名称
	private int projectType = 0;//项目类型0:简单项目；1:复杂项目
	private int status = 1;//项目状态（0:非正常；1:正常）
	private String customId;//客户ID
	private String customName;//客户名称
	private String projectLeaderId;//项目负责人ID
	private String projectLeaderName;//项目负责人名称
	private String projectDesc;//项目描述
	
	private Long createTime;// 创建时间
	private Long updateTime;//修改时间
	
	private Integer dsKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getProjectType() {
		return projectType;
	}
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	public String getProjectLeaderId() {
		return projectLeaderId;
	}
	public void setProjectLeaderId(String projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}
	public String getProjectLeaderName() {
		return projectLeaderName;
	}
	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	public Long getCreateTime() {
		if(createTime == null) {
			createTime = new Date().getTime();
		}
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		if(updateTime == null) {
			updateTime = new Date().getTime();
		}
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
	
	public Integer getDsKey() {
		return dsKey;
	}
	public void setDsKey(Integer dsKey) {
		this.dsKey = dsKey;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("ID:");
		sb.append(this.getId());
		sb.append(",projectName:");
		sb.append(this.getProjectName());
		sb.append(",appName:");
		sb.append(this.getAppName());
		sb.append(",projectType:");
		sb.append(this.getProjectType());
		sb.append(",status:");
		sb.append(this.getStatus());
		sb.append(",customId:");
		sb.append(this.getCustomId());
		sb.append(",customName:");
		sb.append(this.getCustomName());
		sb.append(",projectLeaderId:");
		sb.append(this.getProjectLeaderId());
		sb.append(",projectLeaderName:");
		sb.append(this.getProjectLeaderName());
		sb.append(",projectDesc:");
		sb.append(this.getProjectDesc());
		sb.append("}");
		return sb.toString();
	}
	

}
