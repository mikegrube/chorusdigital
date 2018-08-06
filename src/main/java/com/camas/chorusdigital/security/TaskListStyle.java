package com.camas.chorusdigital.security;

public enum TaskListStyle {

	ALL ("A"),
	ACTIVE ("V"),
	MINE ("M"),
	ALERTED ("L");

	private final String code;

	TaskListStyle(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}


}
