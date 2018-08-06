package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.season.Season;

public interface SeasonSalesClassService {

	Iterable<SeasonSalesClass> list();

	SeasonSalesClass get(Long id);

	SeasonSalesClass save(SeasonSalesClass seasonSalesClass);

	void delete(Long id);

	Season getActiveSeason();

	Iterable<SeasonSalesClass> getActiveSeasonSalesClasses();
}
