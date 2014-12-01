package com.keitsen.demo.basic.util;

import java.util.UUID;


public class StringUtil {


	/**
	 * 生成32位的UUID字符串
	 * 
	 * @return
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
