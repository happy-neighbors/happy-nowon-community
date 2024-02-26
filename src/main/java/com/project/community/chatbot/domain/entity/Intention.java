package com.project.community.chatbot.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "intention")
@SequenceGenerator(name = "gen_int_seq", sequenceName = "int_seq", 
allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Intention {
	
	@Id
	@GeneratedValue(generator = "gen_int_seq", strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false)
	private String keyword;
	
	@JoinColumn
	@ManyToOne
	private Response response;
	
	@JoinColumn
	@ManyToOne
	private Intention upperKeyword;
}
