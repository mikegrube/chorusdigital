package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertService;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.performance.PerformanceService;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.season.SeasonService;
import com.camas.chorusdigital.security.TaskListStyle;
import com.camas.chorusdigital.security.UserPrefsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
	private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private TaskRepository repository;
	@Autowired
	public void setRepository(TaskRepository repository) {
		this.repository = repository;
	}

	private CommitmentService commitmentService;
	@Autowired
	public void setCommitmentService(CommitmentService commitmentService) {
		this.commitmentService = commitmentService;
	}

	private UserPrefsService userPrefsService;
	@Autowired
	public void setUserPrefsService(UserPrefsService userPrefsService) {
		this.userPrefsService = userPrefsService;
	}

	private EventService eventService;
	@Autowired
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	@Override
	public Iterable<Task> list() {

		TaskListStyle taskListStyle = userPrefsService.getUserTaskListStyle();
		switch (taskListStyle.name()) {
			case "ALL":
				return listAll();
			case "ACTIVE":
				return listActive();
			case "MINE":
				return listForVolunteer();
			case "ALERTED":
				return listAlerted();
			default:
				return listAll();
		}
	}

	@Override
	public Iterable<Task> listAvailable() {
		Iterable<Task> tasks = repository.findAllByActiveOrderByEventDateAsc(true);
		List<Task> availableTasks = new ArrayList<>();

		for (Task task : tasks) {
			updateUnfilledSlots(task);
			if (task.getUnfilledSlots() > 0) {
				availableTasks.add(task);
			}
		}

		return availableTasks;
	}

	@Override
	public Iterable<Task> listAll() {

		Iterable<Task> tasks = repository.findAllByOrderByEventDateAsc();
		for (Task task : tasks) {
			updateUnfilledSlots(task);
		}

		userPrefsService.setUserTaskListStyle(TaskListStyle.ALL);

		return tasks;
	}

	@Override
	public Iterable<Task> listActive() {

		Iterable<Task> tasks = repository.findAllByActiveOrderByEventDateAsc(true);
		for (Task task : tasks) {
			updateUnfilledSlots(task);
		}

		userPrefsService.setUserTaskListStyle(TaskListStyle.ACTIVE);

		return tasks;
	}

	@Override
	public Iterable<Task> listForVolunteer() {

		List<Task> tasks = new ArrayList<>();
		Iterable<Commitment> commitments = commitmentService.commitmentsForCurrentUser();
		for (Commitment commitment : commitments) {
			Task task = commitment.getTask();
			updateUnfilledSlots(task);
			tasks.add(task);
		}

		userPrefsService.setUserTaskListStyle(TaskListStyle.MINE);

		return tasks;
	}

	@Override
	public Iterable<Task> listAlerted() {

		userPrefsService.setUserTaskListStyle(TaskListStyle.ALERTED);

		return null;
	}
	@Override
	public Task get(Long id) {

		Task task = repository.findById(id).get();
		updateUnfilledSlots(task);

		return task;
	}

	@Override
	public Task save(Task task) {

		task = repository.save(task);
		updateUnfilledSlots(task);

		return task;
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<TaskUnit> availableTaskUnits() {

		return Arrays.asList(TaskUnit.values());
	}

	@Override
	public Iterable<Event> availableEvents() {

		return eventService.activeEvents();
	}

	@Override
	public void deactivate(Task task) {
		task.setActive(false);
		save(task);
	}

	@Override
	public Iterable<Commitment> commitmentsForTask(Task task) {
		return commitmentService.findByTask(task);
	}

	@Override
	public boolean isDateValid(String eventDateString) {
		try {
			Date testDate = sdf.parse(eventDateString);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	private void updateUnfilledSlots(Task task) {
		Iterable<Commitment> commitments = commitmentsForTask(task);
		int ct = 0;
		for (Commitment commitment : commitments) {
			ct++;
		}
		task.setUnfilledSlots(task.getSlots() - ct);
	}


}
