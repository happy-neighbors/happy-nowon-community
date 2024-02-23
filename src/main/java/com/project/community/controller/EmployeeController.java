package com.project.community.controller;

import java.lang.reflect.Member;
import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.community.domain.dto.EmployeeDTO;
import com.project.community.domain.dto.employeeUpdateDTO;
import com.project.community.security.EmployeeDetails;
import com.project.community.service.impl.EmployeeServiceProcess;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeServiceProcess employeeServiceProcess;

	@GetMapping("/sign")
	public String signIn(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "exception", required = false) String exception, Model model) {

		model.addAttribute("error", error);
		model.addAttribute("exception", exception);

		return "sign/sign";
	}

	// 회원가입 페이지로 이동
	@GetMapping("/signup")
	public String signup() {
		return "/sign/signup";
	}

	// 회원가입 저장
	@PostMapping("/sign/signup")
	public String employeeRegistration(EmployeeDTO employeeDTO) {
		employeeServiceProcess.employeeRegistration(employeeDTO);

		return "redirect:/sign";
	}

	@ResponseBody
	public boolean checkEmpUsername(@RequestParam(value = "empUsername") String empUsername) {
		return employeeServiceProcess.existsByempUsername(empUsername);
	}

	@GetMapping("/mypage")
	public String myPage(Model model, Principal principal) {
		// 현재 로그인한 사용자의 아이디를 가져온다.
		String empUsername = principal.getName();
		Member member = employeeServiceProcess.getMemberByUsername(empUsername);

		// 아이디를 기반으로 회원정보를 조회한다.
		model.addAttribute("empUsername", empUsername);
		model.addAttribute("member", member);
		return "/sign/mypage";
	}
	
	@GetMapping("/signin")
	public String signin() {
		return "/sign/signin";
	}

	@GetMapping("/modify")
	public String modify() {
		return "/sign/modify";
	}
}
