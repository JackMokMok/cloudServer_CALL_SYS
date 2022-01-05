package com.cloudapp.lib;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class StringUtil {
	private static DecimalFormat decimalFormat = new DecimalFormat();
	
	private static String DEFAULT_CHARSET = "UTF-8";
	
	public static final String COMMA = ",";

	public static final String SEMICOLON = "/";

	private static final String emailPattern = "^[\\w\\.\\_\\-]+@[\\w\\.\\_\\-]+(\\.[\\w\\-]{2,3}){1,2}$";

	private static final String mobilePattern = "^((13[0-9])|(147)|(15[^4,\\D])|(18[0,2-9]))\\d{8}$";
	
	private static final String string = "0123456789abcdefghijklmnopqrstuvwxyz";   

	/**
	 * 帐号最大允许长度
	 */
	private static final int maxUsernameLength = 40;

	/**
	 * 帐号最小允许长度
	 */
	private static final int minUsernameLength = 4;

	/**
	 * 帐号密码最小允许长度
	 */
	private static final int minPasswordLength = 6;

	public static String md5Enc(String s) {
		return DigestUtils.md5Hex(s);
	}

	public static boolean isValid(String s) {
		return (s != null && s.trim().length() > 0) ? true : false;
	}

	public static boolean isEmail(String s) {
		return isValid(s) && s.matches(emailPattern);
	}

	public static boolean isUsername(String s) {
		return (s != null && (s.trim().length() <= maxUsernameLength && s
				.trim().length() >= minUsernameLength)) ? true : false;
	}

	public static boolean isPassword(String s) {
		return (s != null && s.trim().length() >= minPasswordLength) ? true
				: false;
	}

	public static boolean isMobileNO(String mobiles) {
		return isValid(mobiles) && mobiles.matches(mobilePattern);
	}

	/**
	 * @param l
	 * @return
	 */
	@SuppressWarnings(value = "rawtypes")
	public static String listToCommaString(List l) {
		return collectionToCommaString(l);
	}

	/**
	 * 将集合中的值拼接为逗号字符串
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings(value = "rawtypes")
	public static String collectionToCommaString(Collection c) {
		if (c == null || c.size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (Object o : c)
			sb.append(COMMA).append(o.toString());
		return sb.substring(1);
	}

	public static String[] commaStringToArray(String s) {
		return s.split(COMMA);
	}

	public static List<String> commaStringToList(String s) {
		return Arrays.asList(commaStringToArray(s));
	}

	public static String semicolonString(Object... os) {
		StringBuilder sb = new StringBuilder();
		for (Object o : os)
			sb.append(SEMICOLON).append(o.toString());
		return sb.substring(1);
	}

	/**
	 * 以参数格式拼接StringBuffer
	 * 
	 * @param sb
	 * @param name
	 * @param value
	 */
	public static void appendParameter(StringBuffer sb, String name,
			String value) {
		if (sb.length() != 0)
			sb.append("&");
		sb.append(name + "=" + value);
	}

	/**
	 * 浮点数格式化输出
	 * 
	 * @param value
	 * @return
	 */
	public static String formatNumber(float value) {
		decimalFormat.applyPattern("#.00");
		if (value == 0.0)
			return "0";
		else
			return decimalFormat.format(value);
	}

	@SuppressWarnings("deprecation")
	public static String encPassword(String password) {
		return DigestUtils.md5Hex(DigestUtils.sha(password));
	}

	public static String encodeUrl(String s, String encoding) {
		if (!StringUtil.isValid(encoding))
			encoding = DEFAULT_CHARSET;
		String result = s;
		try {
			result = URLEncoder.encode(s, encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return result;
	}

	public static String decodeURL(String str, String encoding) {
		if (!StringUtil.isValid(str))
			return null;
		if (!StringUtil.isValid(encoding))
			encoding = DEFAULT_CHARSET;
		final char splitter = '/';
		try {
			StringBuilder sb = new StringBuilder(str.length());
			int start = 0;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == splitter) {
					sb.append(URLDecoder.decode(str.substring(start, i),
							encoding));
					sb.append(splitter);
					start = i + 1;
				}
			}
			if (start < str.length())
				sb.append(URLDecoder.decode(str.substring(start), encoding));
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getNewImgName(String name) {
		UUID uuid = UUID.randomUUID();
		if (isValid(name)) {
			String[] str = name.split("\\.");
			if (str.length > 1) {
				return uuid.toString() + "." + str[1];
			}
		}
		return null;
	}

	public static String replaceSpecialChars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}

	public static boolean isNum(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static int getRandom(int count) {
		return (int) Math.round(Math.random() * (count));
	}
	 
	public static String getRandomString(int length){
	    StringBuffer sb = new StringBuffer();
	    int len = string.length();
	    for (int i = 0; i < length; i++) {
	        sb.append(string.charAt(getRandom(len-1)));
	    }
	    return sb.toString();
	}
}
