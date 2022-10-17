package com.AgendaBackEnd.app.Controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.AgendaBackEnd.app.Model.MailSender;
import com.AgendaBackEnd.app.Model.Task;
import com.AgendaBackEnd.app.Model.TaskDTO;
import com.AgendaBackEnd.app.Service.ITaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = /*"*"*/"http://agenda-borlenghi-gian.web.app/")
@RequestMapping("/api/task")
public class TaskController {

	@Autowired
	private ITaskService taskService;

	@PostMapping("/createTask")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createTask(@RequestBody Task task) {

		try {
			task.setTask_date_added(LocalDateTime.now());
			task.setIs_finished(false);
			taskService.save(task);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (AccessDeniedException e) {

			throw new BusinessException("You are not authorized, please login", "P-401", HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/editTask/{id}")	
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changeDate(@PathVariable(value = "id") Long id_task,
			@RequestParam ( value = "task_name") String task_name,
			@RequestParam ( value = "task_date") String task_date){

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime new_date_task = LocalDateTime.parse(task_date , formatter);

		try {

			Task task = taskService.findById(id_task);
			task.setTask_name(task_name);
			task.setTask_date(new_date_task);
			taskService.save(task);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (NullPointerException e) {

			throw new BusinessException("Task with id " + id_task + " not found", "P-404", HttpStatus.NOT_FOUND);
		} catch (AccessDeniedException e) {

			throw new BusinessException("You are not authorized, please login", "P-401", HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("/deleteTask/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteTask(@PathVariable(value = "id") Long id_task) {

		try {
			taskService.deleteById(id_task);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {

			throw new BusinessException("Task with id " + id_task + " not found", "P-404", HttpStatus.NOT_FOUND);

		} catch (AccessDeniedException e) {

			throw new BusinessException("You are not authorized, please login", "P-401", HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("/finishTask/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> finishTask(@PathVariable(value = "id") Long id_task) {
		try {

			Task task = taskService.findById(id_task);

			if (task.getIs_finished() == true) {

				throw new BusinessException("Task is already finished", "P-409", HttpStatus.CONFLICT);

			} else {

				task.setIs_finished(true);
				taskService.save(task);

				return new ResponseEntity<>(HttpStatus.OK);
			}

		} catch (NullPointerException e) {

			throw new BusinessException("Task with id " + id_task + " not found", "P-404", HttpStatus.NOT_FOUND);

		}
	}
	
	
	@GetMapping("/sendToFinish/{mail}")
	public ResponseEntity<?> sendEmailFinish (@PathVariable ( value = "mail") String mail, 
							@RequestParam ( value="taskLists") String taskList) throws JsonMappingException, JsonProcessingException, AddressException, MessagingException{
		

		MailSender mail1 = new MailSender();

		mail1.sendMail(mail, "New password ToDoList", mail1.TaskLists(taskList));

		
        return new ResponseEntity<>(HttpStatus.OK);
        
	}
	
		
}
