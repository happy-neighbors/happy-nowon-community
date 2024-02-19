package com.project.community.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "notice")
public class NoticeEntity extends DateEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long no;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false)
	private boolean type;
	private LocalDateTime start;
	private LocalDateTime end;
	@Column(nullable = false)
	@ColumnDefault("0")
	private boolean status;
}
