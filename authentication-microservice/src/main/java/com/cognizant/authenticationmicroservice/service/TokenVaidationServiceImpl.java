package com.cognizant.authenticationmicroservice.service;

import com.cognizant.authenticationmicroservice.dto.AuthenticationResponse;
import com.cognizant.authenticationmicroservice.repository.UserRepository;
import com.cognizant.authenticationmicroservice.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenVaidationServiceImpl implements TokenValidationService {
    @Autowired
	private JwtUtil jwtutil;

	@Autowired
	private UserRepository userRepository;

    @Override
	public AuthenticationResponse validate(String token) {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		String jwt = token;

		if (jwtutil.validateToken(jwt)) {
			authenticationResponse.setUserid(jwtutil.extractUsername(jwt));
			authenticationResponse.setValid(true);
			authenticationResponse.setName(userRepository.findById(jwtutil.extractUsername(jwt)).get().getUsername());
		} else {
			authenticationResponse.setValid(false);
		}
		return authenticationResponse;
	}
    
}
