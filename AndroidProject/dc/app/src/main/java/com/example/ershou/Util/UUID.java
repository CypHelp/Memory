package com.example.ershou.Util;

/**
 * 获取uuid
 * @author 张兴宝 
 *
 */
public class UUID {
	public static String getUuid() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "")
				.toUpperCase();
	}

}
