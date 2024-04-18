package com.AgendaBackEnd.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.AgendaBackEnd.app.Model.Folder;
import com.AgendaBackEnd.app.Model.Task;
import com.AgendaBackEnd.app.Model.User;
import com.AgendaBackEnd.app.Service.IFolderService;
import com.AgendaBackEnd.app.Service.IUserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization", "Access-Control-Allow-Origin" })
@RequestMapping("/api/folder")
public class FolderController {

	@Autowired
	private IFolderService folderService;

	@Autowired
	private IUserService userService;
	
	//------------------------------------------------------------------------------
	
	
	@PostMapping("/createFolder")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createFolder(@RequestBody Folder folder){
		try {
		folder.setFolder_name(folder.getFolder_name().toLowerCase());
		
		Long id = folder.getUser_folder().getId_user();
		User user = userService.findUserById(id);		
		
		List<Folder> allFolder = user.getFolder_x_user();
		
		boolean exists = allFolder.stream()
				.anyMatch(f->f.getFolder_name().equals(folder.getFolder_name()));
		
		if(!exists) {
		
		folderService.save(folder);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
		}else {
			
			throw new BusinessException("Already have a folder with that name", "p-409", HttpStatus.CONFLICT);
			
		}
		}catch(AccessDeniedException e) {
			
			throw new BusinessException("You are not authorized, please login","P-401",HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	//------------------------------------------------------------------------------

	
	@PutMapping("/editFolder/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> editFolder(@PathVariable(value = "id") Long id,
										@RequestParam(value = "name") String name){
		
		Folder folder = folderService.findById(id);
		
		try {
			
			folder.setFolder_name(name);
			
			folderService.save(folder);
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}catch(NullPointerException e) {
			
			throw new BusinessException("Folder not found", "P-404", HttpStatus.NOT_FOUND);
			
		}catch(AccessDeniedException e) {
			
			throw new BusinessException("You are not authorized, please login","P-401",HttpStatus.UNAUTHORIZED);
		}
	}
	
	//------------------------------------------------------------------------------

	@DeleteMapping("/deleteFolder/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteFolder(@PathVariable(value = "id") Long id){
		
		try {
		
			folderService.deleteById(id);
		
			return new ResponseEntity<>(HttpStatus.OK);
			
		}catch(EmptyResultDataAccessException e) {
			
		throw new BusinessException("Folder not found", "P-404", HttpStatus.NOT_FOUND);
			
		}catch(AccessDeniedException e) {
			
			throw new BusinessException("You are not authorized, please login","P-401",HttpStatus.UNAUTHORIZED);
		}
	}
	
	//------------------------------------------------------------------------------

	@GetMapping("/getTasks/{id}")
	@PreAuthorize("hasRole('USER')")
	public List<Task> getTasks(@PathVariable(value = "id") Long id_folder) {
		
		return folderService.findById(id_folder).getTask_x_folder();
	
	}
}
