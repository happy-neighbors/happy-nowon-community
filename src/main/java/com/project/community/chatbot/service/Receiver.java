package com.project.community.chatbot.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.project.community.chatbot.domain.dto.MessageDTO;
import com.project.community.chatbot.domain.dto.RequestDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Receiver {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final TemplateEngine templateEngine;
	private final KomoranService komoranService;
	
	public void receiveMessage(RequestDTO requestDTO) {
		MessageDTO messageDTO = komoranService.nlpAnalyze(requestDTO.getContent());
		
		Context context = new Context();
		context.setVariable("message", messageDTO);
		
		String htmlResponse = templateEngine.process("chatbot/chatbot-message", context);
		
		simpMessagingTemplate.convertAndSend("/topic/request/" + requestDTO.getKey(), htmlResponse);
	}
}
