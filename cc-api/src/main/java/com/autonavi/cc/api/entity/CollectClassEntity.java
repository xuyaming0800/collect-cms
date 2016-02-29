package com.autonavi.cc.api.entity;

public class CollectClassEntity  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 646231726296179966L;
	
	private String id;//主键
	private int isRequired;//是否必须 0:非必须,1:必须
	private int collectCount;//采集次数
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("id:");
		sb.append(this.getId());
		sb.append(",isRequired:");
		sb.append(this.getIsRequired());
		sb.append(",collectCount:");
		sb.append(this.getCollectCount());
		sb.append("}");
		return sb.toString();
	}
}
