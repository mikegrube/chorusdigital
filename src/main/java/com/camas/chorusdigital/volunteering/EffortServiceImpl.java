package com.camas.chorusdigital.volunteering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EffortServiceImpl implements EffortService {
	private static final Logger log = LoggerFactory.getLogger(EffortServiceImpl.class);

	private EffortRepository repository;
	@Autowired
	public void setRepository(EffortRepository repository) {
		this.repository = repository;
	}

	private CommitmentService commitmentService;
	@Autowired
	public void setCommitmentService(CommitmentService commitmentService) {
		this.commitmentService = commitmentService;
	}

	@Override
	public Iterable<Effort> list() {
		return repository.findAll();
	}

	@Override
	public Effort get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Effort save(Effort effort) {
		return repository.save(effort);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Commitment findCommitment(Long id) {
		return commitmentService.get(id);
	}

	@Override
	public Iterable<Effort> effortsForCommitment(Commitment commitment) {
		return repository.findAllByCommitment(commitment);
	}

}
