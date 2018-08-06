package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.contributor.Contributor;
import com.camas.chorusdigital.contributor.ContributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ConcertContributorServiceImpl implements ConcertContributorService {

	private ConcertContributorRepository repository;
	@Autowired
	public void setRepository(ConcertContributorRepository repository) {
		this.repository = repository;
	}

	private ContributorService contributorService;
	@Autowired
	public void setContributorService(ContributorService contributorService) {
		this.contributorService = contributorService;
	}

	private ConcertService concertService;
	@Autowired
	public void setConcertService(ConcertService concertService) {
		this.concertService = concertService;
	}

	@Override
	public Iterable<ConcertContributor> list() { return list(true); }

	public Iterable<ConcertContributor> list(boolean byConcert) {
		if (byConcert) {
			return repository.findAllByOrderByConcertAscContributorAsc();
		} else {
			return repository.findAllByOrderByContributorAscConcertAsc();
		}
	}

	@Override
	public ConcertContributor get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public ConcertContributor save(ConcertContributor concertContributor) {
		return repository.save(concertContributor);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<Contributor> availableContributors() {
		return contributorService.list();
	}

	@Override
	public Iterable<Concert> availableConcerts() {
		return concertService.list();
	}

	@Override
	public Iterable<ConcertContributorRole> availableConcertContributorRoles() {

		return Arrays.asList(ConcertContributorRole.values());
	}

	@Override
	public Iterable<ConcertContributor> concertContributorsForConcert(Concert concert) {
		return repository.findByConcert(concert);
	}

	@Override
	public ConcertContributor findConcertContributor(Concert concert, Contributor contributor, ConcertContributorRole role) {
		return repository.findByConcertAndContributorAndConcertContributorRole(concert, contributor, role);
	}

	@Override
	public Iterable<Concert> concertsForContributor(Contributor contributor) {
		List<Concert> concerts = new ArrayList<>();
		Iterable<ConcertContributor> concertContributors = repository.findByContributor(contributor);
		for (ConcertContributor concertContributor : concertContributors) {
			concerts.add(concertContributor.getConcert());
		}
		return concerts;
	}

	@Override
	public Concert findConcert(Long concertId) {
		return concertService.get(concertId);
	}

	@Override
	public Iterable<ConcertContributor> getActiveConcertContributors() {

		Concert concert = concertService.getActiveConcert();
		return repository.findByConcert(concert);
	}

}

