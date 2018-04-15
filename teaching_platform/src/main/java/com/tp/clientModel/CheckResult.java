package com.tp.clientModel;

import io.jsonwebtoken.Claims;

public class CheckResult {
	private boolean success;
	private Claims claims;
	private String error;
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Claims getClaims() {
		return claims;
	}
	public void setClaims(Claims claims) {
		this.claims = claims;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
