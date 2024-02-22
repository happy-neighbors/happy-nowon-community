package com.project.community.domain.entity;

import java.time.LocalDateTime;

import com.project.community.domain.dto.TownCommentDTO;
import com.project.community.domain.dto.TownCommentDTO.TownCommentDTOBuilder;

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
@Table(name="townComment")
@Entity
public class TownCommentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String content;
	private LocalDateTime createdAt;
	@ManyToOne
	@JoinColumn(name = "townEntity_no", nullable = false)
    private TownEntity townNo;
	
	public TownCommentDTOBuilder toTownCommentDTO() {
		return TownCommentDTO.builder()
				.no(no)
				.content(content)
				.createdAt(createdAt)
				.townNo(townNo);
	}
	
}
