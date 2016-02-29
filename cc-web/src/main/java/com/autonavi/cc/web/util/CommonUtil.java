package com.autonavi.cc.web.util;

public class CommonUtil {

	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年8月13日
	 * @description 校验参数是否为null
	 * @param values
	 * @param desc
	 * @return
	 */
	public static String checkNull(String[] values,String[] desc){
		if(values==null||desc==null){
			return "校验的值和要校验的数据不符";
		}else if(values.length!=desc.length){
			return "校验的值和要校验的数据不符";
		}else{
			for(int i=0;i<values.length;i++){
				if(values[i]==null||values[i].equals("")){
					return desc[i]+"不能为空";
				}
			}
		}
		return "";
	}
}
