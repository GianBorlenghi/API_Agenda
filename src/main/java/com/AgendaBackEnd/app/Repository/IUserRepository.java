package com.AgendaBackEnd.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.AgendaBackEnd.app.Model.User;

@Repository
public interface IUserRepository  extends CrudRepository<User, Long>{

	@Query(value = "SELECT * FROM users where username = :username",nativeQuery = true)
	User findByUsernameUser(@Param("username") String username);
	
	@Query(value = "SELECT COUNT(mail) FROM users WHERE mail = :text",nativeQuery = true)
	int findByMail(@Param("text") String mail);
	
	@Query(value = "SELECT username FROM users where is_online = true",nativeQuery = true)
	List<String> viewConnectedUsers ();
	
	
}
