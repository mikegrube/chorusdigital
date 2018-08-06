package com.camas.chorusdigital.reporting;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
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
import java.util.Date;
import java.util.Map;

public class SeasonReport extends Report {

	private static final Logger log = LoggerFactory.getLogger(SeasonReport.class);

	private SeasonService seasonService;

	public SeasonReport(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	@Override
	void getColumns(DynamicReportBuilder builder) {

		//For conditional styles
		builder.addField("verified", Boolean.class.getName());

		AbstractColumn column1 = createColumn("season", String.class, "Season", 60, Report.TYPE_TEXT, null);
		AbstractColumn column2 = createColumn("concert", String.class, "Concert", 60, Report.TYPE_TEXT, null);
		AbstractColumn column3 = createColumn("firstConcertDate", Date.class, "First Performance", 60, Report.TYPE_COND, createConditionalStyles(detailStyle));

		builder
				.addColumn(column1)
				.addColumn(column2)
				.addColumn(column3);

		DJGroup g1 = new GroupBuilder().setCriteriaColumn((PropertyColumn) column1).build();
		builder.addGroup(g1);

	}

	@Override
	JRBeanCollectionDataSource getData() {
		return new JRBeanCollectionDataSource(seasonService.getSeasonRecords());
	}

	@Override
	String getTitle() {
		return "Season Report";
	}

	@Override
	String getSubtitle() {
		return "Seasons and Concerts";
	}

	private ArrayList<ConditionalStyle> createConditionalStyles(Style baseStyle) {

		ArrayList<ConditionalStyle> conditionalStyles = new ArrayList<>();

		Style style0 = detailStyle();
		style0.setBackgroundColor(Color.GREEN);
		style0.setTransparency(Transparency.OPAQUE);
		Style style1 = detailStyle();
		style1.setBackgroundColor(Color.RED);
		style1.setTransparency(Transparency.OPAQUE);

		VerifiedCondition status0 = new VerifiedCondition(true);
		VerifiedCondition status1 = new VerifiedCondition(false);

		ConditionalStyle condition0 = new ConditionalStyle(status0, style0);
		ConditionalStyle condition1 = new ConditionalStyle(status1, style1);

		conditionalStyles.add(condition0);
		conditionalStyles.add(condition1);

		return conditionalStyles;
	}

	private class VerifiedCondition extends ConditionStyleExpression implements CustomExpression {

		Boolean validity;

		public VerifiedCondition(Boolean validity) {
			this.validity = validity;
		}

		public Object evaluate(Map fields, Map variables, Map parameters) {
			Boolean ver = (Boolean) fields.get("verified");
			String concert = (String) fields.get("concert");
			Boolean res = ver == validity;
			//log.info("Comparison for " + concert + ": " + res);
			return res;
		}

		public String getClassName() {
			return Boolean.class.getName();
		}
	}
}