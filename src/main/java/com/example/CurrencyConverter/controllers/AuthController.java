package com.example.CurrencyConverter.controllers;

import com.example.CurrencyConverter.dto.LoginDto;
import com.example.CurrencyConverter.dto.SignUpDto;
import com.example.CurrencyConverter.dto.UserDto;
import com.example.CurrencyConverter.service.impl.AuthServiceImpl;
import com.example.CurrencyConverter.service.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;
    private final UserServiceImpl userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> createSignUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto=userService.createSignUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request,
                                        HttpServletResponse response){
        String token=authService.login(loginDto);

        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }
}
