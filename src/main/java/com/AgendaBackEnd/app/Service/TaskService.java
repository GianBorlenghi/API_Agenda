package com.AgendaBackEnd.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AgendaBackEnd.app.Model.Task;
import com.AgendaBackEnd.app.Repository.ITaskRepository;

@Service
public class TaskService implements ITaskService{

	@Autowired
	private ITaskRepository taskRepository;
	
	@Override
	@Transactional
	public void save(Task task) {
		
		taskRepository.save(task);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Task> findAll() {
		
		List<Task> allTask = (List<Task>) taskRepository.findAll();
		return allTask;
		
	}

	@Override
	@Transactional(readOnly=true)
	public Task findById(Long id) {
		
		Task task = taskRepository.findById(id).orElseThrow();
		return task;
		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {

		taskRepository.deleteById(id);
		
	}

}
