package com.camas.chorusdigital.contributor;

import com.camas.chorusdigital.concert.ConcertContributorRole;

import javax.persistence.*;

/**
 * Created by mike on 8/22/17.
 */
@Entity
public class Contributor {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;
	private String lastName;

	private String email;
	private String homePhone;
	private String mobilePhone;
	private boolean member;

	@Enumerated(EnumType.STRING)
	private Section section;

	private static String bankName = "UNASSIGNED";
	private static String unknownName = "UNKNOWN";

	public Contributor() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(String idString) {
		this.id = Long.parseLong(idString);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public static String getBankName() {
		return bankName;
	}

	public static void setBankName(String bankName) {
		Contributor.bankName = bankName;
	}

	public static String getUnknownName() {
		return unknownName;
	}

	public static void setUnknownName(String unknownName) {
		Contributor.unknownName = unknownName;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

}

