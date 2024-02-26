package com.project.community.chatbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KomoranConfig {
	
	private String USER_DIC = "user.dic";
	
	@Bean
	Komoran komoran() throws IOException {
		createDIC();
		
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		komoran.setUserDic(USER_DIC);
		
		return komoran;
	}

	private void createDIC() throws IOException {
		File file = new File(USER_DIC);
		
		if (!file.exists()) file.createNewFile();
		
		Set<String> nnpSet = new HashSet<>();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		String data = null;
		
		while ((data = bufferedReader.readLine()) != null) {
			if (data.startsWith("#")) continue;
			
			nnpSet.add(data.split("\\t")[0]);
		}
		
		bufferedReader.close();
	}
}
