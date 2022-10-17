package com.AgendaBackEnd.app.Controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.AgendaBackEnd.app.Exception.BusinessException;
import com.AgendaBackEnd.app.Exception.UserNotFoundException;
import com.AgendaBackEnd.app.Model.ErrorDTO;

@ControllerAdvice

public class AdviceController  extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException ex){
	ErrorDTO errorDTO = new ErrorDTO();
	
		errorDTO.setCode("P-500");
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setTimeStamp(new Date());
		return new ResponseEntity<ErrorDTO>(errorDTO, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorDTO> businessExceptionHandler(BusinessException ex){
		
		ErrorDTO errorDTO = new ErrorDTO();
		
		errorDTO.setCode(ex.getCode());
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setTimeStamp(new Date());
		
		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
		
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDTO> userNotFoundExceptionHandler(UserNotFoundException ex){
		
		ErrorDTO errorDTO = new ErrorDTO();
		
		errorDTO.setCode(ex.getCode());
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setTimeStamp(new Date());
		
		return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
	}
	

}
