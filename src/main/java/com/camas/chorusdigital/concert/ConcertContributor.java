package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.contributor.Contributor;

import javax.persistence.*;

@Entity
public class ConcertContributor {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Concert concert;

	@ManyToOne
	private Contributor contributor;

	@Enumerated(EnumType.STRING)
	private ConcertContributorRole concertContributorRole;

	public ConcertContributor() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}

	public Contributor getContributor() {
		return contributor;
	}

	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

	public ConcertContributorRole getConcertContributorRole() {
		return concertContributorRole;
	}

	public void setConcertContributorRole(ConcertContributorRole concertContributorRole) {
		this.concertContributorRole = concertContributorRole;
	}
}
