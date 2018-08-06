package com.camas.chorusdigital.security;

public interface UserPrefsService {

	Iterable<UserPrefs> list();

	UserPrefs get(Long id);

	UserPrefs save(UserPrefs userPrefs);

	void delete(Long id);

	Iterable<TaskListStyle> availableTaskListStyles();

	TaskListStyle getUserTaskListStyle();

	void setUserTaskListStyle(TaskListStyle taskListStyle);
}
