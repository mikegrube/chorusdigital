package com.camas.chorusdigital.contributor;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertContributorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class ContributorServiceImpl implements ContributorService {
	private static final Logger log = LoggerFactory.getLogger(ContributorServiceImpl.class);

	private ContributorRepository repository;

	@Autowired
	public void setRepository(ContributorRepository repository) {
		this.repository = repository;
	}

	private ConcertContributorService concertContributorService;
	@Autowired
	public void setConcertContributorService(ConcertContributorService concertContributorService) {
		this.concertContributorService = concertContributorService;
	}

	@Override
	public Iterable<Contributor> list() {
		return repository.findAllByOrderByLastNameAscFirstNameAsc();
	}

	@Override
	public Contributor get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Contributor save(Contributor contributor) {
		return repository.save(contributor);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<Section> availableSections() {

		return Arrays.asList(Section.values());
	}

	@Override
	public Contributor findContributor(String firstName, String lastName) {
		return repository.findByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public Iterable<Concert> concertsForContributor(Contributor contributor) {
		return concertContributorService.concertsForContributor(contributor);
	}

	@Override
	public Contributor findBank() {

		Contributor bank = repository.findByLastName(Contributor.getBankName());
		if (bank == null) {
			bank = new Contributor();
			bank.setLastName(Contributor.getBankName());
			bank.setFirstName("");
			repository.save(bank);
		}

		return bank;

	}

	@Override
	public Contributor findUnknown() {

		Contributor unknown = repository.findByLastName(Contributor.getUnknownName());
		if (unknown == null) {
			unknown = new Contributor();
			unknown.setLastName(Contributor.getUnknownName());
			unknown.setFirstName("");
			repository.save(unknown);
		}

		return unknown;

	}

	@Override
	public Page<Contributor> findAll(PageRequest pageRequest) {

		return repository.findAll(pageRequest);
	}

	@Override
	public Page<Contributor> findAllByMember(boolean member, PageRequest pageRequest) {

		return repository.findAllByMember(member, pageRequest);
	}

	//For reporting
	@Override
	public Collection<MemberReportRow> getMemberRecords() {

		List<MemberReportRow> records = new ArrayList<>();

		Iterable <Contributor> contributors = repository.findAllByOrderByLastNameAscFirstNameAsc();
		for (Contributor contributor : contributors) {
			if (contributor.isMember()) {
				MemberReportRow memberReportRow = new MemberReportRow();
				memberReportRow.setFullName(contributor.getName());
				memberReportRow.setEmail(contributor.getEmail());
				memberReportRow.setHomePhone(contributor.getHomePhone());
				memberReportRow.setMobilePhone(contributor.getMobilePhone());
				records.add(memberReportRow);
			}
		}

		return records;
	}

}
