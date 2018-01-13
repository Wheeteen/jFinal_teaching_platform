package com.tp.util;

public class Result<T> {
	private boolean success;
	private T data = null;
	private String error = null;
	
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
}
