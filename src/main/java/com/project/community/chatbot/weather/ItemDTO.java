package com.project.community.chatbot.weather;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
	private String fcstDate;
	
	@Builder.Default
	List<FcstDate> fcstDateList=new ArrayList<>();
	
}
