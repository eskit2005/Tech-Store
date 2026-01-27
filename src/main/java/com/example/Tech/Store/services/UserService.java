package com.example.Tech.Store.services;

import com.example.Tech.Store.dtos.AddUserRequest;
import com.example.Tech.Store.dtos.UserDto;
import com.example.Tech.Store.entities.Role;
import com.example.Tech.Store.exceptions.UserAlreadyExistsException;
import com.example.Tech.Store.mappers.UserMapper;
import com.example.Tech.Store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDto createUser(AddUserRequest addUserRequest) {
        var errorUser=userRepository.findByEmail(addUserRequest.getEmail());
        if(errorUser.isPresent()){ throw new UserAlreadyExistsException("User already exists"); }
        var user=userMapper.toEntity(addUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (addUserRequest.getEmail().endsWith("@g.bracu.ac.bd")) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        userRepository.save(user);
        System.out.println(userMapper.toDto(user).toString());
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
