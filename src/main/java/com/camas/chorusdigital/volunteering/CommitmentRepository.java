package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.security.User;
import org.springframework.data.repository.CrudRepository;

interface CommitmentRepository extends CrudRepository<Commitment, Long> {

	Iterable<Commitment> findAllByTask(Task task);

	Iterable<Commitment> findAllByUser(User currentUser);
}
