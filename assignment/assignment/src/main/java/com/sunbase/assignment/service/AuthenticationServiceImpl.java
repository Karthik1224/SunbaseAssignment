package com.sunbase.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunbase.assignment.Repository.LoginDetailsRepository;
import com.sunbase.assignment.dto.JwtResponse;
import com.sunbase.assignment.dto.SignIn;
import com.sunbase.assignment.dto.SignUp;
import com.sunbase.assignment.modal.LoginDetails;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
    @Autowired
    private LoginDetailsRepository loginDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    
    //Here we will create new login details and save in database
    public LoginDetails signUp(SignUp signUp)
    {
    	logger.info("Inside AuthenticationServiceImpl : signUp");
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setEmail(signUp.getEmail());
        loginDetails.setPassword(passwordEncoder.encode(signUp.getPassword()));
        return loginDetailsRepository.save(loginDetails);
    }

    //Verify the user details received from request with details exist in db, then generate token and send
    public JwtResponse signIn(SignIn signIn)
    {
    	logger.info("Inside AuthenticationServiceImpl : signIn");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(),signIn.getPassword()));
               UserDetails userDetails = loginDetailsRepository.findByEmail(signIn.getEmail()).orElseThrow(()-> new IllegalArgumentException(" Invalid Username or Password  !!"));
               String token = jwtService.generateToken(userDetails);
               JwtResponse response = new JwtResponse();
               response.setEmail(userDetails.getUsername());
               response.setJwtToken(token);
               logger.info("JwtResponse : {}",response);
               return response;


    }

}
