package com.project.community.domain.entity;

import com.project.community.domain.dto.AnabadaDTO;

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
@Table(name="anabada")
@Entity
public class AnabadaEntity extends DateEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String title;
	private String writer;
	private String phone;
	private String area;
	@Column(columnDefinition = "longtext")
	private String content;
	private String state;
	@ManyToOne
	@JoinColumn(name = "employee_empNo", nullable = false)
    private EmployeeEntity employee;
	public AnabadaDTO toAnabadaDTO() {
		return AnabadaDTO.builder()
				.no(no)
				.title(title)
				.writer(writer)
				.phone(phone)
				.area(area)
				.content(content)
				.empNo(employee.getEmpNo())
				.state(state)
				.readCount(getReadCount())
				.createdAt(getCreatedAt())
				.updatedAt(getUpdatedAt())
				.build();
	}
	public AnabadaEntity update(String area, String content, String title, String state) {
		this.area=area;
		this.content=content;
		this.title=title;
		this.state=state;
		return this;
	}

}
