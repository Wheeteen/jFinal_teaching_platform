package com.tp.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.tp.clientModel.UserInfo;
import com.tp.clientModel.UserReqModel;
import com.tp.clientModel.UserRespModel;
import com.tp.service.NoticeService;
import com.tp.util.GetHeader;
import com.tp.util.Result;

public class NoticeController extends Controller {
	static NoticeService service = new NoticeService();
	
	/**
	 * 1. 发布公告（老师对该门课的某个班级发布公告）-》class_id
	 * 2. 可以使用 Map<String, String> map = new HashMap<>(); // key--value
	 */
	public void createNotice(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  String id = userInfo.getId();
		  String tea_name = userInfo.getName();
		  int type = userInfo.getType();
		  
		  // 只有老师才能create notice
		  if(type == 2){
			  String jsonString = HttpKit.readData(getRequest());
			  UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			  int class_id = user.getClass_id(); // 班级id
			  String title = user.getTitle(); 
			  String content = user.getContent();
			  String file_id = user.getFile_id(); // 若有上传文件，传文件的Id
			  
			  Result<UserRespModel> result = service.createNotice(class_id, title, content, file_id, id, tea_name);
			  renderJson(result);
		  } else {
			  JSONObject json = new JSONObject();
			  json.put("error", "The user is a student,he has no access to create notice");
			  renderJson(json);
		  }
		}
		else {
		  renderJson(userInfo.getError());
		}

	}
	
	/**
	 * 1. 更新公告：修改公告内容 或者 上传公告的文件：content 和  file_id
	 * @param: notice_id 、content、 file_id
	 * important: notice_id 和  content 一定要传！ file_id可以不传
	 * @method: post
	 */
	public void updateNotice(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		    String jsonString = HttpKit.readData(getRequest());
			UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
			int notice_id = user.getNotice_id();   // 公告 的 id 
			String content = user.getContent(); // 公告的内容
			String file_id = user.getFile_id(); // 若有上传文件，传文件的Id
			
		    Result<UserRespModel> result = service.updateNotice(notice_id, content, file_id);
		    renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	
	/**
	 * 1. 删除公告
	 * @param: notice_id
	 */
	public void deleteNotice() {
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
//		    String jsonString = HttpKit.readData(getRequest());
//			UserReqModel user =  JSONObject.parseObject(jsonString, UserReqModel.class);
//			int notice_id = user.getNotice_id();   // 公告 的 id 
			int notice_id = getParaToInt("notice_id");
		    Result<UserRespModel> result = service.deleteNotice(notice_id);
		    renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
	/**
	 * 查询该门课所有的公告(按时间先后顺序)
	 * @param： class_id
	 */
	public void getNoticeByClassId(){
		UserInfo userInfo = GetHeader.getHeader(getRequest());
		if(userInfo.isSuccess()){
		  int classId = getParaToInt("class_id");
		  Result<UserRespModel> result = service.getNoticeByClassId(classId);
		  renderJson(result);
		}
		else {
		  renderJson(userInfo.getError());
		}
	}
}
