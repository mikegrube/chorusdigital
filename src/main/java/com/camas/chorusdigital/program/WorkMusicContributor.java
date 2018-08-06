package com.camas.chorusdigital.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
public class WorkMusicContributor {
	private static final Logger log = LoggerFactory.getLogger(ConcertWorkTrack.class);

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Work work;

	@ManyToOne
	private MusicContributor musicContributor;

	@Enumerated(EnumType.STRING)
	private WorkMusicContributorRole workMusicContributorRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public MusicContributor getMusicContributor() {
		return musicContributor;
	}

	public void setMusicContributor(MusicContributor musicContributor) {
		this.musicContributor = musicContributor;
	}

	public WorkMusicContributorRole getWorkMusicContributorRole() {
		return workMusicContributorRole;
	}

	public void setWorkMusicContributorRole(WorkMusicContributorRole workMusicContributorRole) {
		this.workMusicContributorRole = workMusicContributorRole;
	}
}
