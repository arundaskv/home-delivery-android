package com.delivery.apis.response;

import java.io.InputStream;

public class ResponseData {
	InputStream inputStream;
	int statuscode;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public int getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}
	
	
}
