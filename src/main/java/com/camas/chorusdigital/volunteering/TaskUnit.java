package com.camas.chorusdigital.volunteering;

public enum TaskUnit {

	HOURS ("H"),
	ITEMS ("I"),
	DOLLARS ("D");

	private final String code;

	TaskUnit(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
