package com.project.community.chatbot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageDTO {
	
	private String today;
	private String time;
	private ResponseDTO responseDTO;
	
	public MessageDTO today(String today) {
		this.today = today;
		
		return this;
	}
	
	public MessageDTO response(ResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
		
		return this;
	}
}
