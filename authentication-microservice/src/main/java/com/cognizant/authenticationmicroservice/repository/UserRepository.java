package com.cognizant.authenticationmicroservice.repository;

import com.cognizant.authenticationmicroservice.model.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {

}