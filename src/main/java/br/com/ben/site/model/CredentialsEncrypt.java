package br.com.ben.site.model;

import com.google.gson.annotations.SerializedName;

public class CredentialsEncrypt {
	
	@SerializedName("accessToken")
	private String accessToken;
	
	@SerializedName("publicKey")
	private String publicKey;
	
	private ErrorHandler error;
	
	public ErrorHandler getError() {
		return error;
	}

	public void setError(ErrorHandler error) {
		this.error = error;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
