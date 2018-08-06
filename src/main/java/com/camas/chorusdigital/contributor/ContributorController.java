package com.camas.chorusdigital.contributor;

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

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/contributor")
public class ContributorController {
	private static final Logger log = LoggerFactory.getLogger(ContributorController.class);

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = { 10, 15, 30, 50};

	private ContributorService service;
	@Autowired
	public void setService(ContributorService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(@RequestParam("pageSize") Optional<Integer> pageSize,
					   @RequestParam("page") Optional<Integer> page, Model model) {

		//Paging
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;


		Page<Contributor> contributors = service.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by("lastName", "firstName")));

		Pager pager = new Pager(contributors.getTotalPages(), contributors.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("contributors", contributors);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);

		return "contributorList";

	}

	@RequestMapping(value="/listMember", method= RequestMethod.GET)
	public String listMember(@RequestParam("pageSize") Optional<Integer> pageSize,
					   @RequestParam("page") Optional<Integer> page, Optional<Boolean> member, Model model) {

		//Paging
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;


		Page<Contributor> contributors = null;
		if (member.get()) {
			contributors = service.findAllByMember(true, PageRequest.of(evalPage, evalPageSize, Sort.by("lastName", "firstName")));
		} else {
			contributors = service.findAllByMember(false, PageRequest.of(evalPage, evalPageSize, Sort.by("lastName", "firstName")));
		}

		Pager pager = new Pager(contributors.getTotalPages(), contributors.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("contributors", contributors);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("member", member.get());

		return "contributorMemberList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Contributor contributor = service.get(id);
		model.addAttribute("contributor", contributor);
		model.addAttribute("concerts", service.concertsForContributor(contributor));

		return "contributorShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("contributor", new Contributor());
		model.addAttribute("sections", service.availableSections());

		return "contributorEdit";
	}

	@PostMapping("/save")
	public String saveContributor(@Valid Contributor contributor, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "contributorEdit";
		}

		service.save(contributor);

		return "redirect:/contributor/" + contributor.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("contributor", service.get(id));
		model.addAttribute("sections", service.availableSections());

		return "contributorEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/contributor/list";
	}
}
