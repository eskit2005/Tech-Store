package com.example.Tech.Store.services;

import com.example.Tech.Store.dtos.AddUserRequest;
import com.example.Tech.Store.dtos.UserDto;
import com.example.Tech.Store.mappers.UserMapper;
import com.example.Tech.Store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(AddUserRequest addUserRequest) {
        var user=userMapper.toEntity(addUserRequest);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public UserDto updateUser(UserDto userDto) {
        var user=userRepository.findById(userDto.getId()).orElse(null);
        if(user==null) throw new RuntimeException("User not found");
        var updatedUser=userMapper.toEntity(userDto);
        userRepository.save(updatedUser);
        return userMapper.toDto(user);


    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user-> userMapper.toDto(user))
                .toList();
    }

    public UserDto getUser(Long id){
        var user=userRepository.findById(id).orElse(null);
        if(user==null) throw new RuntimeException("User not found");
        return userMapper.toDto(user);
    }

    public UserDto getUserByEmail(String email){
        var user=userRepository.findByEmail(email).orElse(null);
        if(user==null) throw new RuntimeException("User not found");
        return userMapper.toDto(user);
    }


}
