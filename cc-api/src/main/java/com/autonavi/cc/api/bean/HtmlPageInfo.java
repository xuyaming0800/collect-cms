package com.autonavi.cc.api.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * html页面信息
 * @author wenpeng.jin
 *
 */
public class HtmlPageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -474877215345449360L;

	private String id; //主键
	private String ownerId; //项目ID    
	private String ownerName; //项目名称
	private String customId = ""; //客户ID   为了分库分表 
	private String collectClassParentId; //采集品类大类ID（父类）
	private String collectClassParentName; //采集品类大类ID（父类）
	private String collectClassId; //采集品类小类ID（子类）
	private String collectClassName; //采集品类小类ID（子类）
	private String collectClassPay; //采集品类小类ID（子类）
	private String htmlText;//html内容
	private String jsText;//js内容
	private String cssText;//css内容
	private String webFlowText;//页面跳转内容
	private int status = 2;//状态（0:废弃;1:正常(发布),2正常(未发布)）
	private String versionNo;//版本号
	private Long createTime;// 创建时间
	private Long updateTime;//修改时间
	
	private String createTimeFormat;// 创建时间
	
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
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
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

	public String getCollectClassParentName() {
		return collectClassParentName;
	}
	public void setCollectClassParentName(String collectClassParentName) {
		this.collectClassParentName = collectClassParentName;
	}
	public String getCollectClassName() {
		return collectClassName;
	}
	public void setCollectClassName(String collectClassName) {
		this.collectClassName = collectClassName;
	}
	public String getCollectClassPay() {
		return collectClassPay;
	}
	public void setCollectClassPay(String collectClassPay) {
		this.collectClassPay = collectClassPay;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	
	
	public String getCreateTimeFormat() {
		return createTimeFormat;
	}
	public void setCreateTimeFormat(String createTimeFormat) {
		this.createTimeFormat = createTimeFormat;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("id:");
		sb.append(this.getId());
		sb.append(",ownerId:");
		sb.append(this.getOwnerId());
		sb.append(",ownerName:");
		sb.append(this.getOwnerName());
		sb.append(",collectClassParentId:");
		sb.append(this.getCollectClassParentId());
		sb.append(",collectClassParentName:");
		sb.append(this.getCollectClassParentName());
		sb.append(",collectClassId:");
		sb.append(this.getCollectClassId());
		sb.append(",collectClassName:");
		sb.append(this.getCollectClassName());
		sb.append(",collectClassPay:");
		sb.append(this.getCollectClassPay());
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
		sb.append(",versionNo:");
		sb.append(this.getVersionNo());
		sb.append("}");
		return sb.toString();
	}
}
