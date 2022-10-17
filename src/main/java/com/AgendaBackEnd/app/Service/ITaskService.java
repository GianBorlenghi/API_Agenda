package com.AgendaBackEnd.app.Service;

import java.util.List;

import com.AgendaBackEnd.app.Model.Task;

public interface ITaskService {
	
	public void save(Task task);
	
	public List<Task> findAll();
	
	public Task findById(Long id);
	
	public void deleteById(Long id);

}
