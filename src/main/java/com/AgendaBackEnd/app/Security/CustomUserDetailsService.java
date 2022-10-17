package com.AgendaBackEnd.app.Security;

import org.springframework.stereotype.Service;

import com.AgendaBackEnd.app.Model.Rol;
import com.AgendaBackEnd.app.Model.User;
import com.AgendaBackEnd.app.Service.IUserService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserService userServ;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userServ.findByUsername(username);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
				,mapRols(user.getRols()));
	}


	private Collection<? extends GrantedAuthority> mapRols(Set<Rol> rols) {

		return rols.stream().map(rol -> new SimpleGrantedAuthority(rol.getRole_name())).collect(Collectors.toList());
	}

}
