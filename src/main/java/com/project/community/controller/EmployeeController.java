package com.project.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
	
	
	@GetMapping("/sign")
	public String signIn(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "exception", required = false) String exception,
			Model model) {
		
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		
		return "sign/sign";
	}
	
}
