package com.project.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.community.chatbot.domain.entity.Intention;
import com.project.community.chatbot.domain.entity.IntentionRepository;
import com.project.community.chatbot.domain.entity.Response;
import com.project.community.chatbot.domain.entity.ResponseRepository;

@SpringBootTest
class HappyNowonCommunityApplicationTests {

	@Autowired
	IntentionRepository intentionRepository;



	@Autowired
	ResponseRepository responseRepository;



	@Test
	void chatbotRegistrationGreeting() {

	intentionRepository.save(Intention.builder()

	.keyword("인사")

	.response(responseRepository.save(Response.builder()

	.keyword("인사")

	.content("안녕하세요. 노원 행복마당 챗봇입니다.")

	.build()))

	.build());

	}

}
