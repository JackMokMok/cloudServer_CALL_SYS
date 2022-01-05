package com.cloudapp.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;

import freemarker.template.Template;

public class FileUtil {
	private static BufferedReader br;

	private static String DEFAULT_CHARSET = "UTF-8";

	/**
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(String file) throws Exception {
		return read(file, DEFAULT_CHARSET);
	}

	/**
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(File file) throws Exception {
		return read(file, DEFAULT_CHARSET);
	}

	/**
	 * @param file
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static String read(String file, String charsetName) throws Exception {
		return read(new File(file), charsetName);
	}

	/**
	 * 文件内容读取
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String read(File file, String charsetName) throws Exception {
		StringBuffer sb = new StringBuffer();
		br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), charsetName));
		int byteRead = 0;
		char[] buffer = new char[8192];
		while ((byteRead = br.read(buffer, 0, 8192)) != -1) {
			sb.append(buffer, 0, byteRead);
		}
		return sb.toString();
	}

	/**
	 * @param origin
	 * @param target
	 * @throws Exception
	 */
	public static void copy(File origin, String target) throws Exception {
		copy(origin, new File(target));
	}

	/**
	 * @param origin
	 * @param target
	 * @throws Exception
	 */
	public static void copy(File origin, File target) throws Exception {
		if (origin.isDirectory())
			copyFolder(origin, target);
		else
			copyFile(origin, target);
	}

	private static void copyFolder(File origin, File target) throws Exception {
		if (!target.exists())
			target.mkdirs();
		File[] fs = origin.listFiles();
		for (File f : fs)
			copy(f, new File(target.getPath() + File.separator + f.getName()));
	}

	private static void copyFile(File origin, File target) throws Exception {
		confirmParent(target);

		InputStream is = new FileInputStream(origin);
		OutputStream os = new FileOutputStream(target);
		try {
			int byteRead = 0;
			byte[] buffer = new byte[8192];
			while ((byteRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, byteRead);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	/**
	 * @param file
	 */
	public static void delete(String file) {
		delete(new File(file));
	}

	/**
	 * @param file
	 */
	public static void delete(File file) {
		if (file.exists() && file.isFile()) {
			file.delete();
		} else {
			throw new IllegalArgumentException("删除文件失败");   
		}
	}

	public static void deleteFolder(File file) {
		if(file.exists() && file.isDirectory()) {
			File[] fs = file.listFiles();
			for (File f : fs) {
				if(f.isFile()) {
					delete(f);
				} else if(f.isDirectory()) {
					deleteFolder(f);
				}
			}
			file.delete();
		} else {
			throw new IllegalArgumentException("删除文件夹失败");   
		}
	}

	/**
	 * @param origin
	 * @param target
	 * @throws Exception
	 */
	public static void move(File origin, String target) throws Exception {
		move(origin, new File(target));
	}

	/**
	 * @param origin
	 * @param target
	 * @throws Exception
	 */
	public static void move(File origin, File target) throws Exception {
		copy(origin, target);
		delete(origin);
	}

	/**
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public static void create(String file, String content) throws IOException {
		create(new File(file), content);
	}

	/**
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public static void create(File file, String content) throws IOException {
		FileWriter writer = new FileWriter(file);
		StringReader sr = new StringReader(content);
		try {
			int byteRead = 0;
			char[] buffer = new char[8192];
			while ((byteRead = sr.read(buffer, 0, 8192)) != -1) {
				writer.write(buffer, 0, byteRead);
			}
		} finally {
			writer.close();
		}
	}

	/**
	 * @param content
	 * @param file
	 * @throws Exception
	 */
	public static void write(String content, String file) throws Exception {
		write(content, file, DEFAULT_CHARSET);
	}

	/**
	 * @param content
	 * @param file
	 * @param charsetName
	 * @throws Exception
	 */
	public static void write(String content, String file, String charsetName)
			throws Exception {
		write(content, new File(file), charsetName);
	}

	/**
	 * @param content
	 * @param file
	 * @throws Exception
	 */
	public static void write(String content, File file) throws Exception {
		write(content, file, DEFAULT_CHARSET);
	}

	/**
	 * @param content
	 *            内容
	 * @param file
	 *            被写入文件
	 * @param charsetName
	 *            字符集
	 * @throws Exception
	 */
	public static void write(String content, File file, String charsetName)
			throws Exception {
		confirmParent(file);
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), charsetName));

		StringReader sr = new StringReader(content);

		try {
			int byteRead = 0;
			char[] buffer = new char[8192];
			while ((byteRead = sr.read(buffer, 0, 8192)) != -1) {
				writer.write(buffer, 0, byteRead);
			}
		} finally {
			writer.close();
		}
	}

	/**
	 * @param t
	 * @param rootMap
	 * @param file
	 * @throws Exception
	 */
	public static void write(Template t, Object rootMap, String file)
			throws Exception {
		write(t, rootMap, file, DEFAULT_CHARSET);
	}

	/**
	 * @param t
	 * @param rootMap
	 * @param file
	 * @param charsetName
	 * @throws Exception
	 */
	public static void write(Template t, Object rootMap, String file,
			String charsetName) throws Exception {
		write(t, rootMap, new File(file), charsetName);
	}

	/**
	 * @param t
	 * @param rootMap
	 * @param file
	 * @throws Exception
	 */
	public static void write(Template t, Object rootMap, File file)
			throws Exception {
		write(t, rootMap, file, DEFAULT_CHARSET);
	}

	/**
	 * @param t
	 * @param rootMap
	 * @param file
	 * @param charsetName
	 * @throws Exception
	 */
	public static void write(Template t, Object rootMap, File file,
			String charsetName) throws Exception {
		confirmParent(file);
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), charsetName));
		t.process(rootMap, writer);
		writer.close();
	}

	public static void confirmParent(File file) {
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
	}

	/**
	 * 通过文件路径判断文件是否存在
	 * 
	 * @return true:存在， false:不存在
	 */
	public static boolean fileIsExists(String path) {
		File file = new File(path);
		if (file.exists())
			return true;
		return false;
	}

	private static final String[] imgs = new String[] { ".png", ".gif", ".jpg",
			".bmp" };

	private static final String[] videos = new String[] { ".avi", ".mkv",
			".rmvb", ".mp4", ".rm", ".mov" };

	private static final String[] musics = new String[] { ".mp3", "wav" };

	/**
	 * 通过后缀判读是否是图片格式
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean checkImgSuffix(String suffix) {
		boolean flag = false;
		for (String img : imgs) {
			if (img.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return flag;
	}

	/**
	 * 通过后缀判断是否是视频格式
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean checkVideoSuffix(String suffix) {
		boolean flag = false;
		for (String video : videos) {
			if (video.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return flag;
	}

	/**
	 * 通过后缀判断是否是音频格式
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean checkMusicSuffix(String suffix) {
		boolean flag = false;
		for (String music : musics) {
			if (music.equalsIgnoreCase(suffix)) {
				return true;
			}
		}
		return flag;
	}

	/**
	 * 获取后缀
	 * 
	 * @param filename
	 * @return
	 */
	public static String getSuffix(String filename) {
		String suffix = filename.substring(filename.lastIndexOf("."),
				filename.length());
		return suffix;
	}

}
