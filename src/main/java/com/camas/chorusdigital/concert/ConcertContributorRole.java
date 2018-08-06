package com.camas.chorusdigital.concert;

/**
 * Created by mike on 8/22/17.
 */
public enum ConcertContributorRole {

	MEMBER ("M"),
	DIRECTOR ("D"),
	ACCOMPANIST ("A"),
	SOLOIST ("S"),
	NARRATOR ("N"),
	DANCER ("R"),
	INSTRUMENTALIST ("I"),
	GROUP("G");

	private final String code;

	ConcertContributorRole(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
