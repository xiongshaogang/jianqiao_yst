package com.yst.web.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.DoubleUtils;
import org.alqframework.utils.UniqueUtils;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.yst.web.entity.patient.PatientImage;
import com.yst.web.model.AppResult;

public class UpLoadUtils {
	private static Log logger = LogFactory.getLog(UpLoadUtils.class);
	
	public static String getSerial(Date date, int index) {
		long msel = date.getTime();
		SimpleDateFormat fm = new SimpleDateFormat("MMddyyyyHHmmssSS");
		msel += index;
		date.setTime(msel);
		String serials = fm.format(date);
		return serials;
	}

	// 检查是否是图片格式
	public static boolean checkIsImage(String imgStr) {
		boolean flag = false;
		if (imgStr != null) {
			if (imgStr.equalsIgnoreCase(".gif")
					|| imgStr.equalsIgnoreCase(".jpg")
					|| imgStr.equalsIgnoreCase(".jpeg")
					|| imgStr.equalsIgnoreCase(".png")) {
				flag = true;
			}
		}
		return flag;
	}

	public static Date StrToDate(String str) throws ParseException {
		return new SimpleDateFormat("MM/dd/yyyy").parse(str);
	}

	public static AppResult saveFile(Map<String, String> imageMap, String module) {// 内部调用上传
		logger.debug("=========================================saveFile方法========================");

		// imageUploadPath 图片上传路径
		AppResult appResult = new AppResult();
		appResult.setVersion(1);
		appResult.setResult(AppResult.FAILED);
		String extName = ".jpg"; // 保存文件拓展名
		if(module.equals("Android")){
			extName=".apk";
		}else if(module.equals("IOS")){
			extName=".ipa";
		}
		String newFileName = ""; // 保存新的文件名
		String nowTimeStr = ""; // 保存当前时间
		SimpleDateFormat sDateFormat;
		Random r = new Random();
		String basePath = ServerParam.BASE_PATH;
		String savePath = ServerParam.IMAGE_UPLOAD_PATH + module + "/";// 上传目录
		Set<Entry<String, String>> set = imageMap.entrySet();
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Entry<String, String> entry : set) {
			String imageName = entry.getKey();
			String imageStr = entry.getValue();
			byte[] uploadImage = decode(imageStr);
			FileOutputStream fos = null;
			File fileupload = null;
			try {
				fileupload = new File("temp.te");
				if (!fileupload.exists()) {
					fileupload.createNewFile();
				}
				logger.debug(savePath + "_______________size:"
						+ uploadImage.length + "====================="
						+ basePath);
				fos = new FileOutputStream(fileupload);
				fos.write(uploadImage);
				fos.flush();
				fos.close();
				// 获取文件大小
				long fileSize = fileupload.length();
				// 1m=1024*1024
				if (fileSize > 1024 * 1024*10) {
					appResult.setError_info(imageName + "上传的文件大于10M");
				} else {
					// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
					int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
					sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
					nowTimeStr = sDateFormat.format(new Date()); // 当前时间
					// 获取拓展名
					newFileName = nowTimeStr + rannum + extName; // 文件重命名后的名字
					String filePath = savePath + newFileName;
					filePath = filePath.replace("\\", "/");
					String img_url = "upload/" + module + "/" + newFileName;
					// 检查上传的是否是图片
					// if (UpLoadUtils.checkIsImage(extName)) {
					FileUtils.copyFile(fileupload, new File(filePath));
					appResult.setError_info(imageName + "，上传成功");
					resultMap.put(imageName, img_url);
					appResult.setResult(AppResult.SUCCESS);

					// } else {
					// appResult.setError_info(imageName
					// + "上传的文件类型错误，请选择jpg,jpeg,png和gif格式的图片!");
					// }

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				appResult.setError_info(imageName + "上传失败，出错啦!"
						+ e.getMessage());
			} finally {

				try {
					if (fos != null) {
						fos.close();
					}
					fileupload.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		appResult.setImg_urls(resultMap);
		return appResult;
	}

	public static String encode(byte[] byteArray) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode(byteArray);
	}

	public static byte[] decode(String base64EncodedString) {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		try {
			return base64Decoder.decodeBuffer(base64EncodedString);
		} catch (IOException e) {
			return null;
		}
	}

	public static String getImageBase64(String image) {
		String base64str = "";
		base64str = encode(getBytes(image));
		return base64str;
	}

	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	/**
	 * springMvc上传
	 * @param urlfile
	 * @param field
	 * @return
	 */
	public static Result springSaveFile(MultipartFile urlfile,String module) {
		String fileName = null;
		PatientImage patientImage = null;
		try {
			if (urlfile.isEmpty()) {
				return ResultUtils.returnError("上传文件失败");
			} else {
				String basePath = SpringMVCUtils.getRequest().getRealPath("/upload/"+module);
				File f = new File(basePath);
				f.mkdirs();
				String path = null;
				fileName = urlfile.getOriginalFilename();
				boolean isFile= StringUtils.endsWithAny(StringUtils.lowerCase(fileName), new String[] { ".png", ".jpg",".jpeg",".bmp",".gif"});
				String sysFileName = UniqueUtils.getOrder() + "."+ StringUtils.substringAfter(fileName, ".");
				if (isFile) {
					path = basePath + "/" + sysFileName;
				} else {
					return ResultUtils.returnError("文件格式不正确,上传文件失败");
				}
				urlfile.transferTo(new File(path));
				patientImage = new PatientImage();
				patientImage.setMemory(DoubleUtils.div(urlfile.getSize(), 1024000.0, 2));
				patientImage.setName(fileName);
				patientImage.setAddress("upload/"+module+"/"+sysFileName);
				return ResultUtils.returnSuccess("上传成功", patientImage);
			}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultUtils.returnError("上传失败");
			}
	}
	
	/**
	 * springMvc上传
	 * @param urlfile
	 * @param field
	 * @return
	 */
	public static Result springUploadFile(MultipartFile urlfile,String module) {
		String fileName = null;
		try {
			if (urlfile.isEmpty()) {
				return ResultUtils.returnError("上传文件失败");
			} else {
				String basePath = SpringMVCUtils.getRequest().getRealPath("/upload/"+module);
				File f = new File(basePath);
				f.mkdirs();
				String path = null;
				fileName = urlfile.getOriginalFilename();
				boolean isFile= StringUtils.endsWithAny(StringUtils.lowerCase(fileName), new String[] { ".png", ".jpg",".jpeg",".bmp",".gif"});
				String sysFileName = UniqueUtils.getOrder() + "."+ StringUtils.substringAfter(fileName, ".");
				if (isFile) {
					path = basePath + "/" + sysFileName;
				} else {
					return ResultUtils.returnError("文件格式不正确,上传文件失败");
				}
				urlfile.transferTo(new File(path));
				return ResultUtils.returnSuccess("上传成功", "upload/"+module+"/"+sysFileName);
			}
			} catch (Exception e) {
				e.printStackTrace();
				return ResultUtils.returnError("上传失败");
			}
	}
}