package com.example.CurrencyConverter.service.impl;

import com.example.CurrencyConverter.dto.LoginDto;
import com.example.CurrencyConverter.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;

    public String login(LoginDto loginDto) {

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        UserEntity user= (UserEntity) authentication.getPrincipal();
        return jwtService.createJwtToken(user);
    }
}
