package com.example.Tech.Store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddUserRequest {

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    @NotBlank
    @Size(min = 1, max = 255)
    private String email;
    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
}
