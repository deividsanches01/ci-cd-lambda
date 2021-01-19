package br.com.ben.site.model;

import com.google.gson.annotations.SerializedName;

public class Code {
	@SerializedName("code")
	private String code;
	
	private ErrorHandler error = null;

	public ErrorHandler getError() {
		return error;
	}

	public void setError(ErrorHandler error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Code() {
		
	}
}
