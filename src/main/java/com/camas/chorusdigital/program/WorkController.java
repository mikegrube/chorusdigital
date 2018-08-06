package com.camas.chorusdigital.program;

import com.camas.chorusdigital.Pager;
import com.camas.chorusdigital.SearchRequest;
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
@RequestMapping("/work")
public class WorkController {
	private static final Logger log = LoggerFactory.getLogger(WorkController.class);

	private static String flash = null;

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 10, 15, 30, 50};

	private WorkService service;
	@Autowired
	public void setService(WorkService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(@RequestParam("pageSize") Optional<Integer> pageSize,
					   @RequestParam("page") Optional<Integer> page, Model model) {

		//Paging
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Work> works = service.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("title")));

		addComposer(works);

		Pager pager = new Pager(works.getTotalPages(), works.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("works", works);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);

		return "workList";

	}

	private void addComposer(Iterable<Work> works) {
		for (Work work : works) {
			MusicContributor composer = service.composerForWork(work);
			if (composer != null) {
				work.setComposer(service.composerForWork(work));
			} else {
				log.error("Composer not found for " + work.getTitle());
				MusicContributor unknown = service.findUnknownComposer();
				work.setComposer(unknown);
			}
		}
	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Work work = service.get(id);
		model.addAttribute("work", work);
//FIX		model.addAttribute("concertWorkTracks", service.concertWorkTracksForConcertWork(concertWork));
		model.addAttribute("workMusicContributors", service.workMusicContributorsForWork(work));
		model.addAttribute("concertWorks", service.concertsForWork(work));

		return "workShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("work", new Work());
//		model.addAttribute("concerts", service.availableConcerts());

		return "workEdit";
	}

	@PostMapping("/save")
	public String saveConcert(@Valid Work work, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
//			model.addAttribute("concerts", service.availableConcerts());
			return "workEdit";
		}

		service.save(work);

		return "redirect:/work/" + work.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("work", service.get(id));
//		model.addAttribute("concerts", service.availableConcerts());

		return "workEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/work/list";
	}

	@GetMapping("/search")
	public String searchRequest(Model model) {

		SearchRequest searchRequest = new SearchRequest();
		model.addAttribute("searchRequest", searchRequest);

		return "workSearchRequest";
	}

	@PostMapping("/search")
	public String search(SearchRequest searchRequest, Model model) {

		Iterable<Work> works = service.listForTitleLike(searchRequest.getSearchItem());
		addComposer(works);

		model.addAttribute("works", works);

		return "workSearchList";
	}

}