package com.sunbase.assignment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbase.assignment.dto.SignIn;
import com.sunbase.assignment.dto.SignUp;
import com.sunbase.assignment.service.AuthenticationService;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUp signUp)
    {
    	logger.info("Inside AuthController : signup");
        return ResponseEntity.ok(authenticationService.signUp(signUp));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignIn signIn)
    {
    	logger.info("Inside AuthController : signin");
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }

}