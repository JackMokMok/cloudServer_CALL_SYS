package com.cloudapp.lib;

import java.util.Random;

public class RandomUtil {
	private static final Random r = new Random();
	
	private static final char[] RANGE = "0123456789".toCharArray();

	/**
	 * @return
	 */
	public static int randomInt() {
		return r.nextInt();
	}

	/**
	 * @param base
	 * @return 0（包含）至base（不包含）之间
	 */
	public static int randomInt(int base) {
		return r.nextInt(base);
	}

	/**
	 * @return
	 */
	public static double randomDouble() {
		return r.nextDouble();
	}

	/**
	 * @param div
	 * @return
	 */
	public static double randomDivDouble(int div) {
		return r.nextDouble() / div;
	}

	/**
	 * @param mul
	 * @return
	 */
	public static double randomMulDouble(int mul) {
		return r.nextDouble() * mul;
	}

	/**
	 * @return
	 */
	public static boolean randomBool() {
		return r.nextBoolean();
	}
	
	/**
	 * 生成length为长度数字字符串
	 * @param length
	 * @return
	 */
	public static String randomNumberString(int length) {
	    int cr = RANGE.length;
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; ++i)
			sb.append(RANGE[RandomUtil.randomInt(cr)]);
		return sb.toString();
	}
	
}
