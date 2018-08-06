package com.camas.chorusdigital.program;

import java.util.List;

import com.camas.chorusdigital.concert.Concert;
import org.springframework.data.repository.CrudRepository;

interface ConcertWorkRepository extends CrudRepository<ConcertWork, Long> {

	List<ConcertWork> findByConcertOrderByTrackAsc(Concert concert);

	ConcertWork findByConcertAndWork(Concert concert, Work work);

	List<ConcertWork> findByWork(Work work);

}