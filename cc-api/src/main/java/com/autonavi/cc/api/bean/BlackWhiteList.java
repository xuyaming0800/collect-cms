package com.autonavi.cc.api.bean;

import java.io.Serializable;
import java.util.Date;

public class BlackWhiteList implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9192783536897512485L;
	private String id;//主键
	private String ownerId;//项目Id
	private String projectName;//项目名称
	private int blackWhiteListType;//黑白名单类型
	private String blackWhiteListDesc;//黑白名单描述
	private int status = 1;//黑名单状态（0:非正常；1:正常）
	private String customId= "";//分库分表使用
	
	private Long createTime;// 创建时间
	private Long updateTime;//修改时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getBlackWhiteListType() {
		return blackWhiteListType;
	}
	public void setBlackWhiteListType(int blackWhiteListType) {
		this.blackWhiteListType = blackWhiteListType;
	}
	public String getBlackWhiteListDesc() {
		return blackWhiteListDesc;
	}
	public void setBlackWhiteListDesc(String blackWhiteListDesc) {
		this.blackWhiteListDesc = blackWhiteListDesc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("ID:");
		sb.append(this.getId());
		sb.append(",projectId:");
		sb.append(this.getOwnerId());
		sb.append(",projectName:");
		sb.append(this.getProjectName());
		sb.append(",blackWhitListType:");
		sb.append(this.getBlackWhiteListType());
		sb.append(",blackWhitListDesc:");
		sb.append(this.getBlackWhiteListDesc());
		sb.append(",status:");
		sb.append(this.getStatus());
		sb.append("}");
		return super.toString();
	}
	

}
