package com.example.Tech.Store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddProductRequest {

    @NotBlank
    private final String description;
    @NotNull
    private final short categoryId;
    @NotNull
    private final short stock;
    @NotBlank
    @Size(min = 1, max = 1000)
    private final String title;
    @NotNull
    private final String cover_url;
    @NotNull
    @Min(0)
    private final int price;
}
