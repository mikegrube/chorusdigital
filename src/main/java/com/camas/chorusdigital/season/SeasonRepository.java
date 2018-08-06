package com.camas.chorusdigital.season;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SeasonRepository extends PagingAndSortingRepository<Season, Long> {

	Iterable<Season> findAllByOrderByStartYear();

	Season findByStartYear(String string);

}