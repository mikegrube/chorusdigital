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
@RequestMapping("/seasonContributorSalesClass")
public class SeasonContributorSalesClassController {
	private static final Logger log = LoggerFactory.getLogger(SeasonContributorSalesClassController.class);

	private SeasonContributorSalesClassService service;

	@Autowired
	public void setService(SeasonContributorSalesClassService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("seasonContributorSalesClasses", service.list());

		return "seasonContributorSalesClassList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		SeasonContributorSalesClass seasonContributorSalesClass = service.get(id);
		model.addAttribute("seasonContributorSalesClass", seasonContributorSalesClass);

		return "seasonContributorSalesClassShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		SeasonContributorSalesClass seasonContributorSalesClass = new SeasonContributorSalesClass();
		model.addAttribute("seasonContributorSalesClass", seasonContributorSalesClass);

		model.addAttribute("concertContributors", service.getActiveConcertContributors());
		model.addAttribute("seasonSalesClasses", service.getActiveSeasonSalesClasses());
		model.addAttribute("ticketStatuses", service.getTicketStatuses());

		return "seasonContributorSalesClassEdit";
	}

	@PostMapping("/save")
	public String save(@Valid SeasonContributorSalesClass seasonContributorSalesClass, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "seasonContributorSalesClassEdit";
		}

		seasonContributorSalesClass = service.save(seasonContributorSalesClass);

		return "redirect:/seasonContributorSalesClass/" + seasonContributorSalesClass.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("seasonContributorSalesClass", service.get(id));

		model.addAttribute("concertContributors", service.getActiveConcertContributors());
		model.addAttribute("seasonSalesClasses", service.getActiveSeasonSalesClasses());
		model.addAttribute("ticketStatuses", service.getTicketStatuses());

		return "seasonContributorSalesClassEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/seasonContributorSalesClass/list";
	}

	@GetMapping("/assignRequest")
	public String assignRequest(Model model) {

		SeasonAssignmentRequest seasonAssignmentRequest = new SeasonAssignmentRequest();
		seasonAssignmentRequest.setSeason(service.getActiveSeason());

		model.addAttribute("seasonAssignmentRequest", seasonAssignmentRequest);

		return "seasonAssignmentRequest";
	}

	@PostMapping("/assign")
	public String assign(SeasonAssignmentRequest seasonAssignmentRequest, Model model) {

		service.assignTickets(seasonAssignmentRequest);

		return "redirect:/seasonContributorSalesClass/list";
	}

	@GetMapping("/transferRequest")
	public String transferRequest(Model model) {

		SeasonTransferRequest seasonTransferRequest = new SeasonTransferRequest();
		seasonTransferRequest.setSeason(service.getActiveSeason());
		seasonTransferRequest.setCount(2);

		model.addAttribute("seasonTransferRequest", seasonTransferRequest);
		model.addAttribute("concertContributors", service.getActiveConcertContributors());
		model.addAttribute("seasonSalesClasses", service.getActiveSeasonSalesClasses());

		return "seasonTransferRequest";
	}

	@PostMapping("/transfer")
	public String transfer(SeasonTransferRequest seasonTransferRequest, Model model) {

		service.transferTickets(seasonTransferRequest);

		return "redirect:/seasonContributorSalesClass/list";
	}

	@GetMapping("/sellRequest")
	public String sellRequest(Model model) {

		SeasonSellRequest seasonSellRequest = new SeasonSellRequest();
		seasonSellRequest.setSeason(service.getActiveSeason());
		seasonSellRequest.setCount(2);

		model.addAttribute("seasonSellRequest", seasonSellRequest);
		model.addAttribute("concertContributors", service.getActiveConcertContributors());
		model.addAttribute("seasonSalesClasses", service.getActiveSeasonSalesClasses());

		return "seasonSellRequest";

	}

	@PostMapping("/sell")
	public String sell(SeasonSellRequest seasonSellRequest, Model model) {

		service.sellTickets(seasonSellRequest);

		return "redirect:/seasonContributorSalesClass/list";
	}

}
