package com.autonavi.cc.api.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 组件信息bean
 * @author jinwenpeng
 *
 */
/**
 * 组件信息bean
 * @author jinwenpeng
 *
 */
public class DetailConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7474614911602565788L;
	
	private String id; //主键
	private String ownerId; //项目ID     
	private String customId = ""; //客户ID  
	private String collectClassParentId; //采集品类大类ID（父类）
	private String collectClassId; //采集品类小类ID（子类）
	private String jsonText;//组件json内容
	private String type;//组件类型（input,checkbox,button....）
	private int status = 1;//状态（0:废弃;1:正常）
	private int orderNo;//排序号
	private String versionNo;//版本号
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
	public String getJsonText() {
		return jsonText;
	}
	public void setJsonText(String jsonText) {
		this.jsonText = jsonText;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
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
		sb.append(",jsonText:");
		sb.append(this.getJsonText());
		sb.append(",status:");
		sb.append(this.getStatus());
		sb.append(",orderNo:");
		sb.append(this.getOrderNo());
		sb.append(",versionNo:");
		sb.append(this.getVersionNo());
		sb.append("}");
		return sb.toString();
	}
	
}
