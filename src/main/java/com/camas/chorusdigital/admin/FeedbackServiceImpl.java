package com.camas.chorusdigital.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private FeedbackRepository repository;

	@Autowired
	public void setRepository(FeedbackRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Feedback> list() {
		return repository.findAll();
	}

	@Override
	public Feedback get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Feedback save(Feedback feedback) {
		return repository.save(feedback);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}