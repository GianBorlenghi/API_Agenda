package com.AgendaBackEnd.app.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AgendaBackEnd.app.Model.Rol;
import com.AgendaBackEnd.app.Repository.IRolRepository;
@Service
public class RolService implements IRolService{

	@Autowired
	private IRolRepository rolRepository;
	
	
	@Override
	@Transactional(readOnly=true)
	public String findBRolyUserId(Long id) {

		return rolRepository.getRolById(id);
	}


	@Override
	@Transactional(readOnly=true)
	public Rol findIdRolByRolName(String role_name) {

		return rolRepository.getIdRolByRolName(role_name);
	}


	@Override
	public void defaultRols()  {

		Rol rol = new Rol( Long.parseLong("1") ,"ROLE_ADMIN");
		Rol rol1 = new Rol(Long.parseLong("2"),"ROLE_USER");
		rolRepository.save(rol);
		rolRepository.save(rol1);
		
	}
		
	

	
}
