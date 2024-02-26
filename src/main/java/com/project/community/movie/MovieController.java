package com.project.community.movie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieController {
	
	private final MovieService movieService;
	
	@GetMapping("/movie")
	public String movieDetail(Model model) {
		movieService.getThumbnailUrl(model);
		
		return "chatbot/movie-detail";
	}
}
