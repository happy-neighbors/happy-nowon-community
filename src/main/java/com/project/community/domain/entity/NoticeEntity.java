package com.project.community.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.project.community.domain.dto.NoticeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "notice")
@SequenceGenerator(name = "gen_seq_notice", sequenceName = "seq_notice", initialValue = 1, allocationSize = 1)
public class NoticeEntity extends DateEntity {

	@Id
	@GeneratedValue(generator = "gen_seq_notice", strategy = GenerationType.SEQUENCE)
	private Long no;
	@Column(nullable = false)
	private String title;
	@Column(columnDefinition = "text", nullable = false)
	private String content;
	@Column(nullable = false)
	private boolean type;
	private LocalDateTime start;
	private LocalDateTime end;
	@Column(nullable = false)
	@ColumnDefault("0")
	private boolean status;
	
	
	@ManyToOne
	@JoinColumn(name = "empNo", nullable = false)
	private EmployeeEntity employee;
	
	public NoticeDTO toDTO() {
		return NoticeDTO.builder()
				.no(no)
				.title(title)
				.readCount(getReadCount())
				.createdAt(getCreatedAt())
				.updatedAt(getUpdatedAt())
				.memNo(employee.getEmpNo())
				.type(type)
				.start(start)
				.end(end)
				.content(content)
				.build();
	}
}
