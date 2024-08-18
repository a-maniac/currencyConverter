package com.example.CurrencyConverter.service.impl;

import com.example.CurrencyConverter.dto.LoginDto;
import com.example.CurrencyConverter.dto.SignUpDto;
import com.example.CurrencyConverter.dto.UserDto;
import com.example.CurrencyConverter.entities.UserEntity;
import com.example.CurrencyConverter.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new BadCredentialsException("User Not Found"));
    }

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new BadCredentialsException("User not found"));
    }

    public UserDto createSignUp(SignUpDto signUpDto) {
        Optional<UserEntity> user=userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User already present");
        }

        UserEntity userEntity=modelMapper.map(signUpDto,UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDto.class);

    }

}
