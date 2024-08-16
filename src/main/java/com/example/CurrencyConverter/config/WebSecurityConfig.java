package com.example.CurrencyConverter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(auth->auth
                .requestMatchers("/convertCurrency").permitAll()
                .requestMatchers("/convertCurrency/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        // TODO add these lines before formLogin(28Line) to remove csrf token
        //  and make stateless session
        //.csrf( httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
        //.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        return httpSecurity.build();
    }

    @Bean
    UserDetailsService myInMemoryUserDetailsService(){
        UserDetails normalUser= User
                .withUsername("aman")
                .password(passwordEncoder().encode("aman"))
                .roles("USER")
                .build();

        UserDetails adminUser= User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normalUser,adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


