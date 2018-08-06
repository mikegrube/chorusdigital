package com.camas.chorusdigital.volunteering;

import org.springframework.data.repository.CrudRepository;

interface EffortRepository extends CrudRepository<Effort, Long> {

	Iterable<Effort> findAllByCommitment(Commitment commitment);
}
