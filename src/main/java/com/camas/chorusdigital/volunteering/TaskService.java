package com.camas.chorusdigital.volunteering;

public interface TaskService {

	Iterable<Task> list();

	Iterable<Task> listAll();

	Iterable<Task> listActive();

	Iterable<Task> listForVolunteer();

	Iterable<Task> listAlerted();

	Task get(Long id);

	Task save(Task task);

	void delete(Long id);

	Iterable<TaskUnit> availableTaskUnits();

	Iterable<Event> availableEvents();

	void deactivate(Task task);

	Iterable<Commitment> commitmentsForTask(Task task);

	boolean isDateValid(String eventDateString);

	Iterable<Task> listAvailable();
}
