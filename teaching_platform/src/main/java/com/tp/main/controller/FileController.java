package com.tp.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.tp.clientModel.CheckResult;
import com.tp.clientModel.UserInfo;
import com.tp.clientModel.UserReqModel;
import com.tp.clientModel.UserRespModel;
import com.tp.service.CourseService;
import com.tp.service.FileService;
import com.tp.util.GetHeader;
import com.tp.util.Jwt;
import com.tp.util.Result;
import com.tp.util.getUUID;

import io.jsonwebtoken.Claims;


public class FileController extends Controller {
	
	static FileService service = new FileService();
	public void index() {
		render("index.html");
	}
	
	/**
	 * 上传文件or上传图片的统一接口
	 * 1. attention: 要先获取getFile("file"),再获取别的参数，否则获取不到("jwt")
	 */
	 public void upload() {
		 getResponse().setHeader("Access-Control-Allow-Origin", "*");
		  UploadFile file = getFile("file");
		  String jwt = getPara("Authorization");
		  
		  CheckResult checkResult = Jwt.validateJWT(jwt);
		  if(checkResult.getSuccess()) {
			  String result = service.upload(file);
			  renderJson(result);
		  }else{
			String error = checkResult.getError();
			renderJson(error);	
		 }
    }
	
	/**
	 *  1. 老师针对某一门课上传教学资源
	 *  2. 传的是course_id、file_id
	 */
	public void teaUploadFile(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
			String id = userInfo.getId();
		    String tea_name = userInfo.getName();
		    int type = userInfo.getType();
			  
		    // 只有老师才能上传教学资源： upload teaching resource
		    if(type == 2){
			  String jsonString = HttpKit.readData(getRequest());
			  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			  int course_id = user.getCourse_id();   // class 的 id :  班级的id
			  String file_id = user.getFile_id(); // 文件的Id
			  
			  Result<UserRespModel> result = service.teaUploadFile(course_id, file_id, id, tea_name);
			  renderJson(result);
		    } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to upload course_file");
			  renderJson(json);
		   }
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	// get filename(image or file) by teaId and courseName
    // 取出一门课中所有的上传的文件
	/**
	 * 1. 取出一门课中所有上传的文件
	 * 2. @param： course_id
	 */
	public void getFilenameByTea() {
		
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String jsonString = HttpKit.readData(getRequest());
		  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
		  String tea_id = user.getId(); // 文件的Id
		  String course_name = user.getCourse_name();
		  
		  Result<UserRespModel> result = service.getFilenameByTea(tea_id, course_name);
		  renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	// get file info by courseId
	// 取出一门课中所有上传的文件 by courseId
	public void getFileByCourseId(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int course_id = getParaToInt("course_id");
		  Result<UserRespModel> result = service.getFileByCourseId(course_id);
		  renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
}
