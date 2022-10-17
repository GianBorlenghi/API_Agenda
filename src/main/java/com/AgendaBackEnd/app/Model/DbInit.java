package com.AgendaBackEnd.app.Model;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AgendaBackEnd.app.Repository.IRolRepository;
import com.AgendaBackEnd.app.Service.IRolService;
import com.AgendaBackEnd.app.Service.IUserService;

@Component
public class DbInit {
		

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRolService rolService;
	
	@PostConstruct
	private void postConstruct() {
		
		Rol r = rolService.findIdRolByRolName("ROLE_ADMIN");
		if(r == null) {
			rolService.defaultRols();
		}
		
		User us = userService.findByUsername("giancla");
		
		if(us == null) {
			User userDefault = new User();
			
			userDefault.setName("Gian");
			userDefault.setUsername("Borlenghi");
			userDefault.setCity("Santa Clara del Mar");
			userDefault.setUsername("giancla");
			userDefault.setPassword("$2a$10$vESJ2XmxUfc27MCdAFsXsu1xtoaIeFM2fp4dy1JonkOlHs2QCU.eW");
			userService.save(userDefault);
			userService.defaultAdmin(userDefault);
		}
		
	}
	

}
