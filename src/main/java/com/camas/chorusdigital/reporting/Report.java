package com.camas.chorusdigital.reporting;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.*;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.conditionalStyle.ConditionalStyle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Report {

	private static final Logger log = LoggerFactory.getLogger(Report.class);

	protected final int MARGIN = 72;

	protected static Style titleStyle;
	protected static Style subtitleStyle;
	protected static Style headerStyle;
	protected static Style footerStyle;
	protected static Style detailStyle;
	protected static Style detailDollarStyle;
	protected static Style detailNumStyle;

	static {
		titleStyle = titleStyle();
		subtitleStyle = subtitleStyle();
		headerStyle = headerStyle();
		detailStyle = detailStyle();
		detailDollarStyle = dollarStyle();
		detailNumStyle = numberStyle();
	}

	//Column types
	static final int TYPE_TEXT = 0;
	static final int TYPE_FIN = 1;
	static final int TYPE_NUM = 2;
	static final int TYPE_COND = 3;

	public Report() {
	}

	public JasperPrint getReport(Resource res) throws ColumnBuilderException, JRException, ClassNotFoundException {

		DynamicReportBuilder report = initiateReport(res);

		getColumns(report);

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report.build(), new ClassicLayoutManager(), getData());

		return jp;
	}



	//Column definition
	protected AbstractColumn createColumn(String property, Class<?> type, String title, int width, int colType, ArrayList<ConditionalStyle> customStyles)
			throws ColumnBuilderException {

		ColumnBuilder col =  ColumnBuilder.getNew()
				.setColumnProperty(property, type.getName())
				.setTitle(title)
				.setWidth(Integer.valueOf(width))
				.setHeaderStyle(headerStyle);

		switch (colType) {
			case TYPE_TEXT:
				col.setStyle(detailStyle);
				break;
			case TYPE_FIN:
				col.setStyle(detailDollarStyle);
				break;
			case TYPE_NUM:
				col.setStyle(detailNumStyle);
				break;
			case TYPE_COND:
				col.addConditionalStyles(customStyles);
				break;
			default:
				col.setStyle(detailStyle);
				break;
		}

		return col.build();
	}

	private DynamicReportBuilder initiateReport(Resource res) {

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb
				.setTitleStyle(titleStyle)
				.setTitle(getTitle())
				.setSubtitle(getSubtitle())
				.setSubtitleStyle(subtitleStyle)
				.setDetailHeight(15)
				.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
				.setPrintBackgroundOnOddRows(false)
				.setUseFullPageWidth(true)
				.addAutoText(getTitle(), AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT, 200, footerStyle)
				.addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT,200,30, footerStyle)
				.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,200,15, footerStyle);


		try {
			drb
					.addImageBanner(res.getFile().getPath(), 100, 60, ImageBanner.Alignment.Right);

		} catch (IOException ioe) {
			log.error("Logo not found; " + ioe.getMessage());
			log.error("Looking at: " + res.getFilename());
		}

		return drb;

	}

	//----- Style initialization

	private static Style titleStyle() {

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(20, null, true));

		return titleStyle.build();
	}

	private static Style subtitleStyle() {

		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(Font.MEDIUM, null, true));

		return subTitleStyle.build();
	}

	private static Style headerStyle() {

		StyleBuilder headerStyle = new StyleBuilder(true);
		headerStyle
				.setFont(Font.ARIAL_MEDIUM_BOLD)
				.setBorderBottom(Border.PEN_1_POINT())
				.setBackgroundColor(Color.blue)
				.setTextColor(Color.white)
				.setHorizontalAlign(HorizontalAlign.CENTER)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setTransparency(Transparency.OPAQUE);

		return headerStyle.build();
	}

	private static Style footerStyle() {

		StyleBuilder footerStyle = new StyleBuilder(true);
		footerStyle
				.setFont(Font.ARIAL_SMALL)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setTransparency(Transparency.OPAQUE)
				.setTextColor(Color.BLACK)
				.setHorizontalAlign(HorizontalAlign.LEFT)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setPaddingLeft(5);

		return footerStyle.build();
	}

	protected static Style detailStyle() {

		return new StyleBuilder(true)
				.setFont(Font.ARIAL_MEDIUM)
				.setBorder(Border.PEN_1_POINT())
				.setBorderColor(Color.blue)
				.setTextColor(Color.BLACK)
				.setTransparency(Transparency.OPAQUE)
				.setHorizontalAlign(HorizontalAlign.LEFT)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setPaddingLeft(5)
				.build();
	}

	private static Style dollarStyle() {
		return new StyleBuilder(true)
				.setFont(Font.ARIAL_MEDIUM)
				.setBorder(Border.PEN_1_POINT())
				.setBorderColor(Color.blue)
				.setTextColor(Color.BLACK)
				.setTransparency(Transparency.OPAQUE)
				.setHorizontalAlign(HorizontalAlign.RIGHT)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setPaddingRight(5)
				.setPattern("#,##0.00")
				.build();
	}

	private static Style numberStyle() {
		return new StyleBuilder(true)
				.setFont(Font.ARIAL_MEDIUM)
				.setBorder(Border.PEN_1_POINT())
				.setBorderColor(Color.blue)
				.setTextColor(Color.BLACK)
				.setTransparency(Transparency.OPAQUE)
				.setHorizontalAlign(HorizontalAlign.RIGHT)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setPaddingRight(5)
				.setPattern("#,##0")
				.build();
	}

	//For inheritors

	//Descendants will supply the column definitions
	abstract void getColumns(DynamicReportBuilder builder);

	//Descendants will supply the data from a list defined by the name
	abstract JRBeanCollectionDataSource getData();

	//Descendants will supply the title
	abstract String getTitle();

	//Descendants will supply the subtitle
	abstract String getSubtitle();

	public int inches(double inches) {
		int res = (int) Math.round(72.0 * inches);
		return res;
	}

}
