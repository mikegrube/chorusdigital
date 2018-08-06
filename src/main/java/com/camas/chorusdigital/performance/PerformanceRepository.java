package com.camas.chorusdigital.performance;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.venue.Venue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

interface PerformanceRepository extends PagingAndSortingRepository<Performance, Long> {

	List<Performance> findAllByConcertOrderByDateTimeAsc(Concert concert);

	Performance findByConcertAndDateTime(Concert concert, Date dateTime);

	Iterable<Performance> findByVenue(Venue venue);
}