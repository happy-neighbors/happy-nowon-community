package com.project.community.domain.dto;

import java.time.LocalDateTime;

import com.project.community.domain.entity.DateEntity;
import com.project.community.domain.entity.NoticeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO extends DateEntity{

	private Long no;
	private String title;
	private boolean type;
	private LocalDateTime start;
	private LocalDateTime end;
	private String content;
	private long readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public NoticeEntity toEntity() {
		return NoticeEntity.builder()
		.no(no)
		.title(title)
		.type(type)
		.start(start)
		.end(end)
		.content(content)
		.build();
	}
}
