package com.camas.chorusdigital.contributor;

/**
 * Created by mike on 8/22/17.
 */
public enum Section {

	SOPRANO ("S"),
	ALTO ("A"),
	TENOR ("T"),
	BASS ("B");

	private final String code;

	Section(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
