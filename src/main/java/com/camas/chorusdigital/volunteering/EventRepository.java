package com.camas.chorusdigital.volunteering;

import org.springframework.data.repository.CrudRepository;

interface EventRepository extends CrudRepository<Event, Long> {

	Iterable<Event> findByActive(boolean b);
}

