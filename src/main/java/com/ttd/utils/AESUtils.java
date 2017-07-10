package com.ttd.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class AESUtils {
	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";// 默认的加密算法
	private static final String DEFAULT_KEY = "ZH$ttdMsyxlAfdwL";

	/**
	 * @param iv
	 *            向量，增加加密算法强度
	 * @param data
	 *            明文
	 * @return {@link String}
	 * @throws Exception
	 */
	public static String encrpt(String iv, String data) throws Exception {
		// 获取密钥
		SecretKeySpec secreKey = new SecretKeySpec(DEFAULT_KEY.getBytes(), KEY_ALGORITHM);
		IvParameterSpec ivP = new IvParameterSpec(iv.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度

		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// "算法/模式/补码方式"0102030405060708
		cipher.init(Cipher.ENCRYPT_MODE, secreKey, ivP);
		byte[] encrypted = cipher.doFinal(data.getBytes());

		return Base64.encodeBase64String(encrypted);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            密文
	 * @param iv
	 *            向量，增加加密算法强度
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String iv) throws Exception {
		byte[] body = Base64.decodeBase64(data.getBytes());
		SecretKeySpec secreKey = new SecretKeySpec(DEFAULT_KEY.getBytes(), KEY_ALGORITHM);
		IvParameterSpec ivP = new IvParameterSpec(iv.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度

		// 实例化
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secreKey, ivP);
		// 执行操作
		byte[] encryBuf = cipher.doFinal(body);

		return new String(encryBuf);
	}

	/*public static void main(String[] args) {
		try {
			String iv = DigestUtils.sha256Hex("Totodi!@#").substring(0, 15);
			String body = "mac=bnfdvfssjf&os=ios&version=1.0.2&houseId=130&userId=1&foremanId=4&totalAmount=20000";
			// 加密
			String secReslt = AESUtils.encrpt(iv, body);
			System.out.println("加密后：" + secReslt);

			// 解密
			String ss = AESUtils.decrypt(secReslt, iv);
			System.out.println("解密后" + ss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
}
