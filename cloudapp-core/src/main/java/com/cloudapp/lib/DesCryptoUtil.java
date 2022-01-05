package com.cloudapp.lib;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesCryptoUtil {
    public static byte[] desCrypto(byte[] datasource, String password) {            
        try{
	        SecureRandom random = new SecureRandom();
	        DESKeySpec desKey = new DESKeySpec(password.getBytes());
	        //创建一个密匙工厂，然后用它把DESKeySpec转换成
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey securekey = keyFactory.generateSecret(desKey);
	        //Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
	        //用密匙初始化Cipher对象
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
	        //现在，获取数据并加密
	        //正式执行加密操作
	        return cipher.doFinal(datasource);
        }catch(Throwable e){
        }
        return null;
    }
    
    public static byte[] desDeCrypto(byte[] datasource, String password) {            
        try{
	        SecureRandom random = new SecureRandom();
	        DESKeySpec desKey = new DESKeySpec(password.getBytes());
	        //创建一个密匙工厂，然后用它把DESKeySpec转换成
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey securekey = keyFactory.generateSecret(desKey);
	        //Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
	        //用密匙初始化Cipher对象
	        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
	        //现在，获取数据并加密
	        //正式执行加密操作
	        return cipher.doFinal(datasource);
        }catch(Throwable e){
        }
        return null;
    }
    
    private static String hexString = "0123456789ABCDEF";
	public static String encode(byte[] bytes) {
		// 根据默认编码获取字节数组
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}
  
}
