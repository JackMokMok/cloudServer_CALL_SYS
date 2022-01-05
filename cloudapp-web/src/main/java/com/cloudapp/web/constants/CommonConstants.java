package com.cloudapp.web.constants;

public class CommonConstants {
	/**
	 * 系统版本号
	 */
	public static final String SYS_VERSION = "1.0.1";
	
	// session key
	/**
	 * 管理员登录信息
	 */
	public static final String ADMIN_SIGNON_SESSION_KEY = "CLOUD_SERVER_RESOURCE";
	
	// http session
	/**
	 * 验证码session键
	 */
	public static final String CHECK_CODE_SESSION_KEY   = "CLOUD_SERVER_RESOURCE_CHECKCODE";

	// 路径参数
	/**
	 * 应用真实路径
	 */
	public static String REAL_PATH             = null;

	/**
	 * META相对路径
	 */
	public static final String META_PATH       = "META-INF/";
	
	/**
	 * WEBRES相对路径
	 */
	public static final String WEBRES_PATH     = "/WEB-RES";

	/**
	 * 字符集
	 */
	public final static String DEFAULT_CHARSET = "UTF-8";

	// 通用符号及参数
	/**
	 * 英文逗号分隔符
	 */
	public static final String COMMA        = ",";

	/**
	 * 英文分号分隔符
	 */
	public static final String SEMICOLON    = "/";

	public static final String FOURZEROFOUR = "core/404";

	public static final String FORBIDDEN    = "core/forbidden";

	public static final String ERROR        = "core/error";

	public static final String SUCCESS      = "core/success";
	
	public static final String MYMESSAGE    = "core/message";

	/**
	 * 文件上传路径
	 */
	public static String RES_UPLOAD_PATH    = null;
	
	// 邮件服务地址
	public static String CLOUD_NITIFY      = new String("cloud.szpretv.com:3002");

	// 云端服务器（CloudLog）之master
	public static String CLOUD_MY_WORK     = new String("cloud.szpretv.com:8085");
	
	public static String TO_ADDRS          = "";
	
	// AUTHCODE超时时间（单位，毫秒）
	public static long AUTH_CODE_TIMEOUT   = 120 * 1000;   
	
	// 获取AUTHCODE的CHIPHER码
	public static String CHIPHER   = "p2Re0Mi0Er4";

	// MySQL服务器
	public static String MYSQL_SVR         = null;

	// 协议类型
	public static String TYPE              = null;

	// 默认设备类型
	public static String[] DEFAULT_CATEGORY = new String[]{"休息厅","包房"};

	// 默认设备类型
	public static String[] DEFAULT_GROUP = new String[]{"对接组","不对接组"};

	// api服务器地址
	public static String CLOUD_API_SVR     = null;

}
