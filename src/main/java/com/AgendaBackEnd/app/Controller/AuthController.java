package com.AgendaBackEnd.app.Controller;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.AgendaBackEnd.app.Exception.BusinessException;
import com.AgendaBackEnd.app.Exception.RequestException;
import com.AgendaBackEnd.app.Exception.UserNotFoundException;
import com.AgendaBackEnd.app.Model.ChangePasswordDTO;
import com.AgendaBackEnd.app.Model.LoginDTO;
import com.AgendaBackEnd.app.Model.MailSender;
import com.AgendaBackEnd.app.Model.Rol;
import com.AgendaBackEnd.app.Model.User;
import com.AgendaBackEnd.app.Security.JWTAuthResponseDTO;
import com.AgendaBackEnd.app.Security.JwtTokenProvider;
import com.AgendaBackEnd.app.Service.IRolService;
import com.AgendaBackEnd.app.Service.IUserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization", "Access-Control-Allow-Origin" })
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private IUserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IRolService rolService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/auth/register")
	public ResponseEntity<?> register(@RequestBody User user) throws IOException {

		// Validacion registro
		user.setUsername(user.getUsername().toLowerCase());
		User user2 = userService.findByUsername(user.getUsername());
		int emailExists = userService.findUserByMail(user.getMail());

		if (user2 == null && (emailExists == 0)) {

			user.setPassword(passwordEncoder.encode(user.getPassword()));
			Rol rol = rolService.findIdRolByRolName("ROLE_USER");
			user.setRols(Collections.singleton(rol));
			userService.save(user);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} else if (user2 != null) {

			throw new BusinessException("Username is already in use", "409", HttpStatus.CONFLICT);

		} else if (emailExists != 0) {
			throw new BusinessException("Mail is already in use", "409", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	// ------------------------------------------------------------------------------------------------------------

	@PostMapping("/auth/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {

		// Login y validación.

		try {
			User user = userService.findByUsername(loginDTO.getUsername());

			if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
				throw new BadCredentialsException("Bad Credentials");
			}

			LocalDateTime time = LocalDateTime.now();
			user.setLast_connection(time);
			user.setIs_online(true);
			userService.save(user);
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(auth);
			String token = jwtTokenProvider.generateToken(auth);

			String rol = rolService.findBRolyUserId(user.getId_user());
			return ResponseEntity.ok(new JWTAuthResponseDTO(token, "Bearer", user.getId_user(), rol));

		} catch (NullPointerException e) {

			throw new UserNotFoundException("User not found", "404", HttpStatus.NOT_FOUND);
		}
	}

	// ------------------------------------------------------------------------------------------------------------

	@DeleteMapping("/admin/deleteUser/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {

		try {
			userService.deleteUserById(id);

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (EmptyResultDataAccessException e) {

			throw new BusinessException("User with id " + id + " not found", "P-404", HttpStatus.NOT_FOUND);

		} catch (AccessDeniedException e) {

			throw new BusinessException("You are not authorized, only user with role admin can use this method",
					"P-401", HttpStatus.UNAUTHORIZED);
		}
	}

	// ------------------------------------------------------------------------------------------------------------

	@PutMapping("/auth/forgotPassword/{username}")
	public ResponseEntity<?> forgotPassword(@PathVariable(value = "username") String username)
			throws AddressException, MessagingException {

		// Return new password at mail.
		String chars = "abcdefghijklmnÑopqrstvwxyzABCDEFGHIJKLMNÑOPQRSTVWXYZ1234567890";
		String chain = "";

		for (int i = 0; i < 12; i++) {
			int index = ThreadLocalRandom.current().nextInt(0, chars.length() - 1);
			char aleator = chars.charAt(index);
			chain += aleator;
		}

		User user = userService.findByUsername(username);
		try {
			user.setPassword(passwordEncoder.encode(chain));
			userService.save(user);
			MailSender mail = new MailSender();

			mail.sendMail(user.getMail(), "New password ToDoList", mail.newPassword(chain));

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NullPointerException e) {
			throw new UserNotFoundException("User not found", "P-404", HttpStatus.NOT_FOUND);
		}

	}

	// ------------------------------------------------------------------------------------------------------------

	@PutMapping("/auth/changePassword/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword(@PathVariable(value = "id") Long id,
			@RequestBody ChangePasswordDTO changePasswordDto
	/*
	 * @RequestParam(value = "newPassword") String newPassword,
	 * 
	 * @RequestParam(value = "actualPassword") String actualPassword
	 */) {

		User user = userService.findUserById(id);

		try {

			if (passwordEncoder.matches(changePasswordDto.getNewPassword()/* newPassword */, user.getPassword())) {

				throw new RequestException("The password entered is the current of the user", HttpStatus.CONFLICT,
						"P-409");

			} else if ((passwordEncoder.matches(/* actualPassword */changePasswordDto.getActualPassword(),
					user.getPassword()))
					&& (!passwordEncoder.matches(/* newPassword */changePasswordDto.getNewPassword(),
							user.getPassword()))) {

				user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));

				userService.save(user);

				return new ResponseEntity<>(HttpStatus.OK);
			}

		} catch (NullPointerException e) {

			throw new UserNotFoundException("User not found", "P-404", HttpStatus.NOT_FOUND);

		} catch (AccessDeniedException e) {

			throw new BusinessException("You are not authorized, please login", "P-401", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>("Actual password entered is invalid", HttpStatus.CONFLICT);
	}

	// ------------------------------------------------------------------------------------------------------------

	@PostMapping("/auth/logout")
	public ResponseEntity<?> logout() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username);

		try {
			
			user.setIs_online(false);

			userService.save(user);

			SecurityContextHolder.clearContext();
			
			return new ResponseEntity<>("Logout", HttpStatus.OK);

		} catch (NullPointerException e) {

			throw new UserNotFoundException("No user conected", "P-404", HttpStatus.NOT_FOUND);

		} catch (AccessDeniedException e) {

			throw new BusinessException("You are not authorized, please login", "P-401", HttpStatus.UNAUTHORIZED);
		}

	}

	// ------------------------------------------------------------------------------------------------------------

}
