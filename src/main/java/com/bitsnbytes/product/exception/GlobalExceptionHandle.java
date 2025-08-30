package com.bitsnbytes.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.bitsnbytes.product.dto.ExceptionResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandle {

	@ExceptionHandler(CategoryAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponseDTO> handleCategoryAlreadyExistException(CategoryAlreadyExistsException ex,
			WebRequest webRequest) {
		ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(webRequest.getDescription(false),
				HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDTO);
	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ExceptionResponseDTO> handleCategoryAlreadyExistException(CategoryNotFoundException e,
			WebRequest webRequest) {
		ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(webRequest.getDescription(false),
				HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponseDTO> handleGlobalException(Exception e,
			WebRequest webRequest) {
		ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(webRequest.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDTO);
	}
}
