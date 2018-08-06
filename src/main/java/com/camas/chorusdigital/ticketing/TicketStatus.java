package com.camas.chorusdigital.ticketing;

public enum TicketStatus {

	AVAILABLE ("V"),
	ASSIGNED ("A"),
	SOLD ("S");

	private final String code;

	TicketStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
