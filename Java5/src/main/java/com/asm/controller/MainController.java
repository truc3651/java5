package com.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	public String HomePage() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String LoginPage() {
		return "login";
	}
	
	@GetMapping("/403")
	public String errorPage() {
		return "403";
	}
}
