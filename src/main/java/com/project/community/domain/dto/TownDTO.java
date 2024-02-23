package com.project.community.domain.dto;

import java.time.LocalDateTime;

import com.project.community.domain.entity.EmployeeEntity;
import com.project.community.domain.entity.TownEntity;

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
public class TownDTO {
	
	private long no;
	private String title;
	private String leader;
	private String phone;
	private String area;
	private String content;
	private long empNo;
	private long readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public TownEntity toEntity(EmployeeEntity employeeEntity) {
		return TownEntity.builder()
				.no(no)
				.title(title)
				.leader(leader)
				.phone(phone)
				.area(area)
				.content(content)
				.employee(employeeEntity)
				.build();
	}

}
