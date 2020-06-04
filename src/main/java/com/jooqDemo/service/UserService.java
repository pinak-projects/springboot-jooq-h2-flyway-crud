package com.jooqDemo.service;

import java.util.List;

import com.jooqDemo.entity.User;

public interface UserService {

	User fetchUser(int userId);

	int createUser(User user);

	List<User> fetchUsersList();

	int deleteUser(int userId);

	boolean isUserExistsById(int userId);

    int updateUserDetails(User user);
}
