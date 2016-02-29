package com.autonavi.cc.api.bean;

import java.io.Serializable;
import java.util.Date;

public class CollectClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3129652840582801776L;
	
	private String id;//主键
	private String initClassId;//第一次建立品类的ID值
	private String taskType;//主被动标记
	private String collectClassParentId;//父类ID
	private int collectClassType;//分类是目录还是分类0 目录 1 分类
	private String collectClassName;//品类名称
	private Double collectClassPay;//品类价格 采集价格
	private Double collectClassPayMin;//扣除客户的最小金额
	private Double collectClassPayMax;//扣除客户的最大金额
	private int status = 1;//品类状态 1 有效 0 无效 
	private int entranceStatus = 0;//功能入口状态0：非入口，1：入口
	private String ownerId;//项目ID(应用ID)
	private int collectClassDistance;//拍摄间隔距离(米)
	private int isRequired;//是否必须 0:非必须,1:必须 
	private int collectCount;//采集次数
	private String versionNo;
	
	private Long createTime;// 创建时间
	private Long updateTime;//修改时间
	
	private String customId; //用户ID
	
	private Integer dsKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getInitClassId() {
		return initClassId;
	}
	public void setInitClassId(String initClassId) {
		this.initClassId = initClassId;
	}
	public String getCollectClassParentId() {
		return collectClassParentId;
	}
	public void setCollectClassParentId(String collectClassParentId) {
		this.collectClassParentId = collectClassParentId;
	}
	public int getCollectClassType() {
		return collectClassType;
	}
	public void setCollectClassType(int collectClassType) {
		this.collectClassType = collectClassType;
	}
	public String getCollectClassName() {
		return collectClassName;
	}
	public void setCollectClassName(String collectClassName) {
		this.collectClassName = collectClassName;
	}
	public Double getCollectClassPay() {
		return collectClassPay;
	}
	public void setCollectClassPay(Double collectClassPay) {
		this.collectClassPay = collectClassPay;
	}
	public Double getCollectClassPayMin() {
		return collectClassPayMin;
	}
	public void setCollectClassPayMin(Double collectClassPayMin) {
		this.collectClassPayMin = collectClassPayMin;
	}
	public Double getCollectClassPayMax() {
		return collectClassPayMax;
	}
	public void setCollectClassPayMax(Double collectClassPayMax) {
		this.collectClassPayMax = collectClassPayMax;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public int getCollectClassDistance() {
		return collectClassDistance;
	}
	public void setCollectClassDistance(int collectClassDistance) {
		this.collectClassDistance = collectClassDistance;
	}
	public int getEntranceStatus() {
		return entranceStatus;
	}
	public void setEntranceStatus(int entranceStatus) {
		this.entranceStatus = entranceStatus;
	}
	public int getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}
	public int getCollectCount() {
		return collectCount;
	}
	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
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
	
	
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	
	
	
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Integer getDsKey() {
		return dsKey;
	}
	public void setDsKey(Integer dsKey) {
		this.dsKey = dsKey;
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
		sb.append(",initClassId:");
		sb.append(this.getInitClassId());
		sb.append(",collectClassParentId:");
		sb.append(this.getCollectClassParentId());
		sb.append(",collectClassType:");
		sb.append(this.getCollectClassType());
		sb.append(",collectClassPay:");
		sb.append(this.getCollectClassPay());
		sb.append(",status:");
		sb.append(this.getStatus());
		sb.append(",ownerId:");
		sb.append(this.getOwnerId());
		sb.append(",collectClassDistance:");
		sb.append(this.getCollectClassDistance());
		sb.append(",isRequired:");
		sb.append(this.getIsRequired());
		sb.append(",collectCount:");
		sb.append(this.getCollectCount());
		sb.append(",entranceStatus:");
		sb.append(this.getEntranceStatus());
		sb.append("}");
		return sb.toString();
	}
}
