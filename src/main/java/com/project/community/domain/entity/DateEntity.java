package com.project.community.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@SuperBuilder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateEntity {
	
	@ColumnDefault("0")
	private long readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@PrePersist // 필드 초기화
    public void prePersist() {
		this.readCount = 0;
        this.createdAt = LocalDateTime.now();
    }
	
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
