package com.project.community.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.project.community.domain.dto.NoteDTO;
import com.project.community.domain.dto.NoteDTO.NoteDTOBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "note")
@SequenceGenerator(name = "gen_emp_seq", sequenceName = "emp_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoteEntity {
	
	@Id
	@GeneratedValue(generator = "gen_emp_seq", strategy = GenerationType.SEQUENCE)
	private long no;
	private String title;
	private String content;
	private String writer;
	@CreationTimestamp
	private LocalDateTime createdAt;
	private String reader;
	@Column(nullable = false)
	@ColumnDefault("0")
	private boolean state;
	
	public NoteDTO toNoteDTO() {
		return NoteDTO.builder()
				.no(no)
				.title(title)
				.content(content)
				.writer(writer)
				.reader(reader)
				.state(state)
				.createdAt(createdAt)
				.build();
	}

}
