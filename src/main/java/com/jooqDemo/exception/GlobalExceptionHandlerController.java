package com.jooqDemo.exception;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.json.JSONObject;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.jooqDemo.constant.APIConstants;
import com.jooqDemo.constant.ResponseMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerController {

	@Bean
	public ErrorAttributes errorAttributes() {
		// Hide exception field in the return object
		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest requestAttributes, boolean includeStackTrace) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
				errorAttributes.remove("exception");
				return errorAttributes;
			}
		};
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	private ResponseEntity<ResponseMessage> handleResourceNotFoundException(ResourceNotFoundException e) {
		log.error("ResourceNotFoundException", e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException hmnrex)
			throws IOException {
		log.error("Some value is missing in JSON.", hmnrex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseMessage(APIConstants.SOMETHING_WENR_WRONG));
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ResponseMessage> handleCustomException(CustomException ce) throws IOException {
		log.error("CustomException", ce);
		return ResponseEntity.status(ce.getHttpStatus()).body(new ResponseMessage(ce.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseMessage> handleException(Exception e) throws IOException {
		log.error("Exception", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseMessage(APIConstants.SOMETHING_WENR_WRONG));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		JSONObject jsonObject = new JSONObject().put(APIConstants.MESSAGE, ex.getBindingResult().getAllErrors().stream()
				.map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
		return ResponseEntity.badRequest().body(jsonObject.toString());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
		JSONObject jsonObject = new JSONObject().put(APIConstants.MESSAGE,
				ex.getConstraintViolations().stream().map(a -> a.getMessage()).collect(Collectors.toList()));
		return ResponseEntity.badRequest().body(jsonObject.toString());
	}

}
