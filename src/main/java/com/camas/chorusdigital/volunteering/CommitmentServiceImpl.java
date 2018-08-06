package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.security.User;
import com.camas.chorusdigital.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommitmentServiceImpl implements CommitmentService {
	private static final Logger log = LoggerFactory.getLogger(CommitmentServiceImpl.class);

	private CommitmentRepository repository;
	@Autowired
	public void setRepository(CommitmentRepository repository) {
		this.repository = repository;
	}

	private TaskService taskService;
	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private EffortService effortService;
	@Autowired
	public void setEffortService(EffortService effortService) {
		this.effortService = effortService;
	}

	@Override
	public Iterable<Commitment> list() {

		Iterable<Commitment> commitments = repository.findAll();
		for (Commitment commitment : commitments) {
			updateEffortPosted(commitment);
		}

		return commitments;
	}

	@Override
	public Commitment get(Long id) {

		Commitment commitment = repository.findById(id).get();
		updateEffortPosted(commitment);

		return commitment;
	}

	@Override
	public Commitment save(Commitment commitment) {
		return repository.save(commitment);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Task findTask(Long taskId) {
		return taskService.get(taskId);
	}

	@Override
	public User findUserByUserName(String userName) {
		return userService.findByUserName(userName);
	}

	@Override
	public Iterable<Commitment> findByTask(Task task) {

		Iterable<Commitment> commitments = repository.findAllByTask(task);
		for (Commitment commitment : commitments) {
			updateEffortPosted(commitment);
		}

		return commitments;
	}

	@Override
	public User findCurrentUser() {
		return userService.findCurrentUser();
	}

	@Override
	public Iterable<Commitment> commitmentsForCurrentUser() {

		User currentUser = userService.findCurrentUser();

		Iterable<Commitment> commitments = repository.findAllByUser(currentUser);
		for (Commitment commitment : commitments) {
			updateEffortPosted(commitment);
		}

		return commitments;
	}

	@Override
	public Iterable<Effort> findEffortsForCommitment(Commitment commitment) {
		return effortService.effortsForCommitment(commitment);
	}

	@Override
	public Iterable<User> findAvailableVolunteers() {
		Iterable<User> users = userService.list();
		List<User> volunteers = new ArrayList<>();

		for (User user : users) {
			if (user.hasRole("ROLE_VOLUNTEER")) {
				volunteers.add(user);
			}
		}
		return volunteers;
	}

	private void updateEffortPosted(Commitment commitment) {
		Iterable<Effort> efforts = findEffortsForCommitment(commitment);
		int hours = 0;
		for (Effort effort : efforts) {
			hours += effort.getHours();
		}
		commitment.setEffortPosted(hours);
	}


}
