package com.example.Tech.Store.mappers;

import com.example.Tech.Store.dtos.WishlistDto;
import com.example.Tech.Store.entities.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    @Mapping(target="user_id", expression="java(wishlist.getId().getUserId())" )
    @Mapping(target="product_id" , expression="java(wishlist.getId().getProductId())")
    WishlistDto toDto(Wishlist wishlist);
}
