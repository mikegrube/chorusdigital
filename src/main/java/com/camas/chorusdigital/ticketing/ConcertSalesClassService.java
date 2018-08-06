package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.Concert;

public interface ConcertSalesClassService {

	Iterable<ConcertSalesClass> list();

	ConcertSalesClass get(Long id);

	ConcertSalesClass save(ConcertSalesClass concertSalesClass);

	void delete(Long id);

	ConcertSalesClass findUnassigned(Concert concert);

	Concert getActiveConcert();

	Iterable<ConcertSalesClass> getActiveConcertSalesClasses();
}
