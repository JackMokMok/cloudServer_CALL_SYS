package com.cloudapp.lib;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenUtil {

	private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

	/**
	 * AES密钥
	 */
	public static final String ASE_KEY = "ASE/IDEAS/SECRETKEY";

	/**
	 * 根据token获取用户id
	 * 
	 * @param token
	 * @return
	 */
	public static int getUserIdByToken(String token) {
		int id = 0;
		try {
			if (token != null && !"".equals(token)) {
				String decToken = aesDecrypt(token, getAesKey(ASE_KEY));
				id = Integer.valueOf(decToken);
			}
		} catch (GeneralSecurityException e) {
            logger.error("Token获取用户id异常（生成秘钥失败）, " + e.getMessage());
		} catch (NumberFormatException e) {
            logger.error("Token获取用户id异常（转换数值失败）, " + e.getMessage());
		}
		return id;
	}

	/**
	 * 获取token
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public static String getToken(Integer id) {
		String token = "";
		try {
			if (id != null) {
				token = aesEncrypt(id + "", getAesKey(ASE_KEY));
			}
		} catch (GeneralSecurityException e) {
            logger.error("生成Token异常, " + e.getMessage());
		}
		return token;
	}

	/**
	 * 获取AES密钥
	 * 
	 * @param aseKey
	 * @return
	 */
	public static byte[] getAesKey(String aseKey)
			throws GeneralSecurityException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(aseKey.getBytes());
		keyGenerator.init(128, secureRandom);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param keyBytes
	 *            加密密码
	 * @return
	 */
	public static String aesEncrypt(String content, byte[] keyBytes)
			throws GeneralSecurityException {
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(content.getBytes());
		return parseByte2HexStr(result);
	}

	/**
	 * AES解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param keyBytes
	 *            解密密钥
	 * @return
	 */
	public static String aesDecrypt(String content, byte[] keyBytes)
			throws GeneralSecurityException {
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(parseHexStr2Byte(content));
		return new String(result);
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) {
		String token = getToken(123);
		System.out.println(token);
		System.out.println(getUserIdByToken(token));
	}
}
