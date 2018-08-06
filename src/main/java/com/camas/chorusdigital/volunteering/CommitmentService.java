package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.security.User;

public interface CommitmentService {

	Iterable<Commitment> list();

	Commitment get(Long id);

	Commitment save(Commitment commitment);

	void delete(Long id);

	Task findTask(Long taskId);

	User findUserByUserName(String userName);

	Iterable<Commitment> findByTask(Task task);

	User findCurrentUser();

	Iterable<Commitment> commitmentsForCurrentUser();

	Iterable<Effort> findEffortsForCommitment(Commitment commitment);

	Iterable<User> findAvailableVolunteers();
}
