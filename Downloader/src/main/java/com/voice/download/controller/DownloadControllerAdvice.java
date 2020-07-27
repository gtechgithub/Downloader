package com.voice.download.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class DownloadControllerAdvice {


	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exception() {
		return new ResponseEntity<String>("Something Went Wrong", HttpStatus.OK);
	}
}
