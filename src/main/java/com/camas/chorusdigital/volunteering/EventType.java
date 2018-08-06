package com.camas.chorusdigital.volunteering;

public enum EventType {

	SEASON ("S"),
	CONCERT ("C"),
	PERFORMANCE ("P"),
	FUNDRAISER ("F"),
	CONTINUING ("G"),
	OTHER ("O")
	;

	private final String code;

	EventType (String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
