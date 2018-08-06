package com.camas.chorusdigital.performance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertService;
import com.camas.chorusdigital.venue.Venue;
import com.camas.chorusdigital.venue.VenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PerformanceServiceImpl implements PerformanceService {
	private static final Logger log = LoggerFactory.getLogger(PerformanceServiceImpl.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	//Error cache
	private String flash = null;

	private PerformanceRepository repository;
	@Autowired
	public void setRepository(PerformanceRepository repository) {
		this.repository = repository;
	}

	private ConcertService concertService;
	@Autowired
	public void setService(ConcertService concertService) {
		this.concertService = concertService;
	}

	private VenueService venueService;
	@Autowired
	public void setService(VenueService venueService) {
		this.venueService = venueService;
	}

	@Override
	public Iterable<Performance> list() {
		return repository.findAll();
	}

	@Override
	public Performance get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Performance save(Performance performance) {
		return repository.save(performance);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Performance> listForConcert(Concert concert) {

		return repository.findAllByConcertOrderByDateTimeAsc(concert);

	}

	@Override
	public Iterable<Concert> availableConcerts() {
		return concertService.list();
	}

	@Override
	public Iterable<Venue> availableVenues() {
		return venueService.list();
	}

	@Override
	public Performance findPerformance(Concert concert, String dateTimeString) {
		Date dateTime = null;
		try {
			dateTime = sdf.parse(dateTimeString);
		} catch (ParseException e) {
			log.error("Unable to parse date: " + dateTimeString);
		}

		return repository.findByConcertAndDateTime(concert, dateTime);
	}

	@Override
	public Iterable<Performance> performancesForVenue(Venue venue) {
		return repository.findByVenue(venue);
	}

	@Override
	public Concert getConcert(Long concertId) {
		return concertService.get(concertId);
	}

	@Override
	public Page<Performance> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

}
