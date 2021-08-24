package com.affirm.loan.exception;

public class AffirmServiceException extends Exception {
    public AffirmServiceException(Throwable cause) {
        super(cause);
    }
    public AffirmServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
