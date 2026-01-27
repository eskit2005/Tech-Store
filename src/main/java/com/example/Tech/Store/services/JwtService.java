package com.example.Tech.Store.services;

import com.example.Tech.Store.configs.JwtConfig;
import com.example.Tech.Store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public String getAccessToken(User user) {
        return generateToken(user,jwtConfig.getAccessTokenExpiration());
    }

    public String getRefreshToken(User user) {
        return generateToken(user,jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(User user,long tokenExpiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("role",user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+(1000*tokenExpiration)))
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtConfig.getSecret())))
                .compact();
    }

    public boolean validateToken(String token) {
        return getClaimsFromToken(token)
                .getExpiration().after(new Date());

    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtConfig.getSecret())))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public long getUserIdFromToken(String token) {
        return Long.parseLong(getClaimsFromToken(token).getSubject());

    }

    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).get("email").toString();
    }

    public String getRoleFromToken(String token) {
        return getClaimsFromToken(token).get("role").toString();
    }


}
