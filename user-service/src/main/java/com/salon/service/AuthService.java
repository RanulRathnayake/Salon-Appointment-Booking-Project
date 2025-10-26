package com.salon.service;


import com.salon.payload.response.AuthResponse;
import com.salon.payload.request.SignupDTO;

public interface AuthService {
    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
