package com.project.community.domain.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.community.domain.entity.EmployeeEntity;

import lombok.Data;

@Data
public class OauthDTO {

	private String email;
	private String name;
	private String role;
	
	public EmployeeEntity getDTO() {
		return EmployeeEntity.builder()
				.empUsername(email)
				.empName(name)
				.empRole(role)
				.build();
	}
}