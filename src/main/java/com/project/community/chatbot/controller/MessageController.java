package com.project.community.chatbot.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.project.community.chatbot.domain.dto.RequestDTO;
import com.project.community.chatbot.weather.WeatherRequestDTO;

import lombok.RequiredArgsConstructor;

@Controller // /message/**  
@RequiredArgsConstructor
public class MessageController {
	
	private final RabbitTemplate rabbitTemplate;
	
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	
	@Value("${spring.rabbitmq.template.routing-key}")
	private String routingKey;
	
	@MessageMapping("/chatbot")
	public void chatbot(RequestDTO requestDTO) {
		rabbitTemplate.convertAndSend(exchange, routingKey, requestDTO);
	}
	
	@MessageMapping("/weather")
	public void weatherChatbot(RequestDTO requestDTO) {
		System.out.println(">>>>:"+requestDTO);
		rabbitTemplate.convertAndSend(exchange, routingKey, requestDTO);
	}
}
