package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.contributor.Contributor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface ConcertContributorRepository extends CrudRepository<ConcertContributor, Long> {

	Iterable<ConcertContributor> findAllByOrderByConcertAscContributorAsc();

	Iterable<ConcertContributor> findAllByOrderByContributorAscConcertAsc();

	List<ConcertContributor> findByConcert(Concert concert);

	ConcertContributor findByConcertAndContributorAndConcertContributorRole(Concert concert, Contributor contributor, ConcertContributorRole role);

	Iterable<ConcertContributor> findByContributor(Contributor contributor);
}