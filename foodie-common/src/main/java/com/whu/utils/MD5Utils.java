package com.whu.utils;

import org.apache.commons.codec.binary.Base64;
import java.security.MessageDigest;

/**
 * @author Created By MrXi on 2019/12/21
 */
public class MD5Utils {

	/**
	 * 对字符串进行md5加密
	 * @param strValue 要加密的字符串
	 */
	public static String getMD5Str(String strValue) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
	}

	public static void main(String[] args) {
		try {
			String md5 = getMD5Str("123456");
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
