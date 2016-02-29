package com.autonavi.cc.api.entity;

import com.autonavi.cc.api.bean.CollectClass;


public class ProjectInfoEntity  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1995562012059843668L;
	private String customId;//客户ID
	private String customName;//客户名称
	private String projectName;//项目名称
	private String blackWhiteListType;//黑白名单类型
	
	private CollectClass collectClass;

	
	private  int pageNo;//当前页码
	private int  limit;//每页记录数
	
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public int getPageNo() {
		return pageNo;
	}

	public CollectClass getCollectClass() {
		return collectClass;
	}

	public void setCollectClass(CollectClass collectClass) {
		this.collectClass = collectClass;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getBlackWhiteListType() {
		return blackWhiteListType;
	}

	public void setBlackWhiteListType(String blackWhiteListType) {
		this.blackWhiteListType = blackWhiteListType;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("customId:");
		sb.append(this.getCustomId());
		sb.append("customName:");
		sb.append(this.getCustomName());
		sb.append(",projectName:");
		sb.append(this.getProjectName());
		sb.append(",collectClass:");
		sb.append(this.getCollectClass());
		sb.append(",blackWhiteListType:");
		sb.append(this.getBlackWhiteListType());
		sb.append(",pageNo:");
		sb.append(this.getPageNo());
		sb.append(",limit:");
		sb.append(this.getLimit());
		sb.append("}");
		return sb.toString();
	}
}
