package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertService;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.performance.PerformanceService;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.season.SeasonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EventServiceImpl implements EventService {
	private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

	private EventRepository repository;
	@Autowired
	public void setRepository(EventRepository repository) {
		this.repository = repository;
	}

	private SeasonService seasonService;
	@Autowired
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	private ConcertService concertService;
	@Autowired
	public void setConcertService(ConcertService concertService) {
		this.concertService = concertService;
	}

	private PerformanceService performanceService;
	@Autowired
	public void setPerformanceService(PerformanceService performanceService) {
		this.performanceService = performanceService;
	}

	@Override
	public Iterable<Event> list() {
		return repository.findAll();
	}

	@Override
	public Event get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Event save(Event event) {
		return repository.save(event);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<Event> activeEvents() {
		return repository.findByActive(true);
	}

	@Override
	public Iterable<EventType> getEventTypes() {
		return Arrays.asList(EventType.values());
	}

	@Override
	public Iterable<Season> getEventSeasons() {
		return seasonService.list();
	}

	@Override
	public Iterable<Concert> getEventConcerts() {
		return concertService.list();
	}

	@Override
	public Iterable<Performance> getEventPerformances() {
		return performanceService.list();
	}

	@Override
	public void deactivate(Event event) {
		event.setActive(false);
		save(event);
	}

}
