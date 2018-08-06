package com.camas.chorusdigital.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class UserPrefs {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@ManyToOne
	User user;

	private TaskListStyle taskListStyle;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TaskListStyle getTaskListStyle() {
		return taskListStyle;
	}

	public void setTaskListStyle(TaskListStyle taskListStyle) {
		this.taskListStyle = taskListStyle;
	}
}
