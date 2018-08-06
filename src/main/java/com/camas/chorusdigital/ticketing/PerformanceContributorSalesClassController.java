package com.camas.chorusdigital.ticketing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/performanceContributorSalesClass")
public class PerformanceContributorSalesClassController {
	private static final Logger log = LoggerFactory.getLogger(PerformanceContributorSalesClassController.class);

	private PerformanceContributorSalesClassService service;

	@Autowired
	public void setService(PerformanceContributorSalesClassService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("performanceContributorSalesClasses", service.list());

		return "performanceContributorSalesClassList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		PerformanceContributorSalesClass performanceContributorSalesClass = service.get(id);
		model.addAttribute("performanceContributorSalesClass", performanceContributorSalesClass);

		return "performanceContributorSalesClassShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("performanceContributorSalesClass", new PerformanceContributorSalesClass());

		model.addAttribute("performances", service.getActivePerformances());
		model.addAttribute("concertSalesClasses", service.getActiveConcertSalesClasses());
		model.addAttribute("concertContributors", service.getActiveConcertContributors());
		model.addAttribute("ticketStatuses", service.getTicketStatuses());

		return "performanceContributorSalesClassEdit";
	}

	@PostMapping("/save")
	public String save(@Valid PerformanceContributorSalesClass performanceContributorSalesClass, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "performanceContributorSalesClassEdit";
		}

		performanceContributorSalesClass = service.save(performanceContributorSalesClass);

		return "redirect:/performanceContributorSalesClass/" + performanceContributorSalesClass.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("performanceContributorSalesClass", service.get(id));

		model.addAttribute("performances", service.getActivePerformances());
		model.addAttribute("concertSalesClasses", service.getActiveConcertSalesClasses());
		model.addAttribute("concertContributors", service.getActiveConcertContributors());
		model.addAttribute("ticketStatuses", service.getTicketStatuses());

		return "performanceContributorSalesClassEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/performanceContributorSalesClass/list";
	}

	@GetMapping("/assignRequest")
	public String assignRequest(Model model) {

		PerformanceAssignmentRequest performanceAssignmentRequest = new PerformanceAssignmentRequest();
		performanceAssignmentRequest.setConcert(service.getActiveConcert());

		model.addAttribute("performanceAssignmentRequest", performanceAssignmentRequest);

		return "performanceAssignmentRequest";
	}

	@PostMapping("/assign")
	public String assign(PerformanceAssignmentRequest performanceAssignmentRequest, Model model) {

		service.assignTickets(performanceAssignmentRequest);

		return "redirect:/performanceContributorSalesClass/list";
	}

	@GetMapping("/transferRequest")
	public String transferRequest(Model model) {

		PerformanceTransferRequest performanceTransferRequest = new PerformanceTransferRequest();
		performanceTransferRequest.setCount(2);

		model.addAttribute("performanceTransferRequest", performanceTransferRequest);
		model.addAttribute("concertContributors", service.getActiveConcertContributors());
		model.addAttribute("performances", service.getActivePerformances());

		return "performanceTransferRequest";
	}

	@PostMapping("/transfer")
	public String transfer(PerformanceTransferRequest performanceTransferRequest, Model model) {

		service.transferTickets(performanceTransferRequest);

		return "redirect:/performanceContributorSalesClass/list";
	}

	@GetMapping("/sellRequest")
	public String sellRequest(Model model) {

		PerformanceSellRequest performanceSellRequest = new PerformanceSellRequest();
		performanceSellRequest.setCount(2);

		model.addAttribute("performanceSellRequest", performanceSellRequest);
		model.addAttribute("performances", service.getActivePerformances());
		model.addAttribute("concertContributors", service.getActiveConcertContributors());
		model.addAttribute("concertSalesClasses", service.getActiveConcertSalesClasses());

		return "performanceSellRequest";

	}

	@PostMapping("/sell")
	public String sell(PerformanceSellRequest performanceSellRequest, Model model) {

		service.sellTickets(performanceSellRequest);

		return "redirect:/performanceContributorSalesClass/list";
	}

}
