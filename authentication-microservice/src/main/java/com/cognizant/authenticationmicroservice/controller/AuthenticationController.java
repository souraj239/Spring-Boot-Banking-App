package com.cognizant.authenticationmicroservice.controller;

import com.cognizant.authenticationmicroservice.exceptions.UserNotFoundException;
import com.cognizant.authenticationmicroservice.model.AppUser;
import com.cognizant.authenticationmicroservice.repository.UserRepository;
import com.cognizant.authenticationmicroservice.service.LoginService;
import com.cognizant.authenticationmicroservice.service.TokenValidationService;
import com.cognizant.authenticationmicroservice.service.UserDetailServiceImpl;
import com.cognizant.authenticationmicroservice.dto.AuthenticationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenValidationService tokenValidationService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/login")
    public ResponseEntity<AppUser> login(@RequestBody AppUser userCredentials) throws UserNotFoundException {
        AppUser user=loginService.userLogin(userCredentials);
        return new ResponseEntity<>(user,HttpStatus.OK);
        
    }

    @GetMapping("/validateToken")
	public ResponseEntity<AuthenticationResponse> getValidity(@RequestHeader("Authorization") final String token) {
		return new ResponseEntity<>(tokenValidationService.validate(token),HttpStatus.OK);
	}

    @PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody AppUser appUserCredentials) {
		AppUser createduser = null;
		try {
			createduser = userRepository.save(appUserCredentials);
		} catch (Exception e) {
			return new ResponseEntity<String>("Not created", HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(createduser, HttpStatus.CREATED);

	}

	
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	@GetMapping("/find")
	public ResponseEntity<List<AppUser>> findUsers(@RequestHeader("Authorization") final String token) {
		List<AppUser> createduser = new ArrayList<>();
		List<AppUser> findAll = userRepository.findAll();
		findAll.forEach(emp -> createduser.add(emp));
		System.out.println(createduser);
		return new ResponseEntity<>(createduser, HttpStatus.CREATED);

	}

	@GetMapping("/role/{id}")
	public String getRole(@PathVariable("id") String id) {
		return userRepository.findById(id).get().getRole();
	}

	@DeleteMapping("deleteCustomer/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") String token, @PathVariable String id) {

		System.out.println("Starting deletion of-->" + id);
		userDetailService.deleteCustomer(id);
		System.out.println("Deleted");
		return new ResponseEntity<>("Deleted SUCCESSFULLY", HttpStatus.OK);
	}

    @GetMapping("/health")
	public ResponseEntity<String> healthCheckup() {
		return new ResponseEntity<>("Authentication UP", HttpStatus.OK);
	}

}
