package com.AgendaBackEnd.app.ControllerTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import com.AgendaBackEnd.app.Controller.AuthController;
import com.AgendaBackEnd.app.Service.IUserService;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IUserService userService;

	 @Test
	 void testLogin() throws Exception {
		 
		 mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON))
		 .andExpect(status().isOk())
		 .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		 
	 }
}
