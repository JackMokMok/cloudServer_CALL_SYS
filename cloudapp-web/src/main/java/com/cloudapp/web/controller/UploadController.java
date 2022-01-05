package com.cloudapp.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloudapp.core.entity.FileInfo;
import com.cloudapp.core.service.FileInfoService;
import com.cloudapp.lib.FileUtil;
import com.cloudapp.lib.ImageUtil;
import com.cloudapp.web.constants.CommonConstants;

/**
 * 文件上传（指上传到upload下）
 */

@Controller
public class UploadController {
	
	private static final Log logger = LogFactory
			.getLog(UploadController.class);
	
	@Resource
	private FileInfoService infoService;
	
	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param oldId
	 */
	@RequestMapping(value = "upload-file.json")
	@ResponseBody
	public void uploadFile(@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response, Integer oldId) {
		
		JSONObject jsonObject = new JSONObject();
		response.setContentType("text/html");
		File dirFile = new File(CommonConstants.RES_UPLOAD_PATH);
		if (!dirFile.exists())
			dirFile.mkdirs();

		String suffix = ImageUtil.parseExt(file.getOriginalFilename()).toLowerCase();	//后缀名
		
		long times = System.currentTimeMillis();
				
		String fileName = times + "." + suffix;	//文件全名
		
		String filePath = CommonConstants.RES_UPLOAD_PATH + "/" + fileName;
		
		File myFile = new File(filePath);
		try {
			FileCopyUtils.copy(file.getBytes(), myFile);
			
			
			if(suffix.equals("apk")){	//如果是apk文件
				@SuppressWarnings("resource")
				ApkFile apkFile = new ApkFile(myFile);
			    ApkMeta apkMeta = apkFile.getApkMeta();
			    
			    String label = apkMeta.getLabel();
			    Long versionCode = apkMeta.getVersionCode();
				String versionName = apkMeta.getVersionName();
				String packageName = apkMeta.getPackageName();
				
				jsonObject.put("label", label);
				jsonObject.put("versionCode", versionCode);
				jsonObject.put("versionName", versionName);
				jsonObject.put("packageName", packageName);
			}else if(suffix.equals("jpg") || suffix.equals("png")){
				long size = myFile.length()/1024;
				if(size > 300){		//如果图片大于300K 就压缩
					if(suffix.equals("jpg")){
						Thumbnails.of(filePath).size(1600,900).toFile(filePath);
					}else{		//如果是png图片转成jpg
						fileName = times + ".jpg";
						Thumbnails.of(filePath).size(1600,900).outputFormat("jpg").toFile(CommonConstants.RES_UPLOAD_PATH + "/" + fileName);
						filePath = CommonConstants.RES_UPLOAD_PATH + "/" + fileName;
					}
				}
			}
			
			String md5 = DigestUtils.md5Hex(new FileInputStream(filePath));
			FileInfo info = new FileInfo();
			if(oldId !=null && oldId != 0){		//如果有旧文件
				info = infoService.get(oldId);
				/*if(info != null){
					try{
						FileUtil.delete(new File(CommonConstants.RES_UPLOAD_PATH + "\\" + info.getPath()));
					}catch(Exception e){
						e.printStackTrace();
					}
				}*/
				info.setMd5(md5);
				info.setPath(fileName);
				infoService.update(info);
			}else{
				info.setMd5(md5);
				info.setPath(fileName);
				infoService.save(info);
			}
			
			jsonObject.put("status", 200);
			jsonObject.put("path", fileName);
			jsonObject.put("fileId", info.getId());
		} catch (Exception e) {
			jsonObject.put("status", -10);
			jsonObject.put("msg", e.getMessage());
			logger.error("error:" + e.getMessage());
		}
		try {
			response.getWriter().println(jsonObject.toString());
		} catch (IOException e) {
		}
	}
	
	/**
	 * 删除文件
	 */
	@RequestMapping(value = "remove-file.json")
	@ResponseBody
	private void delRes(String filepath) {
		try {
			if(!filepath.equals("")) {
				FileUtil.delete(new File(CommonConstants.RES_UPLOAD_PATH + "/" + filepath));
			}
		} catch (Exception e) {
			logger.error("删除文件失败（UploadController.java）, " + e.getMessage());
		}
	}
}
