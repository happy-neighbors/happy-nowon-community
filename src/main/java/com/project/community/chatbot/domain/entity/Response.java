package com.project.community.chatbot.domain.entity;

import com.project.community.chatbot.domain.dto.ResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "response")
@SequenceGenerator(name = "gen_resp_seq", sequenceName = "resp_seq", 
allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Response {
	
	@Id
	@GeneratedValue(generator = "gen_resp_seq", strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String keyword;
	
	@Column(nullable = false)
	@Lob
	private String content;
	
	public Response keyword(String keyword) {
		this.keyword = keyword;
		
		return this;
	}
	
	public ResponseDTO toResponseDTO() {
		return ResponseDTO.builder()
				.keyword(keyword)
				.content(content)
				.build();
	}
}
