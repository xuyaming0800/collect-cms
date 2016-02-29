package com.autonavi.cc.api.bean;

import java.io.Serializable;

public class WebFlowNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2748877097871494517L;
	
	private String on;
	private String to;
	public String getOn() {
		return on;
	}
	public void setOn(String on) {
		this.on = on;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("on:");
		sb.append(this.getOn());
		sb.append(",to:");
		sb.append(this.getTo());
		sb.append("}");
		return super.toString();
	}

}
