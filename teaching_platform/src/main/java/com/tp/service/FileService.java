package com.tp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sun.tools.internal.ws.processor.model.Service;
import com.tp.clientModel.UserRespModel;
import com.tp.model.FileInfo;
import com.tp.util.Result;
import com.tp.util.getUUID;

public class FileService {	
	FileInfo fileInfo = new FileInfo();
	
	public String upload(UploadFile file){	
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String path = date.format(new Date());
        File source = file.getFile();
        String fileName = file.getFileName(); //文件名 filename
        String inputFileName = null; //写进文件夹的文件名字，唯一辨别
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        String prefix;
        if(".png".equals(extension) || ".jpg".equals(extension) || ".jpeg".equals(extension) || ".gif".equals(extension)){
            prefix = "img";
            fileName = getUUID.getUUID() + extension;
            inputFileName = fileName;
        }else{
        	inputFileName = getUUID.getUUID()+fileName;
            prefix = "fileDir";
        }
        JSONObject json = new JSONObject();
        try {
            FileInputStream fis = new FileInputStream(source);
            File targetDir = new File(PathKit.getWebRootPath() + "/" + prefix + "/u/"
                    + path);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            File target = new File(targetDir, inputFileName);
            if (!target.exists()) {
                target.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(target);
            byte[] bts = new byte[300];
            while (fis.read(bts, 0, 300) != -1) {
                fos.write(bts, 0, 300);
            }
            fos.close();
            fis.close();
            
            String fileUrl = "/" + prefix + "/u/" + path + "/" + inputFileName;
            String fileRes = fileInfo.upload(fileUrl,fileName);
            if(fileRes != null) {
            	json.put("success", true);
            	json.put("file_id", fileRes);
            	json.put("url", fileUrl);
            } else {
            	json.put("success", false);
            	json.put("error", "DB wrong");
            }
            source.delete();
        } catch (FileNotFoundException e) {
            json.put("success", false);
            json.put("error", "Upload error, please upload again later.");
        } catch (IOException e) {
            json.put("success", false);
            json.put("error", "File write server error, please upload again later.");
        }
		return json.toJSONString();
	}
	
	// 老师上传文件到某一门课程中(course_file_info)
	public Result<UserRespModel> teaUploadFile(int courseId, String fileId, String title, String intro, String tea_id, String tea_name){
		Result<UserRespModel> result = null;
		int res = fileInfo.teaUploadFile(courseId, fileId, title, intro, tea_id, tea_name);
		
		switch(res){
		case -1:
			result = new Result<UserRespModel>(false, "The file_id doesn't exist");
			break;
		case 0:
			result = new Result<UserRespModel>(false, "Something wrong about inserting data into DB");
			break;
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		}
		return result;
	}
	
	// 取出该门课的所有文件 (by teaId and course_name)
	public Result<UserRespModel> getFilenameByTea(String teaId, String course_name){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> fileList = fileInfo.getFilenameByTea(teaId, course_name);
		result = new Result<UserRespModel>(false, "The course doesn't have any files");
		if(fileList != null){
			userRespModel.setList(fileList);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
	//取出该门课的所有文件（by courseId)
	public Result<UserRespModel> getFileByCourseId(int courseId){
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> fileList = fileInfo.getFileByCourseId(courseId);
		result = new Result<UserRespModel>(false, "The course doesn't have any files");
		if(fileList != null){
			int count = fileList.size();
			userRespModel.setCount(count);
			userRespModel.setList(fileList);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
	
}
