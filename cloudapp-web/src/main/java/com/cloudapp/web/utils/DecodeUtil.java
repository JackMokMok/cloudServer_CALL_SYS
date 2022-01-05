package com.cloudapp.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.cloudapp.web.utils.decode.AES;
import com.cloudapp.web.utils.decode.WxPKCS7Encoder;
import org.apache.commons.codec.binary.Base64;

public class DecodeUtil {

    public static String decodePhone(String encryptedData,String sessionKey,String iv){
        String phoneNumber = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                String result = new String(WxPKCS7Encoder.decode(resultByte));
                JSONObject jsonObject = JSONObject.parseObject(result);
                phoneNumber = jsonObject.getString("phoneNumber");
            }
        } catch (Exception e) {
            phoneNumber = "";
            e.printStackTrace();
        }
        return phoneNumber;
    }
}
