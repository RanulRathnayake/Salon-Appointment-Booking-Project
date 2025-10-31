package com.salon.payload.request;

import com.salon.payload.dto.Credential;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequest {

    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private List<Credential> credentials = new ArrayList<>();
    private List<String> realmRoles = new ArrayList<>();
}
