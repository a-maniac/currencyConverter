package com.example.CurrencyConverter.service.impl;

import com.example.CurrencyConverter.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtServiceImpl {

    @Value("${jwt.secretKey}")
    public String secretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwtToken(UserEntity userEntity){
        return Jwts.builder()
                .subject(userEntity.getId().toString())
                .claim("email",userEntity.getEmail())
                .claim("roles", Set.of("ADMIN,USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+60*1000*10))
                .signWith(getSecretKey())
                .compact();
    }

    public String createRefreshToken(UserEntity userEntity){
        return Jwts.builder()
                .subject(userEntity.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ 60L *1000*60*24*30*6))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claim=Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claim.getSubject());
    }


}
