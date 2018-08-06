package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.security.User;

import javax.persistence.*;

@Entity
public class Commitment {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Task task;
	@ManyToOne
	private User user;

	@Transient
	private Integer effortPosted;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getEffortPosted() {
		return effortPosted;
	}

	public void setEffortPosted(Integer effortPosted) {
		this.effortPosted = effortPosted;
	}
}
