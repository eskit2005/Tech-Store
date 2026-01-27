package com.example.Tech.Store.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    @Email(message = "Please provide a valid email")
    private String email;
    @Size(min = 6,max = 255)
    private String password;
}
