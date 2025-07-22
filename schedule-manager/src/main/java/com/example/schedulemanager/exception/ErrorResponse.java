package com.example.schedulemanager.exception;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class ErrorResponse {
	private int status;
	private String message;
	private String path;
}
