package com.project.community.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.project.community.domain.dto.EmployeeDTO;

public interface EmployeeService {
	
	void employeeRegistration(EmployeeDTO employeeDTO);
	
}