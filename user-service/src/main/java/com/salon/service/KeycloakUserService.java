package com.salon.service;

import com.salon.payload.dto.*;
import com.salon.payload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {
    private static final String KEYCLOAK_BASE_URL="http://localhost:8080";

    private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL+"/admin/realms/master/users";

    private static final String TOKEN_URL = KEYCLOAK_BASE_URL+"/realms/master/protocol/openid-connect/token";
    private static final String CLIENT_ID = "salon-booking-client"; // Replace with your client ID
    private static final String CLIENT_SECRET = "txByPyRgUEWZiliRz9OQLXuPE7P5xpZ9"; // Replace with your client secret
    private static final String GRANT_TYPE = "password";
    private static final String scope = "openid email profile"; // Adjust grant type if necessary
    private static final String username = "admin@gmail.com";
    private static final String password = "admin";
    private static  final String clientId = "a43dab90-8534-40ca-b1f1-96f062e16674";

    private final RestTemplate restTemplate;

    public void createUser(SignupDTO signupDTO) throws Exception {

        String ACCESS_TOKEN = getAdminAccessToken(
                username,
                password,
                GRANT_TYPE,null).getAccessToken();

        Credential credential = new Credential();
        credential.setTemporary(false);
        credential.setType("password");
        credential.setValue(signupDTO.getPassword());

        UserRequest userRequest=new UserRequest();
        userRequest.setEmail(signupDTO.getEmail());
        userRequest.setEnabled(true);
        userRequest.setUsername(signupDTO.getUsername());
        userRequest.getCredentials().add(credential);



        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("ACCESS_TOKEN"); //remove ""

        // Create HTTP entity
        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                KEYCLOAK_ADMIN_API,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    public TokenResponse getAdminAccessToken(String username,
                                             String password,
                                             String grantType,
                                             String refreshToken) throws Exception {
        return new TokenResponse();
    }

    public KeycloakRole getRoleByName(String clientId, String token, String role) throws Exception {
        return null;
    }

    public KeycloakUserDTO fetchFirstUserByUsername(String username, String token) throws Exception {
        return null;
    }



}
