package com.example.Tech.Store.controllers;

import com.example.Tech.Store.configs.JwtConfig;
import com.example.Tech.Store.dtos.JwtResponse;
import com.example.Tech.Store.dtos.LoginRequest;
import com.example.Tech.Store.dtos.UserDto;
import com.example.Tech.Store.entities.RefreshToken;
import com.example.Tech.Store.mappers.UserMapper;
import com.example.Tech.Store.repositories.RefreshTokenRepository;
import com.example.Tech.Store.repositories.UserRepository;
import com.example.Tech.Store.services.AuthService;
import com.example.Tech.Store.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final JwtConfig  jwtConfig;
    private final UserMapper userMapper;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user= userRepository.findByEmail(request.getEmail()).orElse(null);
        var accessToken=jwtService.getAccessToken(user);
        var refreshToken=jwtService.getRefreshToken(user);

        var cookie=new Cookie("refreshToken",refreshToken);
        //Meaning: JavaScript running in the browser cannot access this cookie
        //Security benefit: prevents XSS attacks from stealing your refresh token
        cookie.setHttpOnly(true);
        //Meaning: The browser will only send this cookie to requests under /auth/refresh
        //Helps limit exposure of the cookie to only the refresh endpoint
        cookie.setPath("/auth");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        //Meaning: the browser will only send it over HTTPS, not HTTP
        //Important in production to prevent token interception
//        cookie.setSecure(true);
        response.addCookie(cookie);

        //creating a refreshtoken entity to be stored on the database
        Instant now=Instant.now();
        Instant expire=now.plusSeconds(jwtConfig.getRefreshTokenExpiration());
        RefreshToken token=new  RefreshToken();
        token.setIssuedAt(now);
        token.setExpiry(expire);
        token.setToken(refreshToken);
        token.setUser_id(user.getId());
        token.setRevoked(false);
        refreshTokenRepository.save(token);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JwtResponse(accessToken));

    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken){

        if((!jwtService.validateToken(refreshToken)) || (refreshTokenRepository.findByToken(refreshToken).getRevoked())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var UserId=jwtService.getUserIdFromToken(refreshToken);
        var user=userRepository.findById(UserId).orElseThrow();
        var accessToken=jwtService.getAccessToken(user);
        return  ResponseEntity
                .ok()
                .body(new JwtResponse(accessToken));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        var user=authService.getLoggedUser();
        return ResponseEntity
                .ok()
                .body(userMapper.toDto(user));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(value = "refreshToken")String refreshToken,HttpServletResponse response){

        Cookie cookie=new Cookie("refreshToken","");
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(0);
//        cookie.setSecure(true);
        response.addCookie(cookie);

        var token=refreshTokenRepository.findByToken(refreshToken);
        token.setRevoked(true);
        refreshTokenRepository.save(token);

        return ResponseEntity
                .ok()
                .build();

    }




}
