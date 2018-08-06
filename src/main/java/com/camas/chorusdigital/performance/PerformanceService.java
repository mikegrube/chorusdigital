package com.camas.chorusdigital.performance;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.venue.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PerformanceService {

	Iterable<Performance> list();

	Performance get(Long id);

	Performance save(Performance performance);

	void delete(Long id);

	Iterable<Performance> listForConcert(Concert concert);

	Iterable<Concert> availableConcerts();

	Iterable<Venue> availableVenues();

	Performance findPerformance(Concert concert, String dateTimeString);

	Iterable<Performance> performancesForVenue(Venue venue);

	Concert getConcert(Long concertId);

	Page<Performance> findAll(PageRequest dateTime);

}