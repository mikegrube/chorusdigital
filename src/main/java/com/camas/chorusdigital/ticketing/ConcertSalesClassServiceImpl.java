package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConcertSalesClassServiceImpl implements ConcertSalesClassService {
	private static final Logger log = LoggerFactory.getLogger(ConcertSalesClassServiceImpl.class);

	private ConcertSalesClassRepository repository;

	private ConcertService concertService;
	@Autowired
	public void setService(ConcertService concertService) {
		this.concertService = concertService;
	}

	@Autowired
	public void setRepository(ConcertSalesClassRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<ConcertSalesClass> list() {
		return repository.findAll();
	}

	@Override
	public ConcertSalesClass get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public ConcertSalesClass save(ConcertSalesClass concertSalesClass) {
		return repository.save(concertSalesClass);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public ConcertSalesClass findUnassigned(Concert concert) {

		ConcertSalesClass unknown = repository.findByConcertAndName(concert, ConcertSalesClass.getUnknownClass());
		if (unknown == null) {
			unknown = new ConcertSalesClass();
			unknown.setName(ConcertSalesClass.getUnknownClass());
			unknown.setConcert(concert);
			repository.save(unknown);
		}

		return unknown;
	}

	@Override
	public Concert getActiveConcert() {
		return concertService.getActiveConcert();
	}

	@Override
	public Iterable<ConcertSalesClass> getActiveConcertSalesClasses() {
		Concert concert = getActiveConcert();
		return repository.findByConcert(concert);
	}

}
