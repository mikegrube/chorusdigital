package com.camas.chorusdigital.venue;

import org.springframework.data.repository.CrudRepository;

interface VenueRepository extends CrudRepository<Venue, Long> {

	Iterable<Venue> findAllByOrderByName();

	Venue findByName(String name);
}