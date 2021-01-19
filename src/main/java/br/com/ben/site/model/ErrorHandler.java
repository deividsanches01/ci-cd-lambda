package br.com.ben.site.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorHandler {
	
	@SerializedName("timestamp")
	@Expose
	private String timestamp;
	@SerializedName("exception")
	@Expose
	private String exception;
	@SerializedName("messages")
	@Expose
	private List<String> messages = null;
	@SerializedName("path")
	@Expose
	private String path;
	@SerializedName("statusCode")
	@Expose
	private int statusCode;
	
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
