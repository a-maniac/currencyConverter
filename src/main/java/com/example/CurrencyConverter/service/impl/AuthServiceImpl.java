package com.example.CurrencyConverter.service.impl;

import com.example.CurrencyConverter.dto.LoginDto;
import com.example.CurrencyConverter.dto.LoginResponseDto;
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
    private final UserServiceImpl userService;

    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        UserEntity user= (UserEntity) authentication.getPrincipal();
        String accessToken= jwtService.createJwtToken(user);
        String refreshToken= jwtService.createRefreshToken(user);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {

        Long userId=jwtService.getUserIdFromToken(refreshToken);
        UserEntity user=userService.getUserById(userId);

        String accessToken=jwtService.createJwtToken(user);
        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }
}
