package com.tp.util;

import java.util.HashMap;

public class Result<T> {
	private boolean success;
	private T data = null;
	private String error = null;
	private HashMap<String, Object> message = null;
	
	
	public Result(boolean success, T data){
		this.success = success;
		this.data = data;
	}
	public Result(boolean success, String error){
		this.success = success;
		this.error = error;
	}
	public Result(boolean success, T data, String error) {
		this.success = success;
		this.data = data;
		this.error = error;
	}	
	public Result(boolean success) {
        this.success = success;
    }

    public Result(T data) {
        this.success = true;
        this.data = data;
    }

    public Result(String error) {
        this.success = false;
        this.error = error;
    }

    public Result(HashMap<String, Object> hashData) {
		// TODO Auto-generated constructor stub
    	this.success = true;
    	this.message = hashData;
	}
	public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
	public HashMap<String, Object> getMessage() {
		return message;
	}
	public void setMessage(HashMap<String, Object> message) {
		this.message = message;
	}
    
}
