package com.project.community.chatbot.domain.dto;

import lombok.Data;

@Data
public class RequestDTO {
	
	private long key;
	private String name;
	private String content;
}
