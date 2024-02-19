package com.project.community.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.project.community.domain.entity.EmployeeEntity;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

@Getter
public class MyOAuthUser implements OAuth2User {

	private Map<String, Object> attributes;
	private Collection<? extends GrantedAuthority> authorities;
	private String email;
	private String profile_image;
	private String nickName;
	private String role;
	private EmployeeEntity memberEntity;
	private String provider;
	private String providerId;
	
	public MyOAuthUser(OAuth2User oAuth2User, String registrationId) {
		this.authorities=oAuth2User.getAuthorities();
		if(registrationId.equals("google")) {
			ofGoogle(oAuth2User);
		}else if(registrationId.equals("naver")) {
			ofNaver(oAuth2User);
		}
			else if(registrationId.equals("kakao")) {
			ofKakao(oAuth2User);
		}
	}

	private void ofNaver(OAuth2User oAuth2User) {
		this.attributes=oAuth2User.getAttribute("response");
		//this.email=(String)attributes.get("email");
//		this.profileImg=(String)attributes.get("picture");
		this.nickName=(String)attributes.get("name");
	}
	
	private void ofKakao(OAuth2User oAuth2User) {
		this.attributes=oAuth2User.getAttribute("kakao_account");
		//email=(String)attributes.get("email");
		Map<String, String> profile= (Map<String, String>) attributes.get("profile");
		profile_image=(String)profile.get("profile_image");
		
		nickName=(String)profile.get("nickname");
	}

	private void ofGoogle(OAuth2User oAuth2User) {
		this.attributes=oAuth2User.getAttributes();
		this.email=(String)attributes.get("email");
		this.nickName=(String)attributes.get("name");
		this.provider = "google";
		this.providerId = (String)attributes.get("sub");
		this.role=(String)attributes.get(role);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() { //ID에 해당하는 정보
		return nickName;
	}

	public String getPass() {
		return "";
	}
	public String getRole() {
		return "";
	}

}
