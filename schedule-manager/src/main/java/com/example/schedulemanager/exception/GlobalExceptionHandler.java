package com.example.schedulemanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request){
		ErrorResponse error = ErrorResponse.builder()
				.status(e.getStatus().value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.build();
		
		return ResponseEntity.status(error.getStatus()).body(error);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e, HttpServletRequest request){
		ErrorResponse error = ErrorResponse.builder()
				.status(500)
				.message("서버 내부 오류")
				.path(request.getRequestURI())
				.build();
		
		return ResponseEntity.internalServerError().body(error);
	}
}
