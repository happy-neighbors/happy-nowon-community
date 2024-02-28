package com.project.community.chatbot.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.project.community.chatbot.domain.dto.MessageDTO;
import com.project.community.chatbot.domain.dto.RequestDTO;
import com.project.community.chatbot.weather.WeatherAnswer;
import com.project.community.chatbot.weather.WeatherBotService;
import com.project.community.chatbot.weather.WeatherRequestDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Receiver {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final TemplateEngine templateEngine;
	private final KomoranService komoranService;
	
	private final WeatherBotService weatherBotService;
	
	public void receiveMessage(RequestDTO requestDTO) {
		
		if(requestDTO.getDivision()!=null&& requestDTO.getDivision().equals("weather")) {
			//날씨처리
			//simpMessagingTemplate.convertAndSend("/topic/request/" + requestDTO.getKey(), "<p>테스트</p>");
			//*
			//MessageDTO messageDTO = komoranService.nlpAnalyze(requestDTO.getContent());
			
			WeatherRequestDTO dto=requestDTO.getWeather();
			System.out.println(dto);
			//*
			WeatherAnswer answer=weatherBotService.getAnswer(dto, requestDTO.getOrder());
			
			
			Context context = new Context();
			context.setVariable("msg", answer);//Model
			System.out.println(">>>answer:"+answer);
			
			String htmlResponse = templateEngine.process("chatbot/weather-bot-message", context);
			//System.out.println("------------------------");
			//System.out.println(htmlResponse);
			simpMessagingTemplate.convertAndSend("/topic/request/" + requestDTO.getKey(), htmlResponse);
			//*/
			//simpMessagingTemplate.convertAndSend("/topic/request/" + requestDTO.getKey(), "<p>테스트</p>");
			
			return;
		}
		//영화처리
		System.out.println("***receiveMessage:"+requestDTO);
		//*
		MessageDTO messageDTO = komoranService.nlpAnalyze(requestDTO.getContent());
		
		Context context = new Context();
		context.setVariable("message", messageDTO);
		
		String htmlResponse = templateEngine.process("chatbot/chatbot-message", context);
		
		simpMessagingTemplate.convertAndSend("/topic/request/" + requestDTO.getKey(), htmlResponse);
		
		//*/
		
	}
}
