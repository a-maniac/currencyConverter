package com.example.CurrencyConverter;

import com.example.CurrencyConverter.entities.UserEntity;
import com.example.CurrencyConverter.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyConverterApplicationTests {

	@Autowired
	private JwtServiceImpl jwtService;

	@Test
	void contextLoads() {
	}

	@Test

	void testJwtService(){

		UserEntity user= new UserEntity(4L,"aman@gmail.com","!223");
		String token=jwtService.createJwtToken(user);
		System.out.println(token);
		System.out.println(jwtService.getUserIdFromToken(token));

	}


}
