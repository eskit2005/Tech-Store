package com.example.Tech.Store.mappers;

import com.example.Tech.Store.dtos.AddUserRequest;
import com.example.Tech.Store.dtos.UserDto;
import com.example.Tech.Store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(AddUserRequest request);

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

}
