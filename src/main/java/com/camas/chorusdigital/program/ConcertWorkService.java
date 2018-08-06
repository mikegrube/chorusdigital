package com.camas.chorusdigital.program;

import com.camas.chorusdigital.concert.Concert;

public interface ConcertWorkService {

	Iterable<ConcertWork> list();

	ConcertWork get(Long id);

	ConcertWork save(ConcertWork concertWork);

	void delete(Long id);

	Iterable<WorkDisplay> availableWorks();

	Iterable<Concert> availableConcerts();

	Iterable<ConcertWork> concertWorksForConcert(Concert concert);

	ConcertWork findConcertWork(Concert concert, Work work);

	Iterable<ConcertWork> concertsForWork(Work work);

	Iterable<ConcertWorkTrack> tracksForConcertWork(ConcertWork concertWork);

	boolean audioExistsForConcertWork(ConcertWork concertWork);

	Concert findConcert(Long concertId);
}