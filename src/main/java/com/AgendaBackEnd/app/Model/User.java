package com.AgendaBackEnd.app.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_user;
	
	@Basic
	private String username;
	
	@Basic
	private String password;
	
	@Basic
	private String name;
	
	@Basic
	private String surname;
	
	@Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
	private Date date_of_birth;
	
	@Basic
	private String mail;
	
	@Basic
	
	private String province;
	
	@Basic 
	
	private String city;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso = ISO.NONE)
	@JsonFormat(shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime last_connection;

	
	@Basic
	private Boolean is_online;
	
	@Column(name="user_task")
	@OneToMany(mappedBy = "user_task",cascade = CascadeType.ALL )
	@JsonManagedReference(value = "user - task")
	private List<Task> task_x_user = new ArrayList<Task>();
	
	@Column(name = "user_folder")
	@OneToMany(mappedBy = "user_folder", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "user - folder")
	private List<Folder> folder_x_user = new ArrayList<Folder>();
	
	
	@ManyToMany(cascade = {
			CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST,
	},fetch = FetchType.EAGER)
	@JoinTable(
			
			name = "user_role",
			joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = true),
			inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", nullable = true)
			)
	private Set<Rol> rols = new HashSet<>();
	
	public User() {
		super();
	}

	public User(@NotEmpty(message = "the field must not be empty or null") Long id_user,
			String username,
			String password,
			 String name,
			 String surname, Date date_of_birth,
			 String mail,
			 String province,
			String city, LocalDateTime last_connection,
			Boolean is_online, List<Task> task_x_user, List<Folder> folder_x_user, Set<Rol> rols
			) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.date_of_birth = date_of_birth;
		this.mail = mail;
		this.province = province;
		this.city = city;
		this.last_connection = last_connection;
		this.is_online = is_online;
		this.task_x_user = task_x_user;
		//this.folder_x_user = folder_x_user;
		this.rols = rols;

	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDateTime getLast_connection() {
		return last_connection;
	}

	public void setLast_connection(LocalDateTime last_connection) {
		this.last_connection = last_connection;
	}

	public Boolean getIs_online() {
		return is_online;
	}

	public void setIs_online(Boolean is_online) {
		this.is_online = is_online;
	}

	public List<Task> getTask_x_user() {
		return task_x_user;
	}

	public void setTask_x_user(List<Task> task_x_user) {
		this.task_x_user = task_x_user;
	}

	public List<Folder> getFolder_x_user() {
		return folder_x_user;
	}

	public void setFolder_x_user(List<Folder> folder_x_user) {
		this.folder_x_user = folder_x_user;
	}

	public Set<Rol> getRols() {
		return rols;
	}

	public void setRols(Set<Rol> rols) {
		this.rols = rols;
	}


	
	
}
