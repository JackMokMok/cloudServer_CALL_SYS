package com.cloudapp.lib;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileOutputStream;
import java.io.IOException;  
import java.util.Hashtable;  
import javax.imageio.ImageIO;  
import com.google.zxing.BarcodeFormat;  
import com.google.zxing.BinaryBitmap;  
import com.google.zxing.DecodeHintType;  
import com.google.zxing.LuminanceSource;  
import com.google.zxing.MultiFormatReader;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.ReaderException;  
import com.google.zxing.Result;  
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;  
import com.google.zxing.common.BitMatrix;  
import com.google.zxing.common.HybridBinarizer;  
  
public class QRCode {  
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	
    /** 
     * @param args 
     */  
    @SuppressWarnings("static-access")
	public static void main(String[] args) {  
    	 QRCode t=new QRCode();  
         t.encode("fdsfafsafas", "D:\\hwy.png", "aaa"); 
         // t.decode("D:\\hwy.png");  
    } 
    
	
	/**
	 * 给二维码图片加上文字
	 * @param pressText 文字
	 * @param qrFile  二维码文件
	 * @param fontStyle
	 * @param color
	 * @param fontSize
	 * @param width 二维码的宽
	 * @param height 二维码的高
	 */
	public static void pressText(String pressText, String qrcFile, int fontStyle, Color color, int fontSize, int width, int height) throws Exception {
		if (pressText != null) {
			File qrFile = new File(qrcFile); 
			pressText = new String(pressText.getBytes(), "utf-8");
			Image src = ImageIO.read(qrFile);
			int imageW = src.getWidth(null);
			int imageH = src.getHeight(null);
			BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, imageW, imageH, null);
			g.setColor(color);                                         //设置画笔的颜色
			Font font = new Font("宋体", fontStyle, fontSize);           //设置字体
			FontMetrics metrics = g.getFontMetrics(font);
			int startX = (width - metrics.stringWidth(pressText)) / 2;
			int startY = height;
			g.setFont(font);
			g.drawString(pressText, startX, startY);
			g.dispose();
			FileOutputStream out = new FileOutputStream(qrFile);
			ImageIO.write(image, "JPEG", out);
			out.close();
		}
	}
    
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
    
	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}
  
    // 生成二维码
    public static void encode(String str, String pngPath, String hasText) {  
        try {  
            BitMatrix byteMatrix;  
            byteMatrix = new MultiFormatWriter().encode(new String(str.getBytes("GBK"),"iso-8859-1"),  
                    BarcodeFormat.QR_CODE, 200, 200);  
            File file = new File(pngPath);                
            writeToFile(byteMatrix, "png", file); 
            if (!"".equals(hasText)) {
            	pressText(hasText, pngPath, Font.BOLD, Color.BLACK, 20, 200, 190);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    // 识别二维码
    public static String decode(String imgPath) {  
    	String resultStr = null;
        try {  
            File file = new File(imgPath);  
            BufferedImage image;  
            try {  
                image = ImageIO.read(file);  
                if (image == null) {  
                    System.out.println("Could not decode image");  
                }  
                LuminanceSource source = new BufferedImageLuminanceSource(image);  
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(  
                        source));  
                Result result;  
                Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();  
                hints.put(DecodeHintType.CHARACTER_SET, "GBK");  
                result = new MultiFormatReader().decode(bitmap, hints);  
                resultStr = result.getText();  
            } catch (IOException ioe) {  
                System.out.println(ioe.toString());  
            } catch (ReaderException re) {  
                System.out.println(re.toString());  
            }  
        } catch (Exception ex) {  
        }  
        return resultStr;
    }  
  
}  