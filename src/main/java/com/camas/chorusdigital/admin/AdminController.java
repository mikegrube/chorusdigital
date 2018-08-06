package com.camas.chorusdigital.admin;

import com.camas.chorusdigital.security.PasswordChange;
import com.camas.chorusdigital.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "SameReturnValue"})
@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	private static String flash = null;

	private LoaderService service;
	@Autowired
	public void setService(LoaderService service) {
		this.service = service;
	}

	@GetMapping("/index")
	public String admin(Model model) {

		return "adminIndex";
	}

	@GetMapping("/load")
	public String requestLoad(Model model) {

		model.addAttribute("loadRequest", new LoadRequest());
		List<String> types = new ArrayList<>();
		types.add("Concert");
		model.addAttribute("types", types);

		return "loadRequest";
	}

	@PostMapping("/load")
	public String load(LoadRequest loadRequest) {

		service.postYaml(loadRequest.getType(), loadRequest.getYaml());

		return "redirect:/admin/load";
	}

	@GetMapping("/upload")
	public String requestUpload(Model model) {

		model.addAttribute("uploadRequest", new UploadRequest());
		List<String> types = new ArrayList<>();
		types.add("Concert");
		model.addAttribute("types", types);

		return "uploadRequest";
	}

	@PostMapping("/upload")
	public String fileUpload(UploadRequest uploadRequest) {

		try {

			String yaml = new String(uploadRequest.file.getBytes());
			service.postYaml(uploadRequest.type, yaml);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/admin/upload";
	}

	@GetMapping("/notes")
	public String notes() {

		return "notes";
	}

}