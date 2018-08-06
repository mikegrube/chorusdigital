package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.season.Season;

public interface EventService {

	Iterable<Event> list();

	Event get(Long id);

	Event save(Event event);

	void delete(Long id);

	Iterable<Event> activeEvents();

	Iterable<EventType> getEventTypes();

	Iterable<Season> getEventSeasons();

	Iterable<Concert> getEventConcerts();

	Iterable<Performance> getEventPerformances();

	void deactivate(Event event);

}
