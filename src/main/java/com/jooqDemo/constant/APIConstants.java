package com.jooqDemo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class APIConstants {

	public static final String REST_USERS_END_POINT = "/users";
	public static final String USER_ID_PARAM = "/{userId}";

	public static final String MESSAGE = "Message";
	public static final String SOMETHING_WENT_WRONG = "Something went wrong, please try again.";
	public static final String ERROR_SAVE_USER = "Error in saving user details, please try again.";
	public static final String SUCCESS_SAVE_USER = "User details saved successfully.";
	public static final String ERROR_USER_NOT_FOUND = "User details not found.";
	public static final String SUCCESS_USER_DELETED = "User deleted successfully";
	public static final String ERROR_DELETE_USER = "Error in deleting user details, please try again.";
	public static final String ERROR_UPDATE_USER = "Error in updating user details, please try again.";
	public static final String SUCCESS_UPDATE_USER = "User details updated successfully.";

}
