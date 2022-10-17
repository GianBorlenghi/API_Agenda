package com.AgendaBackEnd.app.Exception;

import org.springframework.http.HttpStatus;

public class RequestException extends RuntimeException{
	
	private HttpStatus status;
	private String code;

	
	public RequestException() {
		super();
	}

	public RequestException(String message,HttpStatus status, String code) {
		super(message);
		this.status = status;
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	
	
	
	
	

}