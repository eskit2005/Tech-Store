package com.example.Tech.Store.mappers;

import com.example.Tech.Store.dtos.ProductDto;
import com.example.Tech.Store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ProductMapper {

    //must be used inside transaction method
    @Mapping(target="category_name",  expression = "java(product.getCategory().getName())")
    ProductDto toDto(Product product);
}
