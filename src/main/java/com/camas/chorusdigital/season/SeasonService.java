package com.camas.chorusdigital.season;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.reporting.SeasonInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.List;

public interface SeasonService {

	Iterable<Season> list();

	Season get(Long id);

	Season save(Season season);

	void delete(Long id);

	Iterable<Concert> concertsForSeason(Season season);

	Season findSeason(String startYear);

	Season getActiveSeason();

	Page<Season> findAll(PageRequest title);

	Season getNextSeason();

	List<SeasonReportRow> getSeasonRecords();

	List<SeasonInfo> getSeasonInfoRecords();
}