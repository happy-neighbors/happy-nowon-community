package com.project.community.movie;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class MovieService {
	
	private final String MovieUrl = "http://www.cgv.co.kr/movies/?lt=1&ft=1";
	
	public void getThumbnailUrl(Model model) {
		try {
			Document document = Jsoup.connect(MovieUrl).get();
			
			Elements thumbnail = document.select("span.thumb-image > img[alt=건국전쟁 포스터]");
			String thumbnailUrl = thumbnail.attr("src");
			
			System.out.println("thumbnail: " + thumbnailUrl);
			
			model.addAttribute("thumbnailUrl", thumbnailUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
