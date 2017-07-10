package com.ttd.exception;
/**
 * AppException
 * @author wolf
 * @since 2016-03-10
 */
public class AppException extends RuntimeException{
	private static final long serialVersionUID = 1L;
    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }
    
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}
