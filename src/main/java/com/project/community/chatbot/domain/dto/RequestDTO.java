package com.project.community.chatbot.domain.dto;

import com.project.community.chatbot.weather.WeatherRequestDTO;

import lombok.Data;

@Data
public class RequestDTO {
	
	private long key;
	private String division;
	private String name;
	private String content;
	private int order; 

	private WeatherRequestDTO weather;
}
