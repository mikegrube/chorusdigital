package com.camas.chorusdigital.reporting;

import java.util.ArrayList;
import java.util.List;

public class ConcertInfo {

	private String title;
	private String prefix;
	private List<PerformanceInfo> performanceInfoList = new ArrayList<>();
	private List<WorkInfo> workInfoList = new ArrayList<>();

	private int performanceCt = 0;
	private boolean consecutivePrefix = false;
	private boolean worksExist = true;
	private boolean videoExists = false;
	private boolean nonGroupWorksExist = false;
	private boolean memberListExists = true;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<PerformanceInfo> getPerformanceInfoList() {
		return performanceInfoList;
	}

	public void setPerformanceInfoList(List<PerformanceInfo> performanceInfoList) {
		this.performanceInfoList = performanceInfoList;
	}

	public List<WorkInfo> getWorkInfoList() {
		return workInfoList;
	}

	public void setWorkInfoList(List<WorkInfo> workInfoList) {
		this.workInfoList = workInfoList;
	}

	public boolean isConsecutivePrefix() {
		return consecutivePrefix;
	}

	public void setConsecutivePrefix(boolean consecutivePrefix) {
		this.consecutivePrefix = consecutivePrefix;
	}

	public boolean isWorksExist() {
		return worksExist;
	}

	public void setWorksExist(boolean worksExist) {
		this.worksExist = worksExist;
	}

	public boolean isVideoExists() {
		return videoExists;
	}

	public void setVideoExists(boolean videoExists) {
		this.videoExists = videoExists;
	}

	public boolean isNonGroupWorksExist() {
		return nonGroupWorksExist;
	}

	public void setNonGroupWorksExist(boolean nonGroupWorksExist) {
		this.nonGroupWorksExist = nonGroupWorksExist;
	}

	public boolean isMemberListExists() {
		return memberListExists;
	}

	public void setMemberListExists(boolean memberListExists) {
		this.memberListExists = memberListExists;
	}

	public void addPerformanceInfo(PerformanceInfo performanceInfo) {
		performanceInfoList.add(performanceInfo);
	}

	public int getPerformanceCt() {
		return performanceCt;
	}

	public void setPerformanceCt(int performanceCt) {
		this.performanceCt = performanceCt;
	}

	public boolean isClean() {
		boolean clean = true;
		if (performanceCt == 0 || !worksExist || !memberListExists) {
			clean = false;
		}

		return clean;
	}

	public String getPrefixStatus() {
		return prefix + (isConsecutivePrefix() ? "(cons)" : "");
	}

}
