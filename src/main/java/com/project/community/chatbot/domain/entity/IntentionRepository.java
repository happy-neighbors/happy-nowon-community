package com.project.community.chatbot.domain.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IntentionRepository extends JpaRepository<Intention, Long> {

	Optional<Intention> findByKeywordAndUpperKeyword(String token, Intention upperKeyword);
}
