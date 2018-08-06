package com.camas.chorusdigital.program;

import org.springframework.data.repository.CrudRepository;

interface ConcertWorkTrackRepository extends CrudRepository<ConcertWorkTrack, Long> {

	Iterable<ConcertWorkTrack> findByConcertWorkOrderByTrackAsc(ConcertWork concertWork);

	ConcertWorkTrack findByConcertWorkAndTitle(ConcertWork concertWork, String title);
}