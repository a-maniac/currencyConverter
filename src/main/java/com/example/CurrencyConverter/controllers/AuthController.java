package com.example.CurrencyConverter.controllers;

import com.example.CurrencyConverter.dto.LoginDto;
import com.example.CurrencyConverter.dto.LoginResponseDto;
import com.example.CurrencyConverter.dto.SignUpDto;
import com.example.CurrencyConverter.dto.UserDto;
import com.example.CurrencyConverter.service.impl.AuthServiceImpl;
import com.example.CurrencyConverter.service.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;
    private final UserServiceImpl userService;

    @Value("${deployment.env}")
    private String deployEnv;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> createSignUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto=userService.createSignUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request,
                                        HttpServletResponse response){
        LoginResponseDto loginResponseDto=authService.login(loginDto);

        Cookie cookie=new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv)); //set secure only if production
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest httpServletRequest){
        Cookie[] cookies=httpServletRequest.getCookies();
        String refreshToken=Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(()-> new AuthenticationServiceException("Refresh token not found inside the cookie"));
        LoginResponseDto loginResponseDto=authService.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDto);
    }

}
