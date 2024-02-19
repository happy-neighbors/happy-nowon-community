package com.project.community.domain;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class DateEntity {
	
	private long readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
