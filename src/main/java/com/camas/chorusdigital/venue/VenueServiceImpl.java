package com.camas.chorusdigital.venue;

import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.performance.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueServiceImpl implements VenueService {

	private VenueRepository repository;

	private PerformanceService performanceService;
	@Autowired
	public void setPerformanceService(PerformanceService performanceService) {
		this.performanceService = performanceService;
	}

	@Autowired
	public void setRepository(VenueRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Venue> list() {
		return repository.findAllByOrderByName();
	}

	@Override
	public Venue get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Venue save(Venue venue) {
		return repository.save(venue);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Venue findVenue(String name) {
		return repository.findByName(name);
	}

	@Override
	public Iterable<Performance> performancesForVenue(Venue venue) {

		return performanceService.performancesForVenue(venue);
	}

}
