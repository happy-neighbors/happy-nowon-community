package com.project.community.domain.dto;

import java.time.LocalDateTime;

import com.project.community.domain.entity.TownCommentEntity;
import com.project.community.domain.entity.TownCommentEntity.TownCommentEntityBuilder;
import com.project.community.domain.entity.TownEntity;

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
public class TownCommentDTO {

	private long no;
	private String writer;
	private String content;
	private LocalDateTime createdAt;
    private TownEntity townNo;
    
    public TownCommentEntity toEntity(TownEntity townEntity) {
    	return TownCommentEntity.builder()
    			.no(no)
    			.writer(writer)
    			.content(content)
    			.createdAt(createdAt)
    			.townNo(townEntity)
    			.build();
    }
	
}
