package com.camas.chorusdigital.venue;

import com.camas.chorusdigital.performance.Performance;

import java.util.List;

public interface VenueService {

	Iterable<Venue> list();

	Venue get(Long id);

	Venue save(Venue venue);

	void delete(Long id);

	Venue findVenue(String name);

	Iterable<Performance> performancesForVenue(Venue venue);
}