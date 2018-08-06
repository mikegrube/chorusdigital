package com.camas.chorusdigital.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserPrefsServiceImpl implements UserPrefsService {
	private static final Logger log = LoggerFactory.getLogger(UserPrefsServiceImpl.class);

	private UserPrefsRepository repository;
	@Autowired
	public void setRepository(UserPrefsRepository repository) {
		this.repository = repository;
	}

	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Iterable<UserPrefs> list() {
		return repository.findAll();
	}

	@Override
	public UserPrefs get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public UserPrefs save(UserPrefs userPrefs) {
		return repository.save(userPrefs);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<TaskListStyle> availableTaskListStyles() {
		return Arrays.asList(TaskListStyle.values());
	}

	@Override
	public TaskListStyle getUserTaskListStyle() {

		User user = userService.findCurrentUser();

		UserPrefs userPrefs = repository.findByUser(user);

		if (userPrefs == null) {
			userPrefs = new UserPrefs();
			userPrefs.setTaskListStyle(TaskListStyle.ALL);
			userPrefs.setUser(user);
			userPrefs = save(userPrefs);
		}
		return userPrefs.getTaskListStyle();
	}

	@Override
	public void setUserTaskListStyle(TaskListStyle taskListStyle) {

		User user = userService.findCurrentUser();

		UserPrefs userPrefs = repository.findByUser(user);

		if (userPrefs == null) {
			userPrefs = new UserPrefs();
			userPrefs.setUser(user);
		}

		userPrefs.setTaskListStyle(taskListStyle);
		userPrefs = save(userPrefs);

		return;
	}

}
