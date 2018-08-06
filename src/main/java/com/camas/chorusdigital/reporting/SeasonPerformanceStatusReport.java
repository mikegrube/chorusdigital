package com.camas.chorusdigital.reporting;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionStyleExpression;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import com.camas.chorusdigital.season.SeasonService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class SeasonPerformanceStatusReport extends Report {
	private static final Logger log = LoggerFactory.getLogger(SeasonPerformanceStatusReport.class);

	private SeasonPerformanceStatusService service;

	public SeasonPerformanceStatusReport(SeasonPerformanceStatusService service) {
		this.service = service;
	}

	@Override
	void getColumns(DynamicReportBuilder builder) {

		ArrayList<ConditionalStyle> conditionalStyles = createConditionalStyles();

		AbstractColumn column1 = createColumn("season", String.class, "Season", inches(2.0), Report.TYPE_TEXT, null);
		AbstractColumn column2 = createColumn("concertTitle", String.class, "Concert", inches(2.0), Report.TYPE_TEXT, null);
		AbstractColumn column3 = createColumn("performanceDate", Date.class, "Performance Date", inches(1.0), Report.TYPE_TEXT, null);
		AbstractColumn column4 = createColumn("venueName", String.class, "Venue", inches(1.5), Report.TYPE_TEXT, null);
		AbstractColumn column5 = createColumn("concertVerified", Boolean.class, "Concert Ver", inches(1.0), Report.TYPE_COND, conditionalStyles);
		AbstractColumn column6 = createColumn("concertVideoExists", Boolean.class, "Concert Vid", inches(1.0), Report.TYPE_COND, conditionalStyles);
		AbstractColumn column7 = createColumn("concertPrefixOk", Boolean.class, "Concert Pref", inches(1.0), Report.TYPE_COND, conditionalStyles);
		AbstractColumn column8 = createColumn("performanceDateVerified", Boolean.class, "Perf Date Ver", inches(1.0), Report.TYPE_COND, conditionalStyles);
		AbstractColumn column9 = createColumn("venueVerified", Boolean.class, "Venue Ver", inches(1.0), Report.TYPE_COND, conditionalStyles);

		GroupBuilder gb1 = new GroupBuilder();
		DJGroup seasonGroup = gb1.setCriteriaColumn((PropertyColumn) column1)
				.build();

		GroupBuilder gb2 = new GroupBuilder();
		DJGroup concertGroup = gb2.setCriteriaColumn((PropertyColumn) column2)
				.build();

		try {
			builder
					.addColumn(column1)
					.addColumn(column2)
					.addColumn(column3)
					.addColumn(column4)
					.addColumn(column5)
					.addColumn(column6)
					.addColumn(column7)
					.addColumn(column8)
					.addColumn(column9)
					.addGroup(seasonGroup)
					.addGroup(concertGroup)
					.setPageSizeAndOrientation(Page.Page_Letter_Landscape());

		} catch (Exception e) {
			log.error("Error in build code.");
		}
	}

	private ArrayList<ConditionalStyle> createConditionalStyles() {

		ArrayList<ConditionalStyle> conditionalStyles = new ArrayList<>();

		Style style0 = new Style();
		style0.setBackgroundColor(Color.GREEN);
		style0.setTransparency(Transparency.OPAQUE);

		Style style1 = new Style();
		style1.setBackgroundColor(Color.RED);
		style1.setTransparency(Transparency.OPAQUE);

		SeasonPerformanceStatusReport.VerifiedCondition status0 = new SeasonPerformanceStatusReport.VerifiedCondition(true);
		SeasonPerformanceStatusReport.VerifiedCondition status1 = new SeasonPerformanceStatusReport.VerifiedCondition(false);

		ConditionalStyle condition0 = new ConditionalStyle(status0, style0);
		ConditionalStyle condition1 = new ConditionalStyle(status1, style1);

		conditionalStyles.add(condition0);
		conditionalStyles.add(condition1);

		return conditionalStyles;
	}

	private class VerifiedCondition extends ConditionStyleExpression implements CustomExpression {

		private boolean state;

		public VerifiedCondition(boolean state) {
			this.state = state;
		}

		public Object evaluate(Map fields, Map variables, Map parameters) {

			return (Boolean) getCurrentValue() == state;
		}

		public String getClassName() {
			return Boolean.class.getName();
		}
	}

	@Override
	JRBeanCollectionDataSource getData() {
		return new JRBeanCollectionDataSource(service.getSeasonPerformanceStatusInfos());
	}

	@Override
	String getTitle() {
		return "Season Status Report";
	}

	@Override
	String getSubtitle() {
		return "Season Consistency";
	}
}
