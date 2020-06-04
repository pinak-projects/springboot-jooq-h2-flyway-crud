package com.jooqDemo.controller;

import com.jooqDemo.constant.APIConstants;
import com.jooqDemo.constant.ResponseMessage;
import com.jooqDemo.entity.User;
import com.jooqDemo.exception.CustomException;
import com.jooqDemo.exception.ResourceNotFoundException;
import com.jooqDemo.service.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = APIConstants.REST_USERS_END_POINT)
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveUser(@Valid User user) {
		final int userId = userService.createUser(user);
		if (userId == 0) {
			log.info("ERROR: saveUser, MESSAGE: {}, ID: {}", APIConstants.ERROR_SAVE_USER, userId);
			throw new CustomException(APIConstants.ERROR_SAVE_USER, HttpStatus.NOT_IMPLEMENTED);
		}
		log.info("SUCCESS: saveUser, MESSAGE: {}, ID: {}", APIConstants.SUCCESS_SAVE_USER, userId);
		return ResponseEntity.ok(new ResponseMessage(APIConstants.SUCCESS_SAVE_USER));
	}

	@PutMapping(value = APIConstants.USER_ID_PARAM, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@PathVariable final int userId, @Valid User user) {
		if (!userService.isUserExistsById(userId)) {
			log.info("ERROR: updateUser, MESSAGE: {}, ID: {}", APIConstants.ERROR_USER_NOT_FOUND, userId);
			throw new ResourceNotFoundException(APIConstants.ERROR_USER_NOT_FOUND);
		}
		user.setUserId(userId);
		final int affectedRows = userService.updateUserDetails(user);
		if (affectedRows < 1) {
			log.info("ERROR: updateUser, MESSAGE: {}, ID: {}", APIConstants.ERROR_UPDATE_USER, userId);
			throw new CustomException(APIConstants.ERROR_UPDATE_USER, HttpStatus.NOT_IMPLEMENTED);
		}
		log.info("SUCCESS: updateUser, MESSAGE: {}, ID: {}", APIConstants.SUCCESS_UPDATE_USER, userId);
		return ResponseEntity.ok(new ResponseMessage(APIConstants.SUCCESS_UPDATE_USER));
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = APIConstants.USER_ID_PARAM, produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser(@PathVariable final int userId) {
		final User fetchedUser = userService.fetchUser(userId);
		if (fetchedUser == null) {
			log.info("ERROR: getUser, MESSAGE: {}, ID: {}", APIConstants.ERROR_USER_NOT_FOUND, userId);
			throw new ResourceNotFoundException(APIConstants.ERROR_USER_NOT_FOUND);
		}
		log.info("SUCCESS: getUser, MESSAGE: {}, ID: {}", null, userId);
		return fetchedUser;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		return userService.fetchUsersList();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable final int userId) {
		if (!userService.isUserExistsById(userId)) {
			log.info("ERROR: deleteUser, MESSAGE: {}, ID: {}", APIConstants.ERROR_USER_NOT_FOUND, userId);
			throw new ResourceNotFoundException(APIConstants.ERROR_USER_NOT_FOUND);
		}
		if (userService.deleteUser(userId) < 1) {
			log.info("ERROR: deleteUser, MESSAGE: {}, ID: {}", APIConstants.ERROR_DELETE_USER, userId);
			throw new CustomException(APIConstants.ERROR_DELETE_USER, HttpStatus.NOT_IMPLEMENTED);
		}
		log.info("SUCCESS: deleteUser, MESSAGE: {}, ID: {}", APIConstants.SUCCESS_USER_DELETED, userId);
		return ResponseEntity.ok(new ResponseMessage(APIConstants.SUCCESS_USER_DELETED));
	}

}
