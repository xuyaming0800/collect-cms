package com.autonavi.cc.core.util;

import autonavi.online.framework.constant.Miscellaneous;
import autonavi.online.framework.sharding.uniqueid.UniqueIDHolder;

public class CommonUtil {
	
	/**
	 * 生成组件版本号 雪花
	 * @return 
	 */
	public static Long getUniqueId() throws Exception {
		return UniqueIDHolder.getIdWorker().nextId(Miscellaneous.getMyid());
	}

}
