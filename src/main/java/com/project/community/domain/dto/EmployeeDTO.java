package com.project.community.domain.dto;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.community.domain.entity.EmployeeEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {
	
	private long empNo;
	private String empUsername;
	private String empPassword;
	private String empName;
	private String empRole;
	private String empPhone;
	private String empBirth;
	
	
	public EmployeeEntity toEmployeeEntity(PasswordEncoder passwordEncoder) {
		return EmployeeEntity.builder()
				.empUsername(empUsername)
				.empPassword(passwordEncoder.encode(empPassword))
				.empName(empName)
				.empRole(empRole)
				.empBirth(empBirth)
				.empPhone(empPhone)
				.build();
	}
}