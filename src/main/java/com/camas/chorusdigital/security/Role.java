package com.camas.chorusdigital.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
	private static final Logger log = LoggerFactory.getLogger(Role.class);

	@Id
	@GeneratedValue
	private Long id;

	private String role;

	@ManyToMany
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "role_id"),
			     inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user){
		if(!this.users.contains(user)){
			this.users.add(user);
		}

		if(!user.getRoles().contains(this)){
			user.getRoles().add(this);
		}
	}

	public void removeUser(User user){
		this.users.remove(user);
		user.getRoles().remove(this);
	}

}
