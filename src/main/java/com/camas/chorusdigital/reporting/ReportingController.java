package com.camas.chorusdigital.reporting;

import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/reporting")
public class ReportingController {

	private static final Logger log = LoggerFactory.getLogger(ReportingController.class);

	private ReportingService service;
	@Autowired
	public void setService(ReportingService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("reports", service.list());

		return "reportList";
	}

	@GetMapping(value = "/report/{title}", produces = MediaType.APPLICATION_PDF_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getPdf(@PathVariable String title, final HttpServletResponse response) throws JRException, ClassNotFoundException {

		final byte[] report = service.generateReport(title);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + title + ".pdf");
		header.setContentLength(report.length);

		return new HttpEntity<byte[]>(report, header);
	}

}
