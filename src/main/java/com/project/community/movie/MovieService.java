package com.project.community.movie;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	
	private final String MovieUrl = "http://www.cgv.co.kr/movies/?lt=1&ft=1";
	
	public String getThumbnailUrl(String movieName) {
		try {
			Document document = Jsoup.connect(MovieUrl).get();
			
			Elements thumbnail = document.select("img[alt=" + movieName + " 포스터]");
			String thumbnailUrl = thumbnail.attr("src");
			
			return thumbnailUrl;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
}
