package com.br.rmboni.sqsQueueProducer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GeneralExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(final Exception exception, final Locale locale) {
		logger.error("Erro", exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(exception.getMessage()));
	}
}
