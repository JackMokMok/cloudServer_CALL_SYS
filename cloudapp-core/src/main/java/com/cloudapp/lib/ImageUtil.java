package com.cloudapp.lib;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
public class ImageUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	/**
     * 把图片印刷到图片上
     * 
     * @param markFilePath --
     *            水印文件地址
     * @param targetFile --
     *            目标文件
     */
	public final static void pressImage(String markFilePath, File targetFile) throws Exception{
		Image targetImg = null;
		Image markImg = null;
		try {
            //目标文件
            targetImg = ImageIO.read(targetFile);
            //水印文件
            File markFile = new File(markFilePath);
            markImg = ImageIO.read(markFile);
		}catch(IOException ioex){
        	logger.error("读取图片文件失败，" + ioex.getMessage());
        	throw new Exception("读取图片文件失败");
        }
        
        int width = targetImg.getWidth(null);
        int height = targetImg.getHeight(null);
        int width_biao = markImg.getWidth(null);
        int height_biao = markImg.getHeight(null);
            //如果文件大小比水印小，则取消
        if(width<width_biao)
            return;
            
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.drawImage(targetImg.getScaledInstance(width, height,
	    	    BufferedImage.SCALE_SMOOTH), 0, 0, width, height, null);
        g.drawImage(markImg.getScaledInstance(width_biao, height_biao,
	    	    BufferedImage.SCALE_SMOOTH), width - width_biao,height - height_biao , width_biao, height_biao, null);
        //水印文件结束
        g.dispose();
        try{
            ImageIO.write(image, "JPEG", targetFile);
        } catch (IOException e) {
        	logger.error("写入水印图片文件失败, " + e.getMessage());
        	throw new Exception("写入水印图片文件失败");
        }
    }
	
	/**
	 * 图片缩放
	 * @param url
	 */
	public final static void zoomImage(File url,int width,int height) throws Exception{
		BufferedImage src = null;
		try {
			src = ImageIO.read(url);
		} catch (IOException e) {
			logger.error("读取缩放图片文件失败, " + e.getMessage());
			throw new Exception("读取缩放图片文件失败");
		}
		int oldWidth = src.getWidth(null);
		int oldHeight = src.getHeight(null);
		if(width>oldWidth)
			return;
		int x = 1000;//精度
		int m = (width*x)/oldWidth;
			
		BufferedImage tag = new BufferedImage(width, (oldHeight*m)/x, BufferedImage.TYPE_INT_RGB);

		Graphics g =tag.getGraphics();
		g.drawImage(src.getScaledInstance(width, (oldHeight*m)/x,
	    	        BufferedImage.SCALE_SMOOTH), 0, 0, width, (oldHeight*m)/x, null); //绘制缩小后的图 
		g.dispose();
		try{
			ImageIO.write(tag, "JPEG", url);
		} catch (IOException e) {
			logger.error("写入缩放后图片文件失败, " + e.getMessage());
			throw new Exception("写入缩放后图片文件失败");
		}
	}
	
	/**
	 * 同时调整高和宽
	 * 算法：比较需要调整的高和宽比率，比率越小，说明需要先调整该条边，另外一边以白色背景补足
	 */
	public final static void zoomImagePro(File url,int width,int height) throws Exception{
		//任何一个值为0，则取消调整
		if(height == 0||width == 0)
			return;
		BufferedImage src = null;
		try {
			src = ImageIO.read(url);
		} catch (IOException e) {
			logger.error("读取缩放图片文件失败, " + e.getMessage());
			throw new Exception("读取缩放图片文件失败");
		}
		int oldWidth = src.getWidth(null);
		int oldHeight = src.getHeight(null);
		//精度
		int x = 1000;
		//宽度缩放比率
		int widthRate = (width*x)/oldWidth;
		//高度缩放比率
		int heightRate = (height*x)/oldHeight;
		
		//最终需要调整成的宽和高
		int finalWidth = 0;
		int finalHeight = 0;
		
		//若宽度缩放更明显，则先缩放宽度
		if(widthRate<=heightRate){
			finalWidth = width;
			finalHeight = (oldHeight*widthRate)/x;
		}else{
			finalWidth = (oldWidth*heightRate)/x;
			finalHeight = height;
		}
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g =tag.getGraphics();
		//生成画布
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		g.drawImage(src.getScaledInstance(finalWidth, finalHeight,
	    	        BufferedImage.SCALE_SMOOTH), (width-finalWidth)/2, (height-finalHeight)/2, finalWidth, finalHeight, null); //绘制缩小后的图 
		g.dispose();
		try{
			ImageIO.write(tag, "JPEG", url);
		} catch (IOException e) {
			logger.error("写入缩放后图片文件失败, " + e.getMessage());
			throw new Exception("写入缩放后图片文件失败");
		}
	}
	
	/**
	 * 调整成正方形的图片
	 */
	public final static void reSizeImage(File image) throws Exception{
		Image src = null;
		//读取图片的长和宽
		try {
			src = ImageIO.read(image);
		} catch (IOException e) {
			logger.error("读取缩放图片文件失败, " + e.getMessage());
			throw new Exception("读取缩放图片文件失败");
		}
		
		int height=src.getHeight(null);
		int width=src.getWidth(null);
		int sideWidth=0;
		if(height<width)
			sideWidth=width;
		else if(height>width)
			sideWidth=height;
		else
			return;
		BufferedImage tag = new BufferedImage(sideWidth, sideWidth, BufferedImage.TYPE_INT_RGB); 
		Graphics g =tag.getGraphics();
		//生成画布
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, sideWidth, sideWidth);
		g.drawImage(src, (sideWidth-width)/2,(sideWidth-height)/2, width, height, null);
		g.dispose();
		try{
	        ImageIO.write(tag, "JPEG", image);
		} catch (IOException ioex) {
			logger.error("写入缩放后图片文件失败, " + ioex.getMessage());
			throw new Exception("写入缩放后图片文件失败");
		}
	}

	/**
	 * 图片缩放
	 * @param url
	 */
	public final static void zoomImage(File url,double percent) throws Exception{
		BufferedImage src = null;
		try {
			src = ImageIO.read(url);
		} catch (IOException e) {
			logger.error("读取缩放图片文件失败, " + e.getMessage());
			throw new Exception("读取缩放图片文件失败");
		}
		int oldWidth = src.getWidth(null);
		int oldHeight = src.getHeight(null);
		int width = Integer.valueOf((Math.round(oldWidth*percent))+"");
		int x = 1000;//精度
		int m = (width*x)/oldWidth;
			
		BufferedImage tag = new BufferedImage(width, (oldHeight*m)/x, BufferedImage.TYPE_INT_RGB);

		Graphics g =tag.getGraphics();
		g.drawImage(src.getScaledInstance(width, (oldHeight*m)/x,
	    	        BufferedImage.SCALE_SMOOTH), 0, 0, width, (oldHeight*m)/x, null); //绘制缩小后的图 
		g.dispose();
		try{
			ImageIO.write(tag, "JPEG", url);
		} catch (IOException e) {
			logger.error("写入缩放后图片文件失败, " + e.getMessage());
			throw new Exception("写入缩放后图片文件失败");
		}
	}
	
	/**
	 * 生成JPG图片
	 * @param url
	 * @param newName
	 */
	public static void createJpg(String url,String newName) throws Exception{
		File image=new File(url);
		String suffix=parseExt(image.getAbsolutePath());
		if(!"jpg".equalsIgnoreCase(suffix.trim())){
			Image src = null;
			try {
				src = ImageIO.read(image);
			} catch (IOException e) {
				logger.error("读取缩放图片文件失败, " + e.getMessage());
				throw new Exception("读取缩放图片文件失败");
			}
			int height=src.getHeight(null);
			int width=src.getWidth(null);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			Graphics g = tag.getGraphics();
			g.drawImage(src, 0,0, width, height, null);
			g.dispose();
			FileOutputStream fos = new FileOutputStream(image);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			try{	
				encoder.encode(tag);
				bos.close();
			} catch(ImageFormatException ifex) {
				logger.error("生成JPG图片文件失败, " + ifex.getMessage());
				throw new Exception("生成JPG图片文件失败");
			} catch(IOException ioe) {
				logger.error("生成JPG图片文件失败, " + ioe.getMessage());
				throw new Exception("生成JPG图片文件失败");
			}
			if(!image.renameTo(new File(newName))){
				throw new Exception("已存在该图片名称");
			}
		}
	}
	
	/**
	 * 裁剪图片
	 * @param fileName -- 待裁剪图片
	 * @param width -- 定位
	 * @param height -- 定位
	 * @param top -- 定位
	 * @param left -- 定位
	 */
	public static void cropImage(File image,int width,int height,int top,int left) throws Exception{
		BufferedImage src = null;
		try {
			src = ImageIO.read(image);
		} catch (IOException e) {
			logger.error("读取裁剪图片文件失败, " + e.getMessage());
			throw new Exception("读取裁剪图片文件失败");
		}
		height = Math.min(height, src.getHeight());
		width = Math.min(width, src.getWidth());
		if (height <= 0)
			height = src.getHeight();
		if (width <= 0)
			width = src.getWidth();
		top = Math.min(Math.max(0, top), src.getHeight() - height);
		left = Math.min(Math.max(0, left), src.getWidth() - width);
		BufferedImage bi_cropper = src.getSubimage(left, top, width, height);
		try{
			ImageIO.write(bi_cropper, "JPEG", image);
		} catch (IOException e) {
			logger.error("写入裁剪后图片文件失败, " + e.getMessage());
			throw new Exception("写入裁剪后图片文件失败");
		}
	}
	
	/**
	 * 给一个文件生成一个新的文件名
	 */
	public static String getNewFileName(String oldFileName){
		if(!parseExt(oldFileName).equals("")) {
			return UUID.randomUUID() + "." + parseExt(oldFileName);
		} else {
			return UUID.randomUUID() + "";
		}
	}
	
	/**
	 * 图片后缀
	 */
	public static String parseExt(String s) {
		if (s == null || s.indexOf(".") == -1)
			return "";
		return s.substring(s.lastIndexOf(".") + 1).trim().toLowerCase();
	}
}
