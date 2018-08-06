package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.Concert;
import org.springframework.data.repository.CrudRepository;

interface ConcertSalesClassRepository extends CrudRepository<ConcertSalesClass, Long> {

	ConcertSalesClass findByConcertAndName(Concert concert, String name);

	Iterable<ConcertSalesClass> findByConcert(Concert concert);
}

