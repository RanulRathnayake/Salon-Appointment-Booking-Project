package com.salon.payload.request;

import com.salon.domain.UserRole;
import lombok.Data;

@Data
public class SignupDTO {

    private String username;
    private String enabled;
    private String fullName;
    private String email;
    private String password;
    private UserRole role;
}



//0.38 keycloak video
