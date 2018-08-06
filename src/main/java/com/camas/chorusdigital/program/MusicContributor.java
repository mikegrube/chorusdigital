package com.camas.chorusdigital.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class MusicContributor {
	private static final Logger log = LoggerFactory.getLogger(MusicContributor.class);

	@Id
	@GeneratedValue
	private Long id;

	String firstName;
	String lastName;
	Integer born;
	Integer died;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {

		return lastName + ", " + firstName;
	}


	public Integer getBorn() {
		return born;
	}

	public void setBorn(Integer born) {
		this.born = born;
	}

	public Integer getDied() {
		return died;
	}

	public void setDied(Integer died) {
		this.died = died;
	}

	public String getYears() {
		if (born != null) {
			if (died != null) {
				return "(" + born + "-" + died + ")";
			} else {
				return "(" + born + "- )";
			}
		} else {
			return "( ? )";
		}
	}

}
