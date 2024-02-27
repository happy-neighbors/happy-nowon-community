package com.project.community.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;


import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class DateEntity {
	
	@ColumnDefault("0")
	private long readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
	    // getCount 메서드에서의 업데이트를 방지하는 조건 추가
	    if (!isGetCountUpdate()) {
	        return;
	    }

	    this.updatedAt = LocalDateTime.now();
	}

	private boolean isGetCountUpdate() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    for (StackTraceElement element : stackTrace) {
	        if (element.getClassName().equals("com.project.community.controller.TownController")
	            && element.getMethodName().equals("detailTown")) {
	            return false;
	        }
	        if (element.getClassName().equals("com.project.community.controller.AnabadaController")
		        && element.getMethodName().equals("detailAnabada")) {
		        return false;
		    }
	        if (element.getClassName().equals("com.project.community.controller.NoticeController")
		        && element.getMethodName().equals("noticeDetail")) {
		        return false;
		    }
	    }

	    return true;
	}


}

