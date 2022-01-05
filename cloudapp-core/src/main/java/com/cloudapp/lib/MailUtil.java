package com.cloudapp.lib;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@SuppressWarnings("resource")
public class MailUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(MailUtil.class);

	private static MimeMessageHelper mimeMessageHelper;

	private static JavaMailSender javaMailSender;

	static {
		javaMailSender = (JavaMailSender) (new FileSystemXmlApplicationContext(
				"classpath:META-INF/mailSender.xml")).getBean("mailSender");
		MimeMessage mm = javaMailSender.createMimeMessage();
		try {
			mimeMessageHelper = new MimeMessageHelper(mm, true, "UTF-8");
		} catch (MessagingException e) {
            logger.error("邮件服务初始化错误, " + e.getMessage());
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param mailto
	 *            对方邮件地址
	 * @param title
	 *            邮件标题
	 * @param content
	 *            邮件内容
	 * @param user
	 *            发送邮箱用户名
	 * @param username
	 *            发送邮箱名称
	 * @throws Exception
	 */
	public static void sendMail(String mailto, String title, String content,
			String user, String username) throws Exception {
		setMailContent(title, content, user, username);
		send(mailto);
	}

	/**
	 * 群发邮件
	 * 
	 * @param mails
	 *            对方邮件地址列表
	 * @param title
	 *            邮件标题
	 * @param content
	 *            邮件内容
	 * @param user
	 *            发送邮箱用户名
	 * @param username
	 *            发送邮箱名称
	 * @throws Exception
	 */
	public static void sendMail(List<String> mails, String title,
			String content, String user, String username) throws Exception {
		setMailContent(title, content, user, username);
		for (String mailto : mails)
			send(mailto);
	}

	private static void setMailContent(String title, String content,
			String user, String username) throws Exception {
		mimeMessageHelper.setFrom(user, username);
		mimeMessageHelper.setSubject(title);
		mimeMessageHelper.setText(content, true);
	}

	private static void send(String mailto) throws Exception {
		mimeMessageHelper.setTo(mailto);
		javaMailSender.send(mimeMessageHelper.getMimeMessage());
	}
}
