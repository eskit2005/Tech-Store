package com.example.Tech.Store.services;

import com.example.Tech.Store.config.JwtConfig;
import com.example.Tech.Store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+(1000*tokenExpiration)))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        return getClaimsFromToken(token)
                .getExpiration().after(new Date());

    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
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


}
