package com.camas.chorusdigital.admin;

public interface FeedbackService {

	Iterable<Feedback> list();

	Feedback get(Long id);

	Feedback save(Feedback feedback);

	void delete(Long id);
}
