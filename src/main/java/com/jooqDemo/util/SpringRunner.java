package com.jooqDemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jooqDemo.entity.User;
import com.jooqDemo.service.UserService;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringRunner implements CommandLineRunner {

	private static final String name = "Sample ";

	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) {
		for (int i = 1; i <= 10; i++) {
			userService.createUser(User.builder().name(name + i).build());
		}
	}

}
