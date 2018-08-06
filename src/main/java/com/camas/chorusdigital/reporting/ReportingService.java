package com.camas.chorusdigital.reporting;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;

public interface ReportingService {

	public Iterable<ReportRequest> list();

	public byte[] getReportPdf(final JasperPrint jp) throws JRException;

	public byte[] generateReport(String title) throws JRException, ClassNotFoundException;
}
