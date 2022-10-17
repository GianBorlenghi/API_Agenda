package com.AgendaBackEnd.app.Model;

import java.util.Date;

public class ErrorDTO {

	private Date timeStamp;
	private String code;
	private String message;
	
	public ErrorDTO() {
		super();
	}
	
	public ErrorDTO(Date timeStamp, String code, String message) {
		super();
		this.timeStamp = timeStamp;
		this.code = code;
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
