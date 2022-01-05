package com.cloudapp.lib;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckcodeUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(CheckcodeUtil.class);

	private static final int CODE_WIDTH = 136;
	private static final int CODE_HEIGHT = 48;
	private static final int CODE_LENGTH = 4;
	private static Font CHECK_CODE_FONT = new Font("Times New Roman",
			Font.PLAIN, 40);

	private static final char[] CODE_RANGE = "0123456789".toCharArray();

	/**
	 * 验证验证码
	 * 
	 * @param session
	 * @param input
	 *            用户输入的验证码
	 * @param codeSessionKey
	 *            验证码session键
	 * @return
	 */
	public static boolean check(HttpSession session, String input,
			String codeSessionKey) {
		if (!StringUtil.isValid(input))
			return false;

		return input.equals(session.getAttribute(codeSessionKey));
	}

	/**
	 * 获取验证码
	 * 
	 * @param session
	 * @param codeSessionKey
	 *            验证码session键
	 * @return
	 */
	public static InputStream gen(HttpSession session, String codeSessionKey) {
		String code = genCode();

		session.setAttribute(codeSessionKey, code);

		return genConfusedImg(code);
	}

	private static String genCode() {
		StringBuffer sb = new StringBuffer();
		int cr = CODE_RANGE.length;

		for (int i = 0; i < CODE_LENGTH; ++i)
			sb.append(CODE_RANGE[RandomUtil.randomInt(cr)]);

		return sb.toString();
	}

	private static InputStream genConfusedImg(String code) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ImageIO.write(
					genConfusedImg(CODE_WIDTH, CODE_HEIGHT, Color.WHITE,
							Color.RED, CHECK_CODE_FONT, 22, 25, 35, code, 5, 20),
					"jpg", output);
		} catch (IOException e) {
			logger.error("生成验证码失败，" + e.getMessage());
		}
		return new ByteArrayInputStream(output.toByteArray());
	}

	/**
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param bgColor
	 *            背景色
	 * @param fontColor
	 *            字符颜色（干扰线颜色与之相同）
	 * @param font
	 *            字符
	 * @param fontXscope
	 *            字符宽度
	 * @param fontXbase
	 *            字符x轴基准点
	 * @param fontYbase
	 *            字符y轴基准点
	 * @param content
	 *            内容字符串
	 * @param interferonScope
	 *            干扰线范围
	 * @param interferonNum
	 *            干扰线数量
	 * @return
	 */
	private static BufferedImage genConfusedImg(int width, int height,
			Color bgColor, Color fontColor, Font font, int fontXscope,
			int fontXbase, int fontYbase, String content, int interferonScope,
			int interferonNum) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		// 填充背景色
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);

		// 设置字符属性及其颜色
		g.setColor(fontColor);
		g.setFont(font);

		// 填充字符串
		int cl = content.length();
		for (int i = 0; i < cl; ++i) {
			AffineTransform at = new AffineTransform();

			// 字体缩放变化
			at.concatenate(AffineTransform.getScaleInstance(randomScale(),
					randomScale()));

			// 字体仿射变化
			at.concatenate(AffineTransform.getShearInstance(randomShear(),
					randomShear()));

			// 字体旋转变化
			at.concatenate(AffineTransform.getRotateInstance(randomRotate()));

			g.setFont(font.deriveFont(at));
			g.drawString(content.substring(i, i + 1), fontXbase + fontXscope
					* i, fontYbase);
		}

		// 填充干扰线
		for (int i = 0; i < interferonNum; i++) {
			int x = RandomUtil.randomInt(width);
			int y = RandomUtil.randomInt(height);
			g.drawLine(x, y, x + RandomUtil.randomInt(interferonScope), y
					+ RandomUtil.randomInt(interferonScope));
		}

		g.dispose();

		return image;
	}

	private static double randomScale() {
		return RandomUtil.randomBool() ? 1 + RandomUtil.randomDivDouble(6)
				: 1 - RandomUtil.randomDivDouble(6);
	}

	private static double randomShear() {
		return RandomUtil.randomDivDouble(3);
	}

	private static double randomRotate() {
		return RandomUtil.randomBool() ? RandomUtil.randomDivDouble(3)
				: -RandomUtil.randomDivDouble(3);
	}
}
