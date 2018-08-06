package com.camas.chorusdigital.gallery;

import com.camas.chorusdigital.utility.UtilityService;
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
@RequestMapping("/photo")
public class PhotoController {
	private static final Logger log = LoggerFactory.getLogger(PhotoController.class);

	private PhotoService service;
	@Autowired
	public void setService(PhotoService service) {
		this.service = service;
	}

	private UtilityService utilityService;
	@Autowired
	public void setUtilityService(UtilityService utilityService) {
		this.utilityService = utilityService;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("photos", service.list());

		return "photoList";
	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Photo photo = service.get(id);

		String photoLocation = utilityService.getGalleryPic(photo);

		StringBuilder sb = new StringBuilder("");
		if (photo.getCaption() != null && !photo.getCaption().equals("")) {
			sb.append(photo.getCaption());
		}
		if (photo.getEvent() != null && !photo.getEvent().equals("")) {
			if (!sb.toString().equals("")) {
				sb.append(", ");
			}
			sb.append(photo.getEvent());
		}
		if (photo.getYear() != null && !photo.getYear().equals("")) {
			if (!sb.toString().equals("")) {
				sb.append(", ");
			}
			sb.append(photo.getYear());
		}

		model.addAttribute("photoLocation", photoLocation);
		model.addAttribute("photoCaption", sb.toString());

		return "photoShow";
	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("photo", new Photo());

		return "photoEdit";
	}

	@PostMapping("/save")
	public String save(@Valid Photo photo, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "photoEdit";
		}

		photo = service.save(photo);

		return "redirect:/photo/" + photo.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("photo", service.get(id));

		return "photoEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/photo/list";
	}

}
