package com.example.Tech.Store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {
    @NotNull
    private UUID id;
    @NotBlank
    @Size(min = 1, max = 1000)
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private int price;
    @NotNull
    private String category_name;
    @NotNull
    private short stock;

    private String coverUrl;
}
