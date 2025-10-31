package com.salon.service.impl;

import com.salon.modal.User;
import com.salon.payload.request.SignupDTO;
import com.salon.payload.response.AuthResponse;
import com.salon.payload.response.TokenResponse;
import com.salon.repository.UserRepository;
import com.salon.service.AuthService;
import com.salon.service.KeycloakUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final KeycloakUserService keycloakUserService;
    @Override
    public AuthResponse login(String username, String password) throws Exception {
        TokenResponse tokenResponse=keycloakUserService.getAdminAccessToken(
                username,
                password,
                "password",
                null
        );
        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome Back " + username);
        response.setMessage("login success");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }

    @Override
    public AuthResponse signup(SignupDTO req) throws Exception {
        keycloakUserService.createUser(req);

        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setUserName(req.getUsername());
        createdUser.setPassword(req.getPassword());
        createdUser.setRole(req.getRole());
        createdUser.setFullName(req.getFullName());
        createdUser.setCreateAt(LocalDateTime.now());

        userRepository.save(createdUser);


        TokenResponse tokenResponse= keycloakUserService.getAdminAccessToken(
                req.getUsername(),
                req.getPassword(),
                "password",
                null
        );

        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome " + createdUser.getEmail());
        response.setMessage("Register success");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {
        TokenResponse tokenResponse= keycloakUserService.getAdminAccessToken(
                null,
                null,
                "refresh_token",
                refreshToken
        );
        AuthResponse response = new AuthResponse();

        response.setMessage("Access token received");
        response.setJwt(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }
}
