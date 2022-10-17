package com.AgendaBackEnd.app.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AgendaBackEnd.app.Model.Rol;
import com.AgendaBackEnd.app.Model.User;
import com.AgendaBackEnd.app.Repository.IUserRepository;

@Service
public class UserService implements IUserService{

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRolService rolService;
	
	
	@Override
	@Transactional(readOnly=true)
	public User findByUsername(String username) {
		User user = userRepository.findByUsernameUser(username);
		return user;
	}


	@Override
	@Transactional
	public void save(User user) {

		userRepository.save(user);
		
	}


	@Override
	@Transactional(readOnly=true)
	public int findUserByMail(String mail) {
		return userRepository.findByMail(mail);
	}


	@Override
	@Transactional
	public void deleteUserById(Long id) {
		
		userRepository.deleteById(id);
		
	}


	@Override
	@Transactional(readOnly=true)
	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow();
	}


	@Override
	@Transactional(readOnly=true)
	public List<String> viewConnectedUsers() {
		
		return userRepository.viewConnectedUsers();
	}


	@Override
	public void defaultAdmin(User administrator) {

		Rol rol = rolService.findIdRolByRolName("ROLE_ADMIN");
		Rol rol1 = rolService.findIdRolByRolName("ROLE_USER");
		
		Set<Rol> rols = new HashSet<Rol>();
		rols.add(rol);
		rols.add(rol1);

		
		administrator.setRols(rols);
		
		
		userRepository.save(administrator);
		
	}
	


	
}
