package com.jooqDemo.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
	private HttpStatus httpStatus;

}
