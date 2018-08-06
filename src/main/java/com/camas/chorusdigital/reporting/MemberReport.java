package com.camas.chorusdigital.reporting;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.camas.chorusdigital.contributor.ContributorService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class MemberReport extends Report {

	private ContributorService contributorService;

	public MemberReport(ContributorService contributorService) {
		this.contributorService = contributorService;
	}

	@Override
	void getColumns(DynamicReportBuilder builder) {

		AbstractColumn column1 = createColumn("fullName", String.class, "Name", 30, Report.TYPE_TEXT, null);
		AbstractColumn column2 = createColumn("email", String.class, "Email", 30, Report.TYPE_TEXT, null);
		AbstractColumn column3 = createColumn("homePhone", String.class, "Home Phone", 30, Report.TYPE_TEXT, null);
		AbstractColumn column4 = createColumn("mobilePhone", String.class, "Mobile Phone", 30, Report.TYPE_TEXT, null);

		builder
				.addColumn(column1)
				.addColumn(column2)
				.addColumn(column3)
				.addColumn(column4);

	}

	@Override
	JRBeanCollectionDataSource getData() {
		return new JRBeanCollectionDataSource(contributorService.getMemberRecords());
	}

	@Override
	String getTitle() {
		return "Member Report";
	}

	@Override
	String getSubtitle() {
		return "Contributors who are/have been performing members";
	}
}
