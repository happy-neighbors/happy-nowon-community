package com.project.community.domain.dto;

import java.time.LocalDateTime;

import com.project.community.domain.entity.NoteEntity;
import com.project.community.domain.entity.NoteEntity.NoteEntityBuilder;

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
public class NoteDTO {
	
	private long no;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime createdAt;
	private String reader;
	private boolean state;
	
	public NoteEntity toEntity() {
		return NoteEntity.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.reader(reader)
				.build();
	}

}
