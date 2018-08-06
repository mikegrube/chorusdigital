package com.camas.chorusdigital.reporting;

import com.camas.chorusdigital.concert.ConcertService;
import com.camas.chorusdigital.contributor.ContributorService;
import com.camas.chorusdigital.season.SeasonService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {

	private static final Logger log = LoggerFactory.getLogger(ReportingServiceImpl.class);

	@Value("${repository.path}")
	private String repositoryPath;

	private ConcertService concertService;
	@Autowired
	public void setConcertService(ConcertService concertService) {
		this.concertService = concertService;
	}

	private ContributorService contributorService;
	@Autowired
	public void setContributorService(ContributorService contributorService) {
		this.contributorService = contributorService;
	}

	private SeasonService seasonService;
	@Autowired
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	private SeasonPerformanceStatusService seasonPerformanceStatusService;
	@Autowired
	public void setSeasonPerformanceStatusService(SeasonPerformanceStatusService seasonPerformanceStatusService) {
		this.seasonPerformanceStatusService = seasonPerformanceStatusService;
	}

	@Override
	public Iterable<ReportRequest> list() {

		List<ReportRequest> reports = new ArrayList<>();

		reports.add(new ReportRequest("Member", "A report containing the list of past and present performing members."));
		reports.add(new ReportRequest("Concert", "A report containing a list of all concerts by season."));
		reports.add(new ReportRequest("Season", "A report containing a list of seasons highlighted by verification."));
		reports.add(new ReportRequest("Season Performance Content Status", "A report showing the current status of the chorus content"));

		return reports;
	}

	public byte[] getReportPdf(final JasperPrint jp) throws JRException {
		return JasperExportManager.exportReportToPdf(jp);
	}

	@Override
	public byte[] generateReport(String title) throws JRException, ClassNotFoundException {

		Report report = null;
		switch (title) {
			case "Member":
				report = new MemberReport(contributorService);
				break;
			case "Concert":
				report = new ConcertReport(concertService);
				break;
			case "Season":
				report = new SeasonReport(seasonService);
				break;
			case "Season Performance Content Status":
				report = new SeasonPerformanceStatusReport(seasonPerformanceStatusService);
				break;
			default:
				break;

		}

		if (report != null) {
			Resource res = new FileSystemResource(repositoryPath + "logo.png");
			return getReportPdf(report.getReport(res));
		} else {
			throw new ClassNotFoundException("No such report");
		}

	}
}
