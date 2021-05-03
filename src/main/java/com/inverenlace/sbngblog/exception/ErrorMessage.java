package com.inverenlace.sbngblog.exception;

public class ErrorMessage {

	private String exception;
	private String message;
	private String path;
	
	public ErrorMessage(Exception exception, String path) {
		this.exception = exception.getClass().getSimpleName();
		this.message = exception.getMessage();
		this.path = path;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ErrorMessage [exception=" + exception + ", message=" + message + ", path=" + path + "]";
	}

}
