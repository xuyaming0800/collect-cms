package com.autonavi.cc.api.exception;

public class ExceptionCode {
	
	/**
	 * 系统异常
	 */
	public static long SYSTEM_ERROR = 100001L;
	/**
	 * 缺少参数
	 */
	public static long MISS_REQUEST_PARAMS = 100002L;
	
	/**
	 * 参数值不正确
	 */
	public static long PARAM_VALUE_ERROR = 100078L;
	
	/**
	 * 不能有两个同名参数
	 */
	public static long TWO_PARAMS_WITH_SAME_NAME = 300001L;
	
	/**
	 * 加密错误
	 */
	public static long ENCRYPT_ERROR = 300005L;
	/**
	 * 解密错误
	 */
	public static long DECRYPT_ERROR = 300006L;
	
	
}
