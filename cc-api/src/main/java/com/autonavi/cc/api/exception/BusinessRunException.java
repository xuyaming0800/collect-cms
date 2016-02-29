package com.autonavi.cc.api.exception;

/**
 * 客户中心-异常处理
 * 
 * @author yaming.xu
 * 
 */
public class BusinessRunException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8072969293350518342L;
	
	private String errorCode;
	private String errorMessage;

	public BusinessRunException() {
		super();
	}

	public BusinessRunException(Throwable cause) {
		super(cause);
	}

	public BusinessRunException(String message) {
		super(message);
	}
	
	public BusinessRunException(String errorMessage, Throwable cause) {
		super(errorMessage,cause);
	}

	public BusinessRunException(String errorMessage, String errorCode) {
		super(errorMessage);
		this.errorCode = errorCode;
	}


	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
