package com.AgendaBackEnd.app.ServiceTest;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.AgendaBackEnd.app.Model.Folder;
import com.AgendaBackEnd.app.Model.Task;
import com.AgendaBackEnd.app.Model.User;
import com.AgendaBackEnd.app.Repository.IFolderRepository;
import com.AgendaBackEnd.app.Repository.ITaskRepository;
import com.AgendaBackEnd.app.Repository.IUserRepository;

@DataJpaTest
public class TaskTest {

	
	@Autowired
	private ITaskRepository taskRepository;

	@Test
	public void testSaveTask() {

		
				}
}
