package com.camas.chorusdigital.volunteering;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
public class Effort {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Commitment commitment;

	@NotNull
	@Digits(integer=3, fraction = 0)
	private Integer hours;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Commitment getCommitment() {
		return commitment;
	}

	public void setCommitment(Commitment commitment) {
		this.commitment = commitment;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}
}
