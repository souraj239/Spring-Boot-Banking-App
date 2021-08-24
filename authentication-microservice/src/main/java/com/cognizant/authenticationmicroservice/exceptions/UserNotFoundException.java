package com.cognizant.authenticationmicroservice.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg){
        super(msg);
    }
    public UserNotFoundException(){}
}
