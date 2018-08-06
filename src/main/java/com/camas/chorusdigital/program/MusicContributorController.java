package com.camas.chorusdigital.program;

import com.camas.chorusdigital.Pager;
import com.camas.chorusdigital.SearchRequest;
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
import java.util.List;
import java.util.Optional;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/musicContributor")
public class MusicContributorController {
	private static final Logger log = LoggerFactory.getLogger(MusicContributorController.class);

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 10, 15, 30, 50};

	private MusicContributorService service;
	@Autowired
	public void setService(MusicContributorService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(@RequestParam("pageSize") Optional<Integer> pageSize,
					   @RequestParam("page") Optional<Integer> page, Model model) {

		//Paging
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<MusicContributor> musicContributors = service.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("lastName", "firstName")));

		Pager pager = new Pager(musicContributors.getTotalPages(), musicContributors.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("musicContributors", musicContributors);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);

		return "musicContributorList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		MusicContributor musicContributor = service.get(id);
		model.addAttribute("musicContributor", musicContributor);
		model.addAttribute("workMusicContributors", service.worksForContributor(musicContributor));

		return "musicContributorShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("musicContributor", new MusicContributor());

		return "musicContributorEdit";
	}

	@PostMapping("/save")
	public String saveMusicContributor(@Valid MusicContributor musicContributor, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "contributorEdit";
		}

		service.save(musicContributor);

		return "redirect:/musicContributor/" + musicContributor.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("musicContributor", service.get(id));

		return "musicContributorEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/musicContributor/list";
	}

	@GetMapping("/search")
	public String searchRequest(Model model) {

		SearchRequest searchRequest = new SearchRequest();
		model.addAttribute("searchRequest", searchRequest);

		return "musicContributorSearchRequest";
	}

	@PostMapping("/search")
	public String search(SearchRequest searchRequest, Model model) {

		Iterable<MusicContributor> musicContributors = service.listForFirstNameLikeOrLastNameLike(searchRequest.getSearchItem());

		model.addAttribute("musicContributors", musicContributors);

		return "musicContributorSearchList";
	}

}
