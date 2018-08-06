package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.contributor.Contributor;

public interface ConcertContributorService {

	Iterable<ConcertContributor> list();

	ConcertContributor get(Long id);

	ConcertContributor save(ConcertContributor concertContributor);

	void delete(Long id);

	Iterable<Contributor> availableContributors();

	Iterable<Concert> availableConcerts();

	Iterable<ConcertContributorRole> availableConcertContributorRoles();

	Iterable<ConcertContributor> concertContributorsForConcert(Concert concert);

	ConcertContributor findConcertContributor(Concert concert, Contributor contributor, ConcertContributorRole role);

	Iterable<Concert> concertsForContributor(Contributor contributor);

	Concert findConcert(Long id);

	Iterable<ConcertContributor> getActiveConcertContributors();
}