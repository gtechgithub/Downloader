package com.voice.download.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorControlller extends AbstractErrorController {

	
	
	public ErrorControlller(ErrorAttributes errorAttributes) {
        super(errorAttributes, Collections.emptyList());
	}

	@RequestMapping
    public ResponseEntity<?> error(HttpServletRequest request, ErrorAttributeOptions options) {


        HttpStatus status = this.getStatus(request);
        return new ResponseEntity<>("error page", status);
    }
	
	@Override
	public String getErrorPath() {
		return "default error page";
	}

	
}
