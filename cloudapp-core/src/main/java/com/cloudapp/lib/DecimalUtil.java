package com.cloudapp.lib;

import java.math.BigDecimal;

public class DecimalUtil {
	public static BigDecimal getDecimal(String original) {
		return new BigDecimal(original);
	}

	public static BigDecimal getDecimal(int original) {
		return new BigDecimal(Integer.toString(original));
	}

	public static BigDecimal getDecimal(double original) {
		return new BigDecimal(Double.toString(original));
	}

	/**
	 * 以四舍五入为原则保留两位小数
	 * 
	 * @return
	 */
	public static BigDecimal round(BigDecimal original) {
		return original.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * @param d1
	 * @param d2
	 * @return -1：d1小于d2， 0：d1等于d2， 1：d1大于d2
	 */
	public static int compare(BigDecimal d1, BigDecimal d2) {
		return d1.compareTo(d2);
	}
}
