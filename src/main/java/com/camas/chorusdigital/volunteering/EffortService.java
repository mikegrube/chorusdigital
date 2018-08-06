package com.camas.chorusdigital.volunteering;

public interface EffortService {

	Iterable<Effort> list();

	Effort get(Long id);

	Effort save(Effort effort);

	void delete(Long id);

	Commitment findCommitment(Long id);

	Iterable<Effort> effortsForCommitment(Commitment commitment);
}
