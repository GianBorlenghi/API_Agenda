package com.AgendaBackEnd.app.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "folders")
public class Folder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_folder;
	
	@Basic
	private String folder_name;
	
	@Column(name = "task_folder")
	@OneToMany(mappedBy = "task_folder", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "task - folder")
	private List<Task> task_x_folder = new ArrayList<Task>();
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "id_user")
	@JsonBackReference(value = "user - folder")
	private User user_folder;

	public Folder() {
		super();
	}

	public Folder(Long id_folder, String folder_name, List<Task> task_x_folder, User user_folder) {
		super();
		this.id_folder = id_folder;
		this.folder_name = folder_name;
		this.task_x_folder = task_x_folder;
		this.user_folder = user_folder;
	}

	public Long getId_folder() {
		return id_folder;
	}

	public void setId_folder(Long id_folder) {
		this.id_folder = id_folder;
	}

	public String getFolder_name() {
		return folder_name;
	}

	public void setFolder_name(String folder_name) {
		this.folder_name = folder_name;
	}

	public List<Task> getTask_x_folder() {
		return task_x_folder;
	}

	public void setTask_x_folder(List<Task> task_x_folder) {
		this.task_x_folder = task_x_folder;
	}

	public User getUser_folder() {
		return user_folder;
	}

	public void setUser_folder(User user_folder) {
		this.user_folder = user_folder;
	}
	
	

	
}
