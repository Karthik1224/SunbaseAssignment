package com.sunbase.assignment.service;

import com.sunbase.assignment.dto.JwtResponse;
import com.sunbase.assignment.dto.SignIn;
import com.sunbase.assignment.dto.SignUp;
import com.sunbase.assignment.modal.LoginDetails;

public interface AuthenticationService {
    LoginDetails signUp(SignUp signUp);
    JwtResponse signIn(SignIn signIn);
}
