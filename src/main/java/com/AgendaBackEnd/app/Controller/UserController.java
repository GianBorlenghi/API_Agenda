package com.AgendaBackEnd.app.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.AgendaBackEnd.app.Exception.BusinessException;
import com.AgendaBackEnd.app.Exception.RequestException;
import com.AgendaBackEnd.app.Exception.UserNotFoundException;
import com.AgendaBackEnd.app.Model.Folder;
import com.AgendaBackEnd.app.Model.User;
import com.AgendaBackEnd.app.Service.IUserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	
	
	@PutMapping("/editUser/{id}")
	@PreAuthorize("hasRole('USER')")
	@ResponseBody
	public ResponseEntity<?> editUser(@PathVariable(value = "id") Long id, @RequestParam(value = "name") String name,
			@RequestParam(value = "surname") String surname, @RequestParam(value = "dateOfBirth") String dateOfBirth,
			@RequestParam(value = "province") String province, @RequestParam(value = "city") String city,
			@RequestParam(value = "mail") String mail) throws ParseException {

		User user = userService.findUserById(id);

		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date birth = format.parse(dateOfBirth);

			if (user.getName().equals(name) && user.getSurname().equals(surname)
					&& user.getDate_of_birth().equals(birth) && user.getProvince().equals(province)
					&& user.getCity().equals(city) && user.getMail().equals(mail)) {

				return new ResponseEntity<>("Nothing change", HttpStatus.OK);

			} else {

				user.setName(name);
				user.setSurname(surname);
				user.setDate_of_birth(birth);
				user.setProvince(province);
				user.setCity(city);
				user.setMail(mail);

				userService.save(user);
				return new ResponseEntity<>(HttpStatus.OK);

			}

		} catch (NullPointerException e) {

			throw new UserNotFoundException("User not found", "P-404", HttpStatus.NOT_FOUND);
			
		}catch(AccessDeniedException e) {
			
			throw new BusinessException("You are not authorized, please login","P-401",HttpStatus.UNAUTHORIZED);
		}

	}
	
	@GetMapping("admin/viewConnectedUsers")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public List<String> viewConnectedUsers() {

		List<String> allUserConnected = userService.viewConnectedUsers();

		if (allUserConnected.size() == 0) {

			throw new RequestException("There are no users connected yet", HttpStatus.NOT_FOUND, "P-404");

		} else {

			return allUserConnected.stream()
					.sorted().
					collect(Collectors.toList());
		}

	}
	
	@GetMapping("/getUser/{id}")
	@ResponseBody
	public User getUser(@PathVariable ( value = "id") Long id) {
		
		try {
			
		User user = userService.findUserById(id);
		return user;
		
		}catch(NullPointerException e) {
			
			throw new UserNotFoundException("User not found", "P-404", HttpStatus.NOT_FOUND);

		}
		
	}

	@GetMapping("/getFolders/{id}")
	@PreAuthorize("hasRole('USER')")
	public List<Folder> getAllFolderXUser(@PathVariable(value="id") Long id_user) {
		
		return userService.findUserById(id_user).getFolder_x_user();
	}
	
	@PostMapping("/set_offline/{id}")
	public String set_offline(@PathVariable(value="id") Long id){
	 User us =  userService.findUserById(id);
	 us.setIs_online(false);
	 userService.save(us);
	 return "ok";
	}
	
}
