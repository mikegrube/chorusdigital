package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.season.SeasonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonSalesClassServiceImpl implements SeasonSalesClassService {
	private static final Logger log = LoggerFactory.getLogger(SeasonSalesClassServiceImpl.class);

	private SeasonSalesClassRepository repository;

	private SeasonService seasonService;
	@Autowired
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	@Autowired
	public void setRepository(SeasonSalesClassRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<SeasonSalesClass> list() {

		//TODO: List only the season for the active concert
		return repository.findAll();
	}

	@Override
	public SeasonSalesClass get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public SeasonSalesClass save(SeasonSalesClass seasonSalesClass) {
		return repository.save(seasonSalesClass);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Season getActiveSeason() {
		return seasonService.getActiveSeason();
	}

	@Override
	public Iterable<SeasonSalesClass> getActiveSeasonSalesClasses() {

		Season season = getActiveSeason();
		return repository.findBySeason(season);
	}

}
