package com.camas.chorusdigital.contributor;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertContributorRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface ContributorService {

	Iterable<Contributor> list();

	Contributor get(Long id);

	Contributor save(Contributor contributor);

	void delete(Long id);

	Iterable<Section> availableSections();

	Contributor findContributor(String firstName, String lastName);

	Iterable<Concert> concertsForContributor(Contributor contributor);

	Contributor findBank();

	Contributor findUnknown();

	Page<Contributor> findAll(PageRequest name);

	Page<Contributor> findAllByMember(boolean member, PageRequest name);

	Collection<?> getMemberRecords();
}