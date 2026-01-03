package com.example.Tech.Store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RemoveProductRequest {
    @NotNull
    private final UUID Id;
    @NotBlank
    private final String name;

}
