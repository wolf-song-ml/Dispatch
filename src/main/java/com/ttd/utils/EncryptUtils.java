package com.ttd.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

/**
 * 加密算法
 * 
 * @author wolf
 */
public class EncryptUtils {

	public static String encodeURI(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			return str;
		}
	}

	public static String decodeURI(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * MD5加密算法(16位,ERP密码为16位)
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeMD516(String str) {
		String temp = encodeMD532(str, null);
		if (temp != null) {
			return temp.substring(8, 24);// 16位的加密
		}
		return temp;
	}

	/**
	 * MD5加密算法(32位)
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeMD532(String str, String charset) {
		MessageDigest md = null;
		String dstr = null;
		try {
			md = MessageDigest.getInstance("MD5");

			if (StringUtils.isNotBlank(charset)) {
				md.update(str.getBytes(charset));
			} else {
				md.update(str.getBytes());
			}

			dstr = byteArr2HexStr(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
		return dstr;// 32位加密
	}

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16).toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 对字符串加密,加密算法使用SHA-256
	 * 
	 * @param strSrc
	 *            要加密的字符串
	 * @param encName
	 *            加密类型
	 * @return
	 * @throws Exception
	 */
	public static String encodeSHA256(String strSrc) throws Exception {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			String encName = "SHA-256";
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = byteArr2HexStr(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	/**
	 * 生成随机32位MD5串
	 * 
	 * @return
	 */
	public static String MD5Random() {
		return EncryptUtils.encodeMD532(System.currentTimeMillis() + ""
				+ Math.random() * 1000, null);
	}

}
