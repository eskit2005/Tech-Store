package com.example.Tech.Store.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class WishlistDto {
    private UUID product_id;
    private Long user_id;
}
