package com.cognizant.authenticationmicroservice.service;
import com.cognizant.authenticationmicroservice.exceptions.UserNotFoundException;
import  com.cognizant.authenticationmicroservice.model.AppUser;

import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    public AppUser userLogin(AppUser appuser) throws UserNotFoundException;
    
}
