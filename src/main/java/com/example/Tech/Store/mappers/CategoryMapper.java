package com.example.Tech.Store.mappers;

import com.example.Tech.Store.dtos.CategoryDto;
import com.example.Tech.Store.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

//    @Mapping(target="id",source = "id")
    CategoryDto toDto(Category category);
}
