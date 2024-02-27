package com.project.community.movie;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieController {
	
	private final MovieService movieService;
	
	@PostMapping("/movie/thumbnail")
	@ResponseBody
	public String movieThumbnail(@RequestBody Map<String, Object> movieName) {
		String thumbnailUrl = (String) movieName.get("movieName");
		return movieService.getThumbnailUrl(thumbnailUrl);
	}
}
