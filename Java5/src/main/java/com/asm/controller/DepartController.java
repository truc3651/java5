package com.asm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm.entity.Departs;
import com.asm.service.DepartService;

@Controller
@RequestMapping("depart")
public class DepartController {
	@Autowired
	private DepartService departService;
	
	@GetMapping("/test")
	public String draft(Model model) {
		Departs depart = departService.GetFirst();
		model.addAttribute("depart", depart);
		
		return "departs/draft";
	}
	
	
	
}









