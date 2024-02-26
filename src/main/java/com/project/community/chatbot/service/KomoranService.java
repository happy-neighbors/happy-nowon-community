package com.project.community.chatbot.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.community.chatbot.domain.dto.MessageDTO;
import com.project.community.chatbot.domain.dto.ResponseDTO;
import com.project.community.chatbot.domain.entity.Intention;
import com.project.community.chatbot.domain.entity.IntentionRepository;
import com.project.community.chatbot.domain.entity.Response;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KomoranService {
	
	private final Komoran komoran;
	private final IntentionRepository intentionRepository;
	
	public MessageDTO nlpAnalyze(String message) {
		KomoranResult komoranResult = komoran.analyze(message);
		
		komoranResult.getTokenList().forEach(token -> {
			System.out.format("(%2d, %2d) %s %s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
		});
		
		Set<String> nouns = komoranResult.getNouns().stream()
				.collect(Collectors.toSet());
		nouns.forEach(noun -> {
			System.out.println("noun: " + noun);
		});
		
		return analyzeToken(nouns);
	}

	private MessageDTO analyzeToken(Set<String> nouns) {
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a H:mm");
		
		MessageDTO messageDTO = MessageDTO.builder()
				.time(today.format(formatter))
				.build();
		
		for (String token : nouns) {
			Optional<Intention> result = decisionTree(token, null);
			
			Set<String> next = nouns.stream().collect(Collectors.toSet());
			next.remove(token);
			
			ResponseDTO response = analyzeToken(next, result).toResponseDTO();
			
			if (token.contains("인사")) {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
				messageDTO.today(today.format(dateFormatter));
			}
			
			messageDTO.response(response);
			
			return messageDTO;
		}
		
		ResponseDTO response = decisionTree("기타", null).get().getResponse().toResponseDTO();
		messageDTO.response(response);
		
		return messageDTO;
	}

	private Response analyzeToken(Set<String> next, Optional<Intention> upperKeyword) {
		for (String token : next) {
			Optional<Intention> result = decisionTree(token, upperKeyword.get());
			
			if (result.isEmpty()) continue;
			
			return result.get().getResponse();
		}
		
		return upperKeyword.get().getResponse();
	}

	private Optional<Intention> decisionTree(String token, Intention upperKeyword) {
		return intentionRepository.findByKeywordAndUpperKeyword(token, upperKeyword);
	}
}
