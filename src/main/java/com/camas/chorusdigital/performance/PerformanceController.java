package com.camas.chorusdigital.performance;

import com.camas.chorusdigital.Pager;
import com.camas.chorusdigital.concert.Concert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@SuppressWarnings({"FieldCanBeLocal", "SameReturnValue"})
@Controller
@RequestMapping("/performance")
public class PerformanceController {
	private static final Logger log = LoggerFactory.getLogger(PerformanceController.class);

	private static String flash = null;

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 10, 15, 30, 50};

	private PerformanceService service;
	@Autowired
	public void setService(PerformanceService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(@RequestParam("pageSize") Optional<Integer> pageSize,
					   @RequestParam("page") Optional<Integer> page, Model model) {

		//Paging
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Performance> performances = service.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("dateTime")));

		Pager pager = new Pager(performances.getTotalPages(), performances.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("performances", performances);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);

		return "performanceList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		model.addAttribute("performance", service.get(id));

		return "performanceShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("performance", new Performance());
		model.addAttribute("concerts", service.availableConcerts());
		model.addAttribute("venues", service.availableVenues());

		return "performanceEdit";
	}

	@GetMapping("/new/{concertId}")
	public String create(@PathVariable Long concertId, Model model) {

		Performance performance = new Performance();
		performance.setConcert(service.getConcert(concertId));
		model.addAttribute("performance", performance);
		model.addAttribute("concerts", service.availableConcerts());
		model.addAttribute("venues", service.availableVenues());

		return "performanceEdit";
	}

	@PostMapping("/save")
	public String saveConcert(@Valid Performance performance, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("concerts", service.availableConcerts());
			model.addAttribute("venues", service.availableVenues());
			return "performanceEdit";
		}

		service.save(performance);

		return "redirect:/performance/" + performance.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("performance", service.get(id));
		model.addAttribute("concerts", service.availableConcerts());
		model.addAttribute("venues", service.availableVenues());

		return "performanceEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/performance/list";
	}

}