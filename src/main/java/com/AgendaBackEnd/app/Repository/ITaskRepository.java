package com.AgendaBackEnd.app.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.AgendaBackEnd.app.Model.Task;

@Repository
public interface ITaskRepository extends CrudRepository<Task, Long>{

}
