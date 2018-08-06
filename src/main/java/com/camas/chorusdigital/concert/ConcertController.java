package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.Pager;
import com.camas.chorusdigital.SearchRequest;
import com.camas.chorusdigital.program.ConcertWorkDisplay;
import com.camas.chorusdigital.program.WorkService;
import com.camas.chorusdigital.program.ConcertWork;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"FieldCanBeLocal", "SameReturnValue"})
@Controller
@RequestMapping("/concert")
public class ConcertController {
	private static final Logger log = LoggerFactory.getLogger(ConcertController.class);

	private static String flash = null;

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 10, 15, 30, 50};

	private ConcertService service;
	@Autowired
	public void setService(ConcertService service) {
		this.service = service;
	}

	private WorkService workService;
	@Autowired
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(@RequestParam("pageSize") Optional<Integer> pageSize,
					   @RequestParam("page") Optional<Integer> page, Model model) {

		//Paging
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Concert> concerts = service.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("title")));

		Pager pager = new Pager(concerts.getTotalPages(), concerts.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("concerts", concerts);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);

		return "concertList";

	}

	@RequestMapping(value="/listBySeason", method= RequestMethod.GET)
	public String listBySeason(@RequestParam("pageSize") Optional<Integer> pageSize,
					   @RequestParam("page") Optional<Integer> page, Model model) {

		//Paging
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Concert> concerts = service.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("season.startYear")));

		Pager pager = new Pager(concerts.getTotalPages(), concerts.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("concerts", concerts);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);

		return "concertSeasonList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Concert concert = service.get(id);
		model.addAttribute("concert", concert);
		model.addAttribute("performances", service.performancesForConcert(concert));
		model.addAttribute("concertContributors", service.concertContributorsForConcert(concert));
		Iterable<ConcertWork> concertWorks = service.concertWorksForConcert(concert);
		List<ConcertWorkDisplay> concertWorkDisplays = new ArrayList<>();
		for (ConcertWork concertWork : concertWorks) {
			ConcertWorkDisplay wd = new ConcertWorkDisplay();
			wd.setId(concertWork.getWork().getId());
			wd.setTitle(concertWork.getWork().getTitle());
			wd.setComposer(workService.composerForWork(concertWork.getWork()));
			wd.setConcertWorkId(concertWork.getId());
			wd.setPerformedByGroup(concertWork.isPerformedByGroup());
			wd.setTrack(concertWork.getTrack());
			wd.setAudioExists(service.audioExistsForConcertWork(concertWork));
			wd.setVideoExists(concertWork.videoExists());
			concertWorkDisplays.add(wd);
		}

		model.addAttribute("concertWorkDisplays", concertWorkDisplays);

		return "concertShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("concert", new Concert());
		model.addAttribute("seasons", service.availableSeasons());

		return "concertEdit";
	}

	@PostMapping("/save")
	public String saveConcert(@Valid Concert concert, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("seasons", service.availableSeasons());
			return "concertEdit";
		}

		service.save(concert);

		return "redirect:/concert/" + concert.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("concert", service.get(id));
		model.addAttribute("seasons", service.availableSeasons());

		return "concertEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/concert/list";
	}

	@GetMapping("/program/{id}")
	public String displayProgram(@PathVariable Long id, Model model) {

		Concert concert = service.get(id);
		model.addAttribute("title", concert.getTitle());
		model.addAttribute("fileName", concert.getProgramPath());

		return "pdfPage";
	}

	@GetMapping("/search")
	public String searchRequest(Model model) {

		SearchRequest searchRequest = new SearchRequest();
		model.addAttribute("searchRequest", searchRequest);

		return "concertSearchRequest";
	}

	@PostMapping("/search")
	public String search(SearchRequest searchRequest, Model model) {

		model.addAttribute("concerts", service.listForTitleLike(searchRequest.getSearchItem()));

		return "concertSearchList";
	}

	@GetMapping("/fullCreate")
	public String fullCreateRequest(Model model) {

		ConcertCreateRequest concertCreateRequest = new ConcertCreateRequest();
		model.addAttribute("concertCreateRequest", concertCreateRequest);
		model.addAttribute("seasons", service.availableSeasons());
		model.addAttribute("venues", service.availableVenues());
		model.addAttribute("contributors", service.availableContributors());

		return "concertFullCreate";
	}

	@PostMapping("/fullCreate")
	public String fullCreate(ConcertCreateRequest concertCreateRequest, Model model) {

		service.fullCreate(concertCreateRequest);

		return "concertWorkCreate";
	}

	@GetMapping("/activate/{id}")
	public String activate(@PathVariable Long id, Model model) {

		Concert concert = service.get(id);
		service.makeActive(concert);

		return "redirect:/concert/list";
	}

	@GetMapping("/video/{id}")
	public String displayVideo(@PathVariable Long id, Model model) {

		Concert concert = service.get(id);
		String url = concert.getVideoURL();
		if (!url.startsWith("http://")  && !url.startsWith("https://")) {
			url = "http://" + url;
		}

		return "redirect:" + url;
	}
}