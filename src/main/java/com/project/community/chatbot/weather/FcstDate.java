package com.project.community.chatbot.weather;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcstDate {
	
	String fcstTime;
	@Builder.Default
	List<FcstTime> fcstTimeList=new ArrayList<>();
	public FcstDate add(FcstTime timeData) {
		fcstTimeList.add(timeData);
		return this;
	}
	
	
}
