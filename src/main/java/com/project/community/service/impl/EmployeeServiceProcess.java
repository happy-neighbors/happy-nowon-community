package com.project.community.service.impl;

import java.lang.reflect.Member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.community.domain.dto.EmployeeDTO;
import com.project.community.domain.dto.employeeUpdateDTO;
import com.project.community.domain.entity.EmployeeEntity;
import com.project.community.domain.entity.EmployeeEntityRepository;
import com.project.community.security.MyRole;
import com.project.community.service.EmployeeService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceProcess implements EmployeeService {

	private final EmployeeEntityRepository employeeEntityRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void employeeRegistration(EmployeeDTO employeeDTO) {
		EmployeeEntity employeeEntity = employeeEntityRepository
				.save(employeeDTO.toEmployeeEntity(passwordEncoder).addRole(MyRole.USER));
		// POST
		// https://workplace.apigw.ntruss.com/organization/apigw/v2/company/{companyId}/employee/{externalKey}
	}

	// empUSername이 존재하는지 찾아 boolean으로 리턴
	@Override
	public boolean existsByempUsername(String empUsername) {
		return employeeEntityRepository.existsByEmpUsername(empUsername);
	}

	@Override
	public void employeeUpdate(EmployeeDTO employeeDTO) {
		// TODO Auto-generated method stub
	}

	public Member getMemberByUsername(String empUsername) {
		// TODO Auto-generated method stub
		return null;
	}
}