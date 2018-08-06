package com.camas.chorusdigital.concert;

import java.util.List;

import com.camas.chorusdigital.season.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConcertRepository extends PagingAndSortingRepository<Concert, Long> {

	Iterable<Concert> findAllByOrderByTitleAsc();

	Concert findByActive(boolean active);

	List<Concert> findBySeason(Season season);

	Concert findBySeasonAndTitle(Season season, String title);

	Iterable<Concert> findAllByTitleOrderByTitleAsc(String title);

	Iterable<Concert> findAllByTitleContainingIgnoreCase(String titlePart);

	Iterable<Concert> findAllByOrderBySeasonStartYearAscTitleAsc();
}