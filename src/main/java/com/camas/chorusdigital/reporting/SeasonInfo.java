package com.camas.chorusdigital.reporting;

import java.util.ArrayList;
import java.util.List;

public class SeasonInfo {

	private String season;
	private List<ConcertInfo> concertInfoList = new ArrayList<>();

	private int concertCt = 0;
	private int concertCl = 0;

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season.trim();
	}

	public List<ConcertInfo> getConcertInfoList() {
		return concertInfoList;
	}

	public void setConcertInfoList(List<ConcertInfo> concertInfoList) {
		this.concertInfoList = concertInfoList;
	}

	public int getConcertCt() {
		return concertCt;
	}

	public void setConcertCt(int concertCt) {
		this.concertCt = concertCt;
	}

	public int getConcertCl() {
		return concertCl;
	}

	public void setConcertCl(int concertCl) {
		this.concertCl = concertCl;
	}

	public void addConcertInfo(ConcertInfo concertInfo) {
		concertInfoList.add(concertInfo);
	}

	public String getConcertStatus() {
		return concertCl + "/" + concertCt;
	}
}
