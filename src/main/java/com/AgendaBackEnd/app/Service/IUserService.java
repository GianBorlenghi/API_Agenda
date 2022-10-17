package com.AgendaBackEnd.app.Service;

import java.util.List;

import com.AgendaBackEnd.app.Model.User;


public interface IUserService {
	
	public User findByUsername(String username);
	
	public void save(User user);
	
	public int findUserByMail(String mail);
	
	public void deleteUserById(Long id);
	
	public User findUserById(Long id);
	
	public List<String> viewConnectedUsers();
	
	public void defaultAdmin(User administrator);
	
}
