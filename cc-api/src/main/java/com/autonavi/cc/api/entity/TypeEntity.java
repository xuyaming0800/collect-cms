package com.autonavi.cc.api.entity;

import java.util.ArrayList;
import java.util.List;


public class TypeEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1722428203516870976L;
	
	private String type;//组件类型（input,checkbox,button....）
	private Object jsonText;//组件json内容
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getJsonText() {
		return jsonText;
	}
	public void setJsonText(Object jsonText) {
		this.jsonText = jsonText;
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("type:");
		sb.append(this.getType());
		sb.append(",jsonText:");
		sb.append(this.getJsonText());
		sb.append("}");
		return sb.toString();
	}
}
