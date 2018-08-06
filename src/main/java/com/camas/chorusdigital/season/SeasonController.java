package com.camas.chorusdigital.season;

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

//import com.camas.tickets.ticketing.ConcertSalesClassService;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/season")
public class SeasonController {
	private static final Logger log = LoggerFactory.getLogger(SeasonController.class);

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 10, 15, 30, 50};

	private SeasonService service;
	@Autowired
	public void setService(SeasonService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(@RequestParam("pageSize") Optional<Integer> pageSize,
					   @RequestParam("page") Optional<Integer> page, Model model) {

		//Paging
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Season> seasons = service.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("startYear")));

		Pager pager = new Pager(seasons.getTotalPages(), seasons.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("seasons", seasons);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);

		return "seasonList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Season season = service.get(id);
		model.addAttribute("season", season);
		model.addAttribute("concerts", service.concertsForSeason(season));

		return "seasonShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("season", new Season());

		return "seasonEdit";
	}

	@PostMapping("/save")
	public String saveSeason(@Valid Season season, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "seasonEdit";
		}

		service.save(season);

		return "redirect:/season/" + season.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("season", service.get(id));

		return "seasonEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/season/list";
	}

	@GetMapping("/brochure/{id}")
	public String displayBrochure(@PathVariable Long id, Model model) {

		Season season = service.get(id);
		model.addAttribute("title", season.toString());
		model.addAttribute("fileName", season.getProgramPath());

		return "pdfPage";
	}

}
