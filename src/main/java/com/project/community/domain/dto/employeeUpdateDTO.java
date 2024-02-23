package com.project.community.domain.dto;


import java.sql.Time;
import java.time.LocalTime;

import com.project.community.domain.entity.EmployeeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class employeeUpdateDTO {

	private long empNo;
	private String empPhone;
	private String empPassword;
	
	public EmployeeEntity toEntity() {
		return EmployeeEntity.builder()
				.empPassword(empPassword)
				.empPhone(empPhone)
				.build();
	}
}
