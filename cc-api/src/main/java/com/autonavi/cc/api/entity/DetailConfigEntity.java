package com.autonavi.cc.api.entity;

import java.util.ArrayList;
import java.util.List;

public class DetailConfigEntity  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1995562012059843668L;
	private String id;//ID
	private String ownerId;//客户ID
	private String collectClassParentId; //采集品类大类ID（父类）
	private String collectClassId; //采集品类小类ID（子类）
	private String versionNo;//版本号
	private String createTime;//创建时间
	private String htmlText;//html页面内容
	private String jsText;//html页面内容
	private String cssText;//html页面内容
	private String webFlowText;//html页面跳转内容
	private List<CollectClassEntity> restrainInfos;//采集品类 （页面）的约束信息
	private int status;//状态
	private List<TypeEntity> datas;//json_text
	
	private String customId;//用户ID
	
	private  int pageNo;//当前页码
	private int  limit;//每页记录数
	
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

	public String getCollectClassParentId() {
		return collectClassParentId;
	}
	public void setCollectClassParentId(String collectClassParentId) {
		this.collectClassParentId = collectClassParentId;
	}
	public String getCollectClassId() {
		return collectClassId;
	}
	public void setCollectClassId(String collectClassId) {
		this.collectClassId = collectClassId;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
	public String getJsText() {
		return jsText;
	}
	public void setJsText(String jsText) {
		this.jsText = jsText;
	}
	public String getCssText() {
		return cssText;
	}
	public void setCssText(String cssText) {
		this.cssText = cssText;
	}
	public String getWebFlowText() {
		return webFlowText;
	}
	public void setWebFlowText(String webFlowText) {
		this.webFlowText = webFlowText;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<TypeEntity> getDatas() {
		return datas;
	}
	public void setDatas(List<TypeEntity> datas) {
		this.datas = datas;
	}
	public int getPageNo() {
		return pageNo;
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
	public List<CollectClassEntity> getRestrainInfos() {
		return restrainInfos;
	}
	public void setRestrainInfos(List<CollectClassEntity> restrainInfos) {
		this.restrainInfos = restrainInfos;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
		sb.append("id:");
		sb.append(this.getId());
		sb.append(",ownerId:");
		sb.append(this.getOwnerId());
		sb.append(",collectClassParentId:");
		sb.append(this.getCollectClassParentId());
		sb.append(",collectClassId:");
		sb.append(this.getCollectClassId());
		sb.append(",versionNo:");
		sb.append(this.getVersionNo());
		sb.append(",htmlText:");
		sb.append(this.getHtmlText());
		sb.append(",jsText:");
		sb.append(this.getJsText());
		sb.append(",cssText:");
		sb.append(this.getCssText());
		sb.append(",webFlowText:");
		sb.append(this.getWebFlowText());
		sb.append(",status:");
		sb.append(this.getStatus());
		sb.append(",datas:");
		sb.append(this.getDatas());
		sb.append(",restrainInfos:");
		sb.append(this.getRestrainInfos());
		sb.append("}");
		return sb.toString();
	}
}
