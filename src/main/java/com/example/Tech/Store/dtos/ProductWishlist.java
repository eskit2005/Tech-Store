package com.example.Tech.Store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class ProductWishlist {
    @NotNull
    private UUID productId;
}
