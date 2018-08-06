package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.season.Season;
import org.springframework.data.repository.CrudRepository;

interface SeasonSalesClassRepository extends CrudRepository<SeasonSalesClass, Long> {

	Iterable<SeasonSalesClass> findBySeason(Season season);
}

