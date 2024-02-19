package com.project.community.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.project.community.domain.entity.EmployeeEntity;
import com.project.community.domain.entity.EmployeeEntityRepository;
import com.project.community.service.OauthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyOauth2userService extends DefaultOAuth2UserService{
	
	@Autowired
	OauthService oauthService;
	@Autowired
	EmployeeEntityRepository memRepo;
	@Autowired
	PasswordEncoder encoder;

	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		//registrationId : 현재 로그인진행중인 서비스를 구분하는 코드
		String registrationId=userRequest.getClientRegistration().getRegistrationId();
		log.debug(">>>>>registrationId : "+registrationId);
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.debug(">>>>>name : "+oAuth2User.getName());
		MyOAuthUser myoAuthUser = new MyOAuthUser(oAuth2User, registrationId);
		
		EmployeeEntity memberEntity = new EmployeeEntity();
		memberEntity.setEmpUsername(myoAuthUser.getEmail());
		memberEntity.setEmpName(myoAuthUser.getName());
		memberEntity.setProviderId(myoAuthUser.getProviderId());
		memberEntity.setProvider(myoAuthUser.getProvider());
		memberEntity.setEmpRole(myoAuthUser.getRole());
		String pass = myoAuthUser.getPass();
		String role = myoAuthUser.getRole();
		if(pass!=null) {
			memberEntity.setEmpPassword(encoder.encode(pass));
		};
		if(role!=null) {
			memberEntity.setEmpRole("USER");
		}
		if(myoAuthUser.getEmail()!=memberEntity.getEmpUsername()) {
			memRepo.save(memberEntity);
		};
		oAuth2User.getAttributes().forEach((key,value)->{
			log.debug(">>>>>key : "+key + ", value : " + value);
		});
		return myoAuthUser;
	}

}