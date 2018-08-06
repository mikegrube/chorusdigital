package com.camas.chorusdigital.program;

public enum WorkMusicContributorRole {

	COMPOSER ("C"),
	COCOMPOSER ("O"),
	ARRANGER ("A"),
	ADAPTER ("R"),
	LYRICIST ("L");

	private final String code;

	WorkMusicContributorRole(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
