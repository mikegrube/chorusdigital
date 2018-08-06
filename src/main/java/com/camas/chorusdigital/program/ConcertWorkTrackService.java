package com.camas.chorusdigital.program;

import com.camas.chorusdigital.concert.Concert;

public interface ConcertWorkTrackService {

	Iterable<ConcertWorkTrack> list();

	ConcertWorkTrack get(Long id);

	ConcertWorkTrack save(ConcertWorkTrack concertWorkTrack);

	void delete(Long id);

	Iterable<ConcertWorkTrack> concertWorkTracksForConcertWork(ConcertWork concertWork);

	ConcertWorkTrack findConcertWorkTrack(ConcertWork concetWork, String title);

	Object availableConcertWorks();

	ConcertWork findConcertWork(Long concertWorkId);

	Object audioFilesForConcert(Concert concert);
}