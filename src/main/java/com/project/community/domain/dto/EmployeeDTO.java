package com.project.community.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
	private LocalDateTime empHireDate;
	
	//lombok이 제공하는 기본 setter는 "yyyy-MM-dd" 형식이 LocalDateTime와 일치하는 패턴이 아니므로
	//setter를 명시해서 파라미터타입을 "yyyy-MM-dd" 형식과 일치하는 LocalDate 로 선언하고 LocalDateTime로 변화
	public void setEmpHireDate(LocalDate empHireDate) {
		this.empHireDate = LocalDateTime.of(empHireDate, LocalTime.of(0,0,0));
	}
	
	public EmployeeEntity toEmployeeEntity(PasswordEncoder passwordEncoder) {
		return EmployeeEntity.builder()
				.empUsername(empUsername)
				.empPassword(passwordEncoder.encode(empPassword))
				.empName(empName)
				.build();
	}
}