package com.project.community.domain.entity;

import com.project.community.domain.dto.TownDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="town")
@Entity
public class TownEntity extends DateEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String title;
	private String leader;
	private String phone;
	private String area;
	@Column(columnDefinition = "longtext")
	private String content;
	@ManyToOne
	@JoinColumn(name = "employee_empNo", nullable = false)
    private EmployeeEntity employee;
	public TownDTO toTownDTO() {
		return TownDTO.builder()
				.no(no)
				.title(title)
				.leader(leader)
				.phone(phone)
				.area(area)
				.content(content)
				.empNo(employee.getEmpNo())
				.readCount(getReadCount())
				.createdAt(getCreatedAt())
				.updatedAt(getUpdatedAt())
				.build();
	}

}
