package com.AgendaBackEnd.app.Service;

import com.AgendaBackEnd.app.Model.Rol;

public interface IRolService {

	public String findBRolyUserId(Long id);
	public Rol findIdRolByRolName(String role_name);
	public void defaultRols();
}
