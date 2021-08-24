package com.cognizant.authenticationmicroservice.service;

import com.cognizant.authenticationmicroservice.exceptions.UserNotFoundException;
import com.cognizant.authenticationmicroservice.model.AppUser;
import com.cognizant.authenticationmicroservice.repository.UserRepository;
import com.cognizant.authenticationmicroservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements LoginService {

    @Autowired
	private JwtUtil jwtutil;
	
    @Autowired
	private UserDetailServiceImpl customerDetailservice;
 
	@Autowired
	private UserRepository userRepository;

    @Override
    public AppUser userLogin(AppUser appuser) throws UserNotFoundException {
        UserDetails userdetails = customerDetailservice.loadUserByUsername(appuser.getUserid());
		String userid = "";
		String role="";
		String token = "";
		
		AppUser user = null;
		user = userRepository.findById(appuser.getUserid()).orElse(null); //.get()
		
		if (userdetails.getPassword().equals(appuser.getPassword()) && appuser.getRole().equals(user.getRole()) ) {
			userid = appuser.getUserid();
			token = jwtutil.generateToken(userdetails);
			role = appuser.getRole();
			return new AppUser(userid, null, null, token,role);
		} else {
			throw new UserNotFoundException("Username/Password is incorrect...Please check");
		
    }
}
    
}
