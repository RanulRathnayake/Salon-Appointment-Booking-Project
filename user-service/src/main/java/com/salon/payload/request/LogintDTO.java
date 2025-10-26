package com.salon.payload.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogintDTO {

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotNull
    private String password;

}