package com.hul.launch.response;

import org.springframework.http.HttpStatus;

import com.hul.launch.beans.Status;

public class GlobleResponse {
	private Object responseData;
	private Status status;
	private HttpStatus httpStatusCode;

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public HttpStatus getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatus httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public GlobleResponse(Object responseData, Status status, HttpStatus httpStatusCode) {
		super();
		this.responseData = responseData;
		this.status = status;
		this.httpStatusCode = httpStatusCode;
	}

	public GlobleResponse(String message, Integer statusCode) {
		this.status = new Status(statusCode, message);
	}

	public GlobleResponse(String message, Integer statusCode, Object responseData) {
		this.status = new Status(statusCode, message);
		this.responseData = responseData;
	}

	public GlobleResponse(String message, Integer statusCode, HttpStatus httpStatusCode) {
		this.status = new Status(statusCode, message);
		this.httpStatusCode = httpStatusCode;
	}

	@Override
	public String toString() {
		return "GlobleResponse [responseData=" + responseData + ", status=" + status + ", httpStatusCode="
				+ httpStatusCode + "]";
	}

}
