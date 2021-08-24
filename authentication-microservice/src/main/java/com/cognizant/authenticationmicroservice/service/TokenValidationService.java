package com.cognizant.authenticationmicroservice.service;

import com.cognizant.authenticationmicroservice.dto.AuthenticationResponse;

import org.springframework.stereotype.Service;

@Service
public interface TokenValidationService {
    public AuthenticationResponse validate(String token);    
}
