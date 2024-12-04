package com.oauth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
	private String access_token;
	private float expires_in;
	private float refresh_expires_in;
	private String refresh_token;
	private String session_state;
	private String scope;
	@JsonProperty("not-before-policy")
	public int notBeforePolicy;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public float getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(float expires_in) {
		this.expires_in = expires_in;
	}

	public float getRefresh_expires_in() {
		return refresh_expires_in;
	}

	public void setRefresh_expires_in(float refresh_expires_in) {
		this.refresh_expires_in = refresh_expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getSession_state() {
		return session_state;
	}

	public void setSession_state(String session_state) {
		this.session_state = session_state;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public int getNotBeforePolicy() {
		return notBeforePolicy;
	}

	public void setNotBeforePolicy(int notBeforePolicy) {
		this.notBeforePolicy = notBeforePolicy;
	}

}
