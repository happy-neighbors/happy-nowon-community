package com.project.community.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class DateEntity {
	
	@ColumnDefault("0")
	private long readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
