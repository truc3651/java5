package com.asm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm.entity.Departs;
import com.asm.service.DepartService;

@Controller
@RequestMapping("depart")
public class DepartController {
	@Autowired
	private DepartService departService;
	
	@GetMapping("/")
	public String CreateView(Model model) {
		model.addAttribute("depart", new Departs());
		
		return "departs/create_depart";
	}
	
	@PostMapping("create")
	public String CreateSubmit(
			Model model,
			@Valid @ModelAttribute("depart")Departs depart,
			BindingResult result) {
		
		if(result.hasErrors()) {
			model.addAttribute("depart", depart);
			return "departs/create_depart";
		}
		
		departService.Save(depart);
		
		return "departs/create_success";
	}
	
}









