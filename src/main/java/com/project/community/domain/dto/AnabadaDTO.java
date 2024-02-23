package com.project.community.domain.dto;

import java.time.LocalDateTime;

import com.project.community.domain.entity.AnabadaEntity;
import com.project.community.domain.entity.EmployeeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnabadaDTO {
	
	private long no;
	private String title;
	private String writer;
	private String phone;
	private String area;
	private String content;
	private String state;
	private long empNo;
	private long readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public AnabadaEntity toEntity(EmployeeEntity employeeEntity) {
		return AnabadaEntity.builder()
				.no(no)
				.title(title)
				.writer(writer)
				.phone(phone)
				.area(area)
				.content(content)
				.state(state)
				.employee(employeeEntity)
				.build();
	}

}
