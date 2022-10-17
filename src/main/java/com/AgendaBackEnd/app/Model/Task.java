package com.AgendaBackEnd.app.Model;



import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_task;

	@Basic
	@NotEmpty(message = "the field must not be empty or null")
	private String task_name;
	private boolean is_finished;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'H:mm",iso = ISO.NONE)
	@JsonFormat(shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd'T'H:mm")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime task_date_added;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'H:mm",iso = ISO.NONE)
	@JsonFormat(shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd'T'H:mm")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime task_date;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "id_folder")
	@JsonBackReference(value = "task - folder")
	private Folder task_folder;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "id_user")
	@JsonBackReference(value = "user - task")
	private User user_task;

	public Task() {
		super();
	}

	public Task(Long id_task, @NotEmpty(message = "the field must not be empty or null") String task_name,
			LocalDateTime task_date_added, LocalDateTime task_date,
			Folder task_folder, User user_task,boolean is_finished) {
		super();
		this.id_task = id_task;
		this.task_name = task_name;
		this.task_date_added = task_date_added;
		this.task_date = task_date;
		this.task_folder = task_folder;
		this.user_task = user_task;
		this.is_finished = is_finished;
	}

	public Long getId_task() {
		return id_task;
	}

	public void setId_task(Long id_task) {
		this.id_task = id_task;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public LocalDateTime getTask_date_added() {
		return task_date_added;
	}

	public void setTask_date_added(LocalDateTime task_date_added) {
		this.task_date_added = task_date_added;
	}

	public LocalDateTime getTask_date() {
		return task_date;
	}

	public void setTask_date(LocalDateTime task_date) {
		this.task_date = task_date;
	}

	public Folder getTask_folder() {
		return task_folder;
	}

	public void setTask_folder(Folder task_folder) {
		this.task_folder = task_folder;
	}

	public User getUser_task() {
		return user_task;
	}

	public void setUser_task(User user_task) {
		this.user_task = user_task;
	}

	public boolean getIs_finished() {
		return is_finished;
	}

	public void setIs_finished(boolean is_finished) {
		this.is_finished = is_finished;
	}

	
}
