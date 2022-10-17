package com.AgendaBackEnd.app.Model;

import org.springframework.beans.factory.annotation.Required;

public class ChangePasswordDTO {
	
	
	private String actualPassword;
	private String newPassword;
	
	public ChangePasswordDTO() {
		super();
	}

	public String getActualPassword() {
		return actualPassword;
	}

	public void setActualPassword(String actualPassword) {
		this.actualPassword = actualPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	

}
