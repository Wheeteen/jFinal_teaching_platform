package com.tp.service;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.tp.clientModel.UserRespModel;
import com.tp.model.Notice;
import com.tp.util.Result;

public class NoticeService {
	Notice notice = new Notice();
	
	/**
	 * 1. 发布公告（老师对该门课的某个班级发布公告）-》class_id
	 * @param classId
	 * @param title
	 * @param content
	 * @param file_id
	 * @param tea_id
	 * @param tea_name
	 * @return
	 */
	public Result<UserRespModel> createNotice(int classId, String title, String content, String file_id, String tea_id, String tea_name) {
		Result<UserRespModel> result = null;
		int noticeRes = notice.createNotice(classId, title, content, file_id, tea_id, tea_name);
		switch (noticeRes) {
		case 0:
			result = new Result<UserRespModel>(false, "DB wrong");
			break;
		case 1:
			// search notice_id
			Integer notice_id = notice.getNoticeId(classId, title);
			if(notice_id != null) {
				HashMap<String, Object> res_cid = new HashMap<String, Object>();
				res_cid.put("notice_id", notice_id);
				result = new Result<UserRespModel>(res_cid); // return course_id
			} else {
				result = new Result<UserRespModel>(false, "Can't get notice_id from db");
			}
			break;
		default:
			result = new Result<UserRespModel>(false, "The notice has been created, please create another notice which is different from that one.");
			break;
		
		}
		return result;
	}
	
	/**
	 * 更新公告
	 * @param notice_id
	 * @param content
	 * @param file_id
	 * @return
	 */
	public Result<UserRespModel> updateNotice(int notice_id, String content, String file_id){
		Result<UserRespModel> result = null;
		int res = notice.updateNotice(notice_id, content, file_id);
		switch (res) {
		case 1:
			result = new Result<UserRespModel>(true);
			break;
		case 0:
			result = new Result<UserRespModel>(false, "DB wrong");
			break;
		default:
			result = new Result<UserRespModel>(false, "The notice is not existed");
			break;
		}
		return result;
	}
	
	/**
	 * 1. 删除公告
	 * @param: notice_id
	 */
	public Result<UserRespModel> deleteNotice(int notice_id) {
		Result<UserRespModel> result = null;
		int res = notice.deleteNotice(notice_id);
		result = new Result<UserRespModel>(false, "notice_id is not found");
		if(res == 1){
			result = new Result<UserRespModel>(true);
		}
		return result;
	}
	/**
	 * 查询该门课所有的公告(按时间先后顺序)
	 * @param： class_id
	 */
	public Result<UserRespModel> getNoticeByClassId(int classId) {
		Result<UserRespModel> result = null;
		UserRespModel userRespModel = new UserRespModel();
		List<Record> taskList = notice.getNoticeByClassId(classId);

		result = new Result<UserRespModel>(false, "No notice");
		if(taskList != null){
			int count = taskList.size();
			userRespModel.setList(taskList);
			userRespModel.setCount(count);
			result = new Result<UserRespModel>(userRespModel);
		}
		return result;
	}
}
