package com.example.Tech.Store.controllers;
import com.example.Tech.Store.dtos.AddUserRequest;
import com.example.Tech.Store.dtos.EmailRequest;
import com.example.Tech.Store.dtos.UserDto;
import com.example.Tech.Store.exceptions.UserAlreadyExistsException;
import com.example.Tech.Store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/User")
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody AddUserRequest addUserRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(addUserRequest));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeUser(@RequestBody Long id) {
        userService.deleteUser(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @PutMapping("update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.updateUser(userDto));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getAllUsers());
    }

    @PostMapping("find")
    public ResponseEntity<UserDto> getUserById(@RequestBody Long id) {
        return ResponseEntity
                .ok()
                .body(userService.getUser(id));
    }

    @PostMapping("find/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestBody EmailRequest emailRequest) {
        return ResponseEntity
                .ok()
                .body(userService.getUserByEmail(emailRequest.getEmail()));
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(Map.of("error", e.getMessage()));
    }


}
